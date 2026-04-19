package truonggg.dto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {

    private Integer id;

    private String name;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String authorName;
}
