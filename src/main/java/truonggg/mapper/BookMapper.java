package truonggg.mapper;

import org.mapstruct.Mapper;
import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;
import truonggg.entity.Author;
import truonggg.entity.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    default Book toEntity(BookRequestDTO dto, Author author) {
        return Book.create(dto.getName(), author);
    }

    @org.mapstruct.Mapping(target = "authorName", source = "author.name")
    BookResponseDTO toDTO(Book book);

    List<BookResponseDTO> toDTOList(List<Book> books);

}
