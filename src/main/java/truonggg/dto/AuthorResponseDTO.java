package truonggg.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "id", "name", "createAt", "updateAt", "totalBook" })
public class AuthorResponseDTO extends BaseResponseDTO {
    private String name;
    private Integer totalBook;
}
