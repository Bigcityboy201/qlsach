package truonggg.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;
import truonggg.entity.Author;
import truonggg.entity.Book;
import truonggg.mapper.BookMapper;
import truonggg.repository.AuthorRepository;
import truonggg.repository.BookRepository;
import truonggg.service.BookService;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<BookResponseDTO> getAll() {
        return this.bookMapper.toDTOList(this.bookRepository.findAll());
    }

    @Override
    public BookResponseDTO save(BookRequestDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Book book = bookMapper.toEntity(dto);

        book.setAuthor(author);

        Book saved = bookRepository.save(book);

        return bookMapper.toDTO(saved);
    }

    @Override
    public BookResponseDTO update(BookRequestDTO dto, Integer id) {
        Book book=this.bookRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found!"));
        Author author=this.authorRepository.findById(dto.getAuthorId()).orElseThrow(()->new RuntimeException("Author not found!"));
        book.setName(dto.getName());
        book.setAuthor(author);
        book.setUpdateAt(LocalDateTime.now());
        return this.bookMapper.toDTO(this.bookRepository.save(book));
    }

    @Override
    public void delete(Integer id) {
        Book book=this.bookRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found!"));
        this.bookRepository.deleteById(id);
    }
}
