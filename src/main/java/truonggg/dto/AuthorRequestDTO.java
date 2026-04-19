package truonggg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequestDTO {

    @NotBlank(message = "Tên tác giả không được để trống!")
    @Size(min = 2,max = 100,message = "Tên tác giả phải từ 2 đến 100 kí tự!")
    private String name;
}
