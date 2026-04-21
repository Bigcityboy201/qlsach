package truonggg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Author extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Book>books;

    public static Author create(String name) {
        Author author = new Author();
        author.name = name;
        return author;
    }

    public void rename(String name) {
        this.name = name;
        markUpdatedNow();
    }
}
