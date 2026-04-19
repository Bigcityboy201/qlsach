package truonggg.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    private Author author;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review>reviews;
}
