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
import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.dto.DeleteStatusRequestDTO;
import truonggg.entity.Author;
import truonggg.handler.BusinessException;
import truonggg.mapper.AuthorMapper;
import truonggg.repository.AuthorRepository;
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
class AuthorServiceImplTest {

    @Mock
    private AuthorMapper authorMapper;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Nested
    @DisplayName("getAll tests")
    class GetAllTests {
        @Test
        @DisplayName("Should return paged authors when request valid")
        void shouldReturnPagedAuthorsWhenRequestValid() {
            // Arrange
            Author author = Author.create("Tolkien");
            AuthorResponseDTO response = TestDataBuilder.authorResponse("Tolkien");
            PageImpl<Author> page = new PageImpl<>(List.of(author), PageRequest.of(0, 10), 1);
            when(authorRepository.findAllByDeletedFalse(PageRequest.of(0, 10))).thenReturn(page);
            when(authorMapper.toDTOList(page.getContent())).thenReturn(List.of(response));

            // Act
            PagedResult<AuthorResponseDTO> result = authorService.getAll(0, 10);

            // Assert
            assertEquals(1, result.getContent().size());
            assertEquals(1, result.getTotalElements());
        }

        @Test
        @DisplayName("Should throw bad request when page negative")
        void shouldThrowBadRequestWhenPageNegative() {
            // Arrange
            int page = -1;
            int size = 10;

            // Act
            BusinessException ex = assertThrows(BusinessException.class, () -> authorService.getAll(page, size));

            // Assert
            assertEquals(ErrorCode.BAD_REQUEST, ex.getErrorCode());
            assertEquals("author", ex.getDomain());
        }
    }

    @Nested
    @DisplayName("save tests")
    class SaveTests {
        @Test
        @DisplayName("Should save author and set timestamps")
        void shouldSaveAuthorAndSetTimestamps() {
            // Arrange
            AuthorRequestDTO request = TestDataBuilder.authorRequest("Asimov");
            Author author = Author.create("Asimov");
            AuthorResponseDTO response = TestDataBuilder.authorResponse("Asimov");
            when(authorMapper.toEntity(request)).thenReturn(author);
            when(authorRepository.save(author)).thenReturn(author);
            when(authorMapper.toDTO(author)).thenReturn(response);

            // Act
            AuthorResponseDTO result = authorService.save(request);

            // Assert
            assertNotNull(result);
            assertNotNull(author.getCreateAt());
            assertNotNull(author.getUpdateAt());
        }
    }

    @Nested
    @DisplayName("update/delete edge tests")
    class UpdateDeleteEdgeTests {
        @Test
        @DisplayName("Should throw not found when updating missing author")
        void shouldThrowNotFoundWhenUpdatingMissingAuthor() {
            // Arrange
            when(authorRepository.findByIdAndDeletedFalse(9)).thenReturn(Optional.empty());

            // Act
            BusinessException ex = assertThrows(BusinessException.class,
                    () -> authorService.update(TestDataBuilder.authorRequest("x"), 9));

            // Assert
            assertEquals(ErrorCode.NOT_FOUND, ex.getErrorCode());
            assertEquals("author", ex.getDomain());
            verify(authorRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw not found when deleting missing author")
        void shouldThrowNotFoundWhenDeletingMissingAuthor() {
            // Arrange
            when(authorRepository.findByIdAndDeletedFalse(9)).thenReturn(Optional.empty());
            DeleteStatusRequestDTO req = new DeleteStatusRequestDTO();
            req.setDeleted(true);

            // Act
            BusinessException ex = assertThrows(BusinessException.class, () -> authorService.updateDeleteStatus(9, req));

            // Assert
            assertEquals(ErrorCode.NOT_FOUND, ex.getErrorCode());
            assertEquals("author", ex.getDomain());
            verify(authorRepository, never()).save(any());
        }
    }

    private static final class TestDataBuilder {
        private static AuthorRequestDTO authorRequest(String name) {
            AuthorRequestDTO dto = new AuthorRequestDTO();
            dto.setName(name);
            return dto;
        }

        private static AuthorResponseDTO authorResponse(String name) {
            AuthorResponseDTO dto = new AuthorResponseDTO();
            dto.setName(name);
            return dto;
        }
    }
}
