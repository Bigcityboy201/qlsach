package truonggg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {

    @NotNull(message = "Book không được để trống")
    @Positive(message = "Book phải là số dương")
    private Integer bookId;

    @NotBlank(message = "Nội dung review không được để trống")
    @Size(min = 1, max = 1000, message = "Nội dung review tối đa 1000 ký tự")
    private String content;
}