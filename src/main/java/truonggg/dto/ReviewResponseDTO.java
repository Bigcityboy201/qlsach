package truonggg.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "id", "content", "createAt", "updateAt", "bookTitle", "authorName" })
public class ReviewResponseDTO extends BaseResponseDTO {
    private String content;
    private String bookTitle;
    private String authorName;
}