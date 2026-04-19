package truonggg.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;
import truonggg.entity.Author;
import truonggg.entity.Book;
import truonggg.helper.ServiceValidationHelper;
import truonggg.handler.BusinessException;
import truonggg.mapper.BookMapper;
import truonggg.repository.AuthorRepository;
import truonggg.repository.BookRepository;
import truonggg.response.ErrorCode;
import truonggg.response.PagedResult;
import truonggg.service.BookService;
@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public PagedResult<BookResponseDTO> getAll(int page, int size) {
        log.info("Get all books with page={}, size={}", page, size);
        ServiceValidationHelper.validatePageAndSize(page, size, "book");
        Page<Book> books = this.bookRepository.findAll(PageRequest.of(page, size));
        return PagedResult.from(books, this.bookMapper.toDTOList(books.getContent()));
    }

    @Override
    public BookResponseDTO save(BookRequestDTO dto) {
        log.info("Create book with name={} and authorId={}", dto.getName(), dto.getAuthorId());
        Author author = ServiceValidationHelper.requireExists(authorRepository.findById(dto.getAuthorId()),
                () -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));

        Book book = bookMapper.toEntity(dto, author);
        book.markCreatedNow();

        Book saved = bookRepository.save(book);

        return bookMapper.toDTO(saved);
    }

    @Override
    public BookResponseDTO update(BookRequestDTO dto, Integer id) {
        log.info("Update book id={} with authorId={}", id, dto.getAuthorId());
        Book book = ServiceValidationHelper.requireExists(this.bookRepository.findById(id),
                () -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));
        Author author = ServiceValidationHelper.requireExists(this.authorRepository.findById(dto.getAuthorId()),
                () -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));
        book.updateInfo(dto.getName(), author);
        return this.bookMapper.toDTO(this.bookRepository.save(book));
    }

    @Override
    public void delete(Integer id) {
        log.info("Delete book id={}", id);
        ServiceValidationHelper.requireExists(this.bookRepository.findById(id),
                () -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));
        this.bookRepository.deleteById(id);
    }
}
