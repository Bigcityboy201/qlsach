package truonggg.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponseDTO {

    private Integer id;
    private String name;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Integer totalBook;
}
