package truonggg.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {

    private Integer id;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String bookTitle;
    private String authorName;
}