package truonggg.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;
import truonggg.entity.Author;
import truonggg.entity.Book;
import truonggg.handler.BusinessException;
import truonggg.mapper.BookMapper;
import truonggg.repository.AuthorRepository;
import truonggg.repository.BookRepository;
import truonggg.response.ErrorCode;
import truonggg.response.PagedResult;
import truonggg.service.BookService;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public PagedResult<BookResponseDTO> getAll(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new BusinessException("Page must be >= 0 and size must be > 0", ErrorCode.BAD_REQUEST, "book");
        }
        Page<Book> books = this.bookRepository.findAll(PageRequest.of(page, size));
        return PagedResult.from(books, this.bookMapper.toDTOList(books.getContent()));
    }

    @Override
    public BookResponseDTO save(BookRequestDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));

        Book book = bookMapper.toEntity(dto);
        LocalDateTime now = LocalDateTime.now();
        book.setCreateAt(now);
        book.setUpdateAt(now);

        book.setAuthor(author);

        Book saved = bookRepository.save(book);

        return bookMapper.toDTO(saved);
    }

    @Override
    public BookResponseDTO update(BookRequestDTO dto, Integer id) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));
        Author author = this.authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));
        book.setName(dto.getName());
        book.setAuthor(author);
        book.setUpdateAt(LocalDateTime.now());
        return this.bookMapper.toDTO(this.bookRepository.save(book));
    }

    @Override
    public void delete(Integer id) {
        this.bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));
        this.bookRepository.deleteById(id);
    }
}
