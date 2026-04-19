package truonggg.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.entity.Book;
import truonggg.entity.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "request.content")
    @Mapping(target = "createAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "book", source = "book")
    Review toEntity(ReviewRequestDTO request, Book book);

    @Mapping(target = "content", source = "content")
    @Mapping(target = "bookTitle", source = "book.name")
    @Mapping(target = "authorName", source = "book.author.name")
    ReviewResponseDTO toDTO(Review review);

    List<ReviewResponseDTO> toDTOList(List<Review> reviews);
}