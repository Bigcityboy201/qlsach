package truonggg.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.entity.Author;
import truonggg.entity.Book;
import truonggg.entity.Review;
import truonggg.handler.BusinessException;
import truonggg.mapper.ReviewMapper;
import truonggg.repository.BookRepository;
import truonggg.repository.ReviewRepository;
import truonggg.response.ErrorCode;
import truonggg.response.PagedResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewMapper reviewMapper;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Nested
    @DisplayName("getAll tests")
    class GetAllTests {
        @Test
        @DisplayName("Should return paged reviews when request valid")
        void shouldReturnPagedReviewsWhenRequestValid() {
            // Arrange
            Review review = TestDataBuilder.review("Great");
            ReviewResponseDTO dto = TestDataBuilder.reviewResponse("Great");
            PageImpl<Review> page = new PageImpl<>(List.of(review), PageRequest.of(0, 10), 1);
            when(reviewRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);
            when(reviewMapper.toDTOList(page.getContent())).thenReturn(List.of(dto));

            // Act
            PagedResult<ReviewResponseDTO> result = reviewService.getAll(0, 10);

            // Assert
            assertEquals(1, result.getContent().size());
        }
    }

    @Nested
    @DisplayName("save/update tests")
    class SaveUpdateTests {
        @Test
        @DisplayName("Should save review when book exists")
        void shouldSaveReviewWhenBookExists() {
            // Arrange
            Book book = TestDataBuilder.book("Refactoring");
            ReviewRequestDTO request = TestDataBuilder.reviewRequest(1, "Nice");
            Review review = Review.create("Nice", book);
            ReviewResponseDTO response = TestDataBuilder.reviewResponse("Nice");
            when(bookRepository.findById(1)).thenReturn(Optional.of(book));
            when(reviewMapper.toEntity(request, book)).thenReturn(review);
            when(reviewRepository.save(review)).thenReturn(review);
            when(reviewMapper.toDTO(review)).thenReturn(response);

            // Act
            ReviewResponseDTO result = reviewService.save(request);

            // Assert
            assertNotNull(result);
            assertNotNull(review.getCreateAt());
            assertNotNull(review.getUpdateAt());
        }

        @Test
        @DisplayName("Should throw not found when book missing on save")
        void shouldThrowNotFoundWhenBookMissingOnSave() {
            // Arrange
            ReviewRequestDTO request = TestDataBuilder.reviewRequest(999, "x");
            when(bookRepository.findById(999)).thenReturn(Optional.empty());

            // Act
            BusinessException ex = assertThrows(BusinessException.class, () -> reviewService.save(request));

            // Assert
            assertEquals(ErrorCode.NOT_FOUND, ex.getErrorCode());
            assertEquals("book", ex.getDomain());
            verify(reviewRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("delete edge tests")
    class DeleteEdgeTests {
        @Test
        @DisplayName("Should throw not found when deleting missing review")
        void shouldThrowNotFoundWhenDeletingMissingReview() {
            // Arrange
            when(reviewRepository.findById(2)).thenReturn(Optional.empty());

            // Act
            BusinessException ex = assertThrows(BusinessException.class, () -> reviewService.delete(2));

            // Assert
            assertEquals(ErrorCode.NOT_FOUND, ex.getErrorCode());
            assertEquals("review", ex.getDomain());
            verify(reviewRepository, never()).deleteById(any());
        }
    }

    private static final class TestDataBuilder {
        private static Book book(String title) {
            return Book.create(title, Author.create("Any Author"));
        }

        private static Review review(String content) {
            return Review.create(content, book("Any book"));
        }

        private static ReviewRequestDTO reviewRequest(Integer bookId, String content) {
            ReviewRequestDTO dto = new ReviewRequestDTO();
            dto.setBookId(bookId);
            dto.setContent(content);
            return dto;
        }

        private static ReviewResponseDTO reviewResponse(String content) {
            ReviewResponseDTO dto = new ReviewResponseDTO();
            dto.setContent(content);
            return dto;
        }
    }
}
