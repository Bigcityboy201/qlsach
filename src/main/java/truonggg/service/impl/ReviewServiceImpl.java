package truonggg.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import truonggg.dto.DeleteStatusRequestDTO;
import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.entity.Book;
import truonggg.entity.Review;
import truonggg.helper.ServiceValidationHelper;
import truonggg.handler.BusinessException;
import truonggg.mapper.ReviewMapper;
import truonggg.repository.BookRepository;
import truonggg.repository.ReviewRepository;
import truonggg.response.ErrorCode;
import truonggg.response.PagedResult;
import truonggg.service.ReviewService;
@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Override
    public PagedResult<ReviewResponseDTO> getAll(int page, int size) {
        log.info("Get all reviews with page={}, size={}", page, size);
        ServiceValidationHelper.validatePageAndSize(page, size, "review");
        Page<Review> reviews = this.reviewRepository.findAllByDeletedFalse(PageRequest.of(page, size));
        return PagedResult.from(reviews, this.reviewMapper.toDTOList(reviews.getContent()));
    }

    @Override
    public ReviewResponseDTO save(ReviewRequestDTO dto) {
        log.info("Create review for bookId={}", dto.getBookId());

        Book book = ServiceValidationHelper.requireExists(this.bookRepository.findByIdAndDeletedFalse(dto.getBookId()),
                () -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));

        Review review = this.reviewMapper.toEntity(dto, book);
        review.markCreatedNow();
        return this.reviewMapper.toDTO(this.reviewRepository.save(review));
    }

    @Override
    public ReviewResponseDTO update(ReviewRequestDTO dto, Integer id) {
        log.info("Update review id={} for bookId={}", id, dto.getBookId());
        Book book = ServiceValidationHelper.requireExists(this.bookRepository.findByIdAndDeletedFalse(dto.getBookId()),
                () -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));
        Review review = ServiceValidationHelper.requireExists(this.reviewRepository.findByIdAndDeletedFalse(id),
                () -> new BusinessException("Review not found!", ErrorCode.NOT_FOUND, "review"));
        review.updateInfo(dto.getContent(), book);
        return this.reviewMapper.toDTO(this.reviewRepository.save(review));
    }

    @Override
    @Transactional
    public void updateDeleteStatus(Integer id, DeleteStatusRequestDTO dto) {
        log.info("Update review delete status id={} deleted={}", id, dto.getDeleted());
        Review review = ServiceValidationHelper.requireExists(this.reviewRepository.findByIdAndDeletedFalse(id),
                () -> new BusinessException("Review not found!", ErrorCode.NOT_FOUND, "review"));

        if (Boolean.TRUE.equals(dto.getDeleted())) {
            review.markDeletedNow();
        } else {
            review.restore();
        }

        this.reviewRepository.save(review);
    }
}
