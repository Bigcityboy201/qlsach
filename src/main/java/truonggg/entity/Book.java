package truonggg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Book extends BaseEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    private Author author;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review>reviews;

    public static Book create(String name, Author author) {
        Book book = new Book();
        book.name = name;
        book.author = author;
        return book;
    }

    public void updateInfo(String name, Author author) {
        this.name = name;
        this.author = author;
        markUpdatedNow();
    }
}
