package truonggg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {

    @NotBlank(message = "Tiêu đề sách không được để trống!")
    @Size(min = 1,max = 255,message = "Tiêu đề tối đa 255 ki tự!")
    private String name;

    @NotNull(message = "Author không được để trống!")
    @Positive(message = "Author phải là số dương!")
    private Integer authorId;
}
