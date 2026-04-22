package truonggg.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteStatusRequestDTO {
    @NotNull(message = "Trạng thái xóa không được để trống")
    private Boolean deleted;
}

