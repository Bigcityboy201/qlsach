package truonggg.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.dto.DeleteStatusRequestDTO;
import truonggg.entity.Author;
import truonggg.helper.ServiceValidationHelper;
import truonggg.handler.BusinessException;
import truonggg.mapper.AuthorMapper;
import truonggg.repository.AuthorRepository;
import truonggg.repository.BookRepository;
import truonggg.repository.ReviewRepository;
import truonggg.response.ErrorCode;
import truonggg.response.PagedResult;
import truonggg.service.AuthorService;
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public PagedResult<AuthorResponseDTO> getAll(int page, int size) {
        log.info("Get all authors with page={}, size={}", page, size);
        ServiceValidationHelper.validatePageAndSize(page, size, "author");
        Page<Author> authors = this.authorRepository.findAllByDeletedFalse(PageRequest.of(page, size));
        return PagedResult.from(authors, this.authorMapper.toDTOList(authors.getContent()));
    }

    @Override
    public AuthorResponseDTO save(AuthorRequestDTO dto) {
        log.info("Create author with name={}", dto.getName());
        Author author = this.authorMapper.toEntity(dto);
        author.markCreatedNow();
        return this.authorMapper.toDTO(this.authorRepository.save(author));
    }

    @Override
    public AuthorResponseDTO update(AuthorRequestDTO dto,Integer id) {
        log.info("Update author id={}", id);
        Author author = ServiceValidationHelper.requireExists(this.authorRepository.findByIdAndDeletedFalse(id),
                () -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));
        author.rename(dto.getName());
        return this.authorMapper.toDTO(this.authorRepository.save(author));
    }

    @Override
    @Transactional
    public void updateDeleteStatus(Integer id, DeleteStatusRequestDTO dto) {
        log.info("Update author delete status id={} deleted={}", id, dto.getDeleted());
        Author author = ServiceValidationHelper.requireExists(this.authorRepository.findByIdAndDeletedFalse(id),
                () -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));

        if (Boolean.TRUE.equals(dto.getDeleted())) {
            // cascade soft-delete: author -> books -> reviews (no custom query)
            var books = this.bookRepository.findAllByAuthorIdAndDeletedFalse(id);
            for (var book : books) {
                var reviews = this.reviewRepository.findAllByBookIdAndDeletedFalse(book.getId());
                reviews.forEach(r -> r.markDeletedNow());
                this.reviewRepository.saveAll(reviews);

                book.markDeletedNow();
            }
            this.bookRepository.saveAll(books);

            author.markDeletedNow();
        } else {
            author.restore();
        }

        this.authorRepository.save(author);
    }

}
