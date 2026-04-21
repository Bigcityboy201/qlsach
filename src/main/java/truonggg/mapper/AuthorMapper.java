package truonggg.mapper;

import org.mapstruct.Mapper;
import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.entity.Author;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    default Author toEntity(AuthorRequestDTO dto) {
        return Author.create(dto.getName());
    }

    @org.mapstruct.Mapping(target = "totalBook", expression = "java(author.getBooks() == null ? 0 : author.getBooks().size())")
    AuthorResponseDTO toDTO(Author author);

    List<AuthorResponseDTO>toDTOList(List<Author>authors);
}
