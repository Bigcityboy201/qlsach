package truonggg.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteStatusRequestDTO {
    @NotNull
    private Boolean deleted;
}

