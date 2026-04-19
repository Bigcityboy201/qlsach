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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Nested
    @DisplayName("getAll tests")
    class GetAllTests {
        @Test
        @DisplayName("Should return paged books when input valid")
        void shouldReturnPagedBooksWhenInputValid() {
            // Arrange
            Book book = TestDataBuilder.book("Clean Code");
            BookResponseDTO dto = TestDataBuilder.bookResponse("Clean Code");
            PageImpl<Book> page = new PageImpl<>(List.of(book), PageRequest.of(0, 10), 1);

            when(bookRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);
            when(bookMapper.toDTOList(page.getContent())).thenReturn(List.of(dto));

            // Act
            PagedResult<BookResponseDTO> result = bookService.getAll(0, 10);

            // Assert
            assertEquals(1, result.getTotalElements());
            assertEquals(1, result.getContent().size());
            verify(bookRepository).findAll(PageRequest.of(0, 10));
        }

        @Test
        @DisplayName("Should throw bad request when size is invalid")
        void shouldThrowBadRequestWhenSizeInvalid() {
            // Arrange
            int page = 0;
            int size = 0;

            // Act
            BusinessException ex = assertThrows(BusinessException.class, () -> bookService.getAll(page, size));

            // Assert
            assertEquals(ErrorCode.BAD_REQUEST, ex.getErrorCode());
            assertEquals("book", ex.getDomain());
            verifyNoInteractions(bookRepository);
        }
    }

    @Nested
    @DisplayName("save tests")
    class SaveTests {
        @Test
        @DisplayName("Should save successfully when author exists")
        void shouldSaveSuccessfullyWhenAuthorExists() {
            // Arrange
            BookRequestDTO request = TestDataBuilder.bookRequest("DDD", 1);
            Author author = Author.create("Evans");
            Book book = Book.create("DDD", author);
            BookResponseDTO response = TestDataBuilder.bookResponse("DDD");

            when(authorRepository.findById(1)).thenReturn(Optional.of(author));
            when(bookMapper.toEntity(request, author)).thenReturn(book);
            when(bookRepository.save(book)).thenReturn(book);
            when(bookMapper.toDTO(book)).thenReturn(response);

            // Act
            BookResponseDTO result = bookService.save(request);

            // Assert
            assertNotNull(result);
            assertNotNull(book.getCreateAt());
            assertNotNull(book.getUpdateAt());
            verify(bookRepository).save(book);
        }

        @Test
        @DisplayName("Should throw not found when author missing")
        void shouldThrowNotFoundWhenAuthorMissing() {
            // Arrange
            BookRequestDTO request = TestDataBuilder.bookRequest("DDD", 88);
            when(authorRepository.findById(88)).thenReturn(Optional.empty());

            // Act
            BusinessException ex = assertThrows(BusinessException.class, () -> bookService.save(request));

            // Assert
            assertEquals(ErrorCode.NOT_FOUND, ex.getErrorCode());
            assertEquals("author", ex.getDomain());
            verify(bookRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("update tests")
    class UpdateTests {
        @Test
        @DisplayName("Should update successfully when book and author exist")
        void shouldUpdateSuccessfullyWhenBookAndAuthorExist() {
            // Arrange
            BookRequestDTO request = TestDataBuilder.bookRequest("Refactoring", 2);
            Author author = Author.create("Martin Fowler");
            Book book = Book.create("Old", author);
            BookResponseDTO response = TestDataBuilder.bookResponse("Refactoring");

            when(bookRepository.findById(1)).thenReturn(Optional.of(book));
            when(authorRepository.findById(2)).thenReturn(Optional.of(author));
            when(bookRepository.save(book)).thenReturn(book);
            when(bookMapper.toDTO(book)).thenReturn(response);

            // Act
            BookResponseDTO result = bookService.update(request, 1);

            // Assert
            assertNotNull(result);
            assertEquals("Refactoring", book.getName());
            assertNotNull(book.getUpdateAt());
        }

        @Test
        @DisplayName("Should throw not found when target book missing")
        void shouldThrowNotFoundWhenTargetBookMissing() {
            // Arrange
            BookRequestDTO request = TestDataBuilder.bookRequest("Refactoring", 2);
            when(bookRepository.findById(999)).thenReturn(Optional.empty());

            // Act
            BusinessException ex = assertThrows(BusinessException.class, () -> bookService.update(request, 999));

            // Assert
            assertEquals(ErrorCode.NOT_FOUND, ex.getErrorCode());
            assertEquals("book", ex.getDomain());
            verify(bookRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("delete tests")
    class DeleteTests {
        @Test
        @DisplayName("Should delete successfully when book exists")
        void shouldDeleteSuccessfullyWhenBookExists() {
            // Arrange
            when(bookRepository.findById(7)).thenReturn(Optional.of(TestDataBuilder.book("x")));

            // Act
            bookService.delete(7);

            // Assert
            verify(bookRepository).deleteById(7);
        }

        @Test
        @DisplayName("Should throw not found when book does not exist")
        void shouldThrowNotFoundWhenBookDoesNotExist() {
            // Arrange
            when(bookRepository.findById(7)).thenReturn(Optional.empty());

            // Act
            BusinessException ex = assertThrows(BusinessException.class, () -> bookService.delete(7));

            // Assert
            assertEquals(ErrorCode.NOT_FOUND, ex.getErrorCode());
            assertEquals("book", ex.getDomain());
            verify(bookRepository, never()).deleteById(any());
        }
    }

    private static final class TestDataBuilder {
        private TestDataBuilder() {
        }

        private static BookRequestDTO bookRequest(String name, Integer authorId) {
            BookRequestDTO dto = new BookRequestDTO();
            dto.setName(name);
            dto.setAuthorId(authorId);
            return dto;
        }

        private static BookResponseDTO bookResponse(String name) {
            BookResponseDTO dto = new BookResponseDTO();
            dto.setName(name);
            return dto;
        }

        private static Book book(String name) {
            return Book.create(name, Author.create("Any Author"));
        }
    }
}
