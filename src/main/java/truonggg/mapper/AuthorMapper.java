package truonggg.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.entity.Author;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorRequestDTO dto);

    @Mapping(target = "totalBook", expression = "java(author.getBooks() == null ? 0 : author.getBooks().size())")
    AuthorResponseDTO toDTO(Author author);

    List<AuthorResponseDTO>toDTOList(List<Author>authors);
}
