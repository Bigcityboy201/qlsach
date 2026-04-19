package truonggg.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;
import truonggg.entity.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "author", ignore = true) // set trong service vì cần authorRepository
    @Mapping(target = "reviews", ignore = true)
    Book toEntity(BookRequestDTO dto);

    // Entity -> DTO
    @Mapping(target = "authorName", source = "author.name")
    BookResponseDTO toDTO(Book book);

    // List Entity -> List DTO
    List<BookResponseDTO> toDTOList(List<Book> books);

}
