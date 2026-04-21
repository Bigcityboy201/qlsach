package truonggg.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;
import truonggg.dto.DeleteStatusRequestDTO;
import truonggg.entity.Author;
import truonggg.entity.Book;
import truonggg.entity.Review;
import truonggg.helper.ServiceValidationHelper;
import truonggg.handler.BusinessException;
import truonggg.mapper.BookMapper;
import truonggg.repository.AuthorRepository;
import truonggg.repository.BookRepository;
import truonggg.repository.ReviewRepository;
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
    private final ReviewRepository reviewRepository;

    @Override
    public PagedResult<BookResponseDTO> getAll(int page, int size) {
        log.info("Get all books with page={}, size={}", page, size);
        ServiceValidationHelper.validatePageAndSize(page, size, "book");
        Page<Book> books = this.bookRepository.findAllByDeletedFalse(PageRequest.of(page, size));
        return PagedResult.from(books, this.bookMapper.toDTOList(books.getContent()));
    }

    @Override
    public BookResponseDTO save(BookRequestDTO dto) {
        log.info("Create book with name={} and authorId={}", dto.getName(), dto.getAuthorId());
        Author author = ServiceValidationHelper.requireExists(authorRepository.findByIdAndDeletedFalse(dto.getAuthorId()),
                () -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));

        Book book = bookMapper.toEntity(dto, author);
        book.markCreatedNow();

        Book saved = bookRepository.save(book);

        return bookMapper.toDTO(saved);
    }

    @Override
    public BookResponseDTO update(BookRequestDTO dto, Integer id) {
        log.info("Update book id={} with authorId={}", id, dto.getAuthorId());
        Book book = ServiceValidationHelper.requireExists(this.bookRepository.findByIdAndDeletedFalse(id),
                () -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));
        Author author = ServiceValidationHelper.requireExists(this.authorRepository.findByIdAndDeletedFalse(dto.getAuthorId()),
                () -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));
        book.updateInfo(dto.getName(), author);
        return this.bookMapper.toDTO(this.bookRepository.save(book));
    }

    @Override
    @Transactional
    public void updateDeleteStatus(Integer id, DeleteStatusRequestDTO dto) {
        log.info("Update book delete status id={} deleted={}", id, dto.getDeleted());
        Book book = ServiceValidationHelper.requireExists(this.bookRepository.findByIdAndDeletedFalse(id),
                () -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));

        if (Boolean.TRUE.equals(dto.getDeleted())) {
            // cascade soft-delete reviews first
            var reviews = this.reviewRepository.findAllByBookIdAndDeletedFalse(id);
            reviews.forEach(Review::markDeletedNow);
            this.reviewRepository.saveAll(reviews);

            book.markDeletedNow();
        } else {
            book.restore();
        }

        this.bookRepository.save(book);
    }
}
