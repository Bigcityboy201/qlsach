package truonggg.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    protected void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    protected void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public void markCreatedNow() {
        LocalDateTime now = LocalDateTime.now();
        this.createAt = now;
        this.updateAt = now;
    }

    public void markUpdatedNow() {
        this.updateAt = LocalDateTime.now();
    }
}
