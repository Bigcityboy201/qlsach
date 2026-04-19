package truonggg.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.entity.Book;
import truonggg.entity.Review;
import truonggg.handler.BusinessException;
import truonggg.mapper.ReviewMapper;
import truonggg.repository.BookRepository;
import truonggg.repository.ReviewRepository;
import truonggg.response.ErrorCode;
import truonggg.response.PagedResult;
import truonggg.service.ReviewService;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Override
    public PagedResult<ReviewResponseDTO> getAll(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new BusinessException("Page must be >= 0 and size must be > 0", ErrorCode.BAD_REQUEST, "review");
        }
        Page<Review> reviews = this.reviewRepository.findAll(PageRequest.of(page, size));
        return PagedResult.from(reviews, this.reviewMapper.toDTOList(reviews.getContent()));
    }

    @Override
    public ReviewResponseDTO save(ReviewRequestDTO dto) {

        Book book = this.bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));

        Review review = this.reviewMapper.toEntity(dto, book);
        LocalDateTime now = LocalDateTime.now();
        review.setCreateAt(now);
        review.setUpdateAt(now);
        return this.reviewMapper.toDTO(this.reviewRepository.save(review));
    }

    @Override
    public ReviewResponseDTO update(ReviewRequestDTO dto, Integer id) {
        Book book = this.bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new BusinessException("Book not found!", ErrorCode.NOT_FOUND, "book"));
        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Review not found!", ErrorCode.NOT_FOUND, "review"));
        review.setBook(book);
        review.setContent(dto.getContent());
        review.setUpdateAt(LocalDateTime.now());
        return this.reviewMapper.toDTO(this.reviewRepository.save(review));
    }

    @Override
    public void delete(Integer id) {
        this.reviewRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Review not found!", ErrorCode.NOT_FOUND, "review"));
        this.reviewRepository.deleteById(id);
    }
}
