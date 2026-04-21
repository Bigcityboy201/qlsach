package truonggg.dto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "id", "name", "createAt", "updateAt", "authorName" })
public class BookResponseDTO extends BaseResponseDTO {

    private String name;

    private String authorName;
}
