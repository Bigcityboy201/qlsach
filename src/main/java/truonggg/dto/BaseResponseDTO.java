package truonggg.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseResponseDTO {
    private Integer id;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
