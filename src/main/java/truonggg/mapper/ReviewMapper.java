package truonggg.mapper;

import org.mapstruct.Mapper;
import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.entity.Book;
import truonggg.entity.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    default Review toEntity(ReviewRequestDTO request, Book book) {
        return Review.create(request.getContent(), book);
    }

    @org.mapstruct.Mapping(target = "content", source = "content")
    @org.mapstruct.Mapping(target = "bookTitle", source = "book.name")
    @org.mapstruct.Mapping(target = "authorName", source = "book.author.name")
    ReviewResponseDTO toDTO(Review review);

    List<ReviewResponseDTO> toDTOList(List<Review> reviews);
}