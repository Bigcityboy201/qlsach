package truonggg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id",referencedColumnName = "id")
    private Book book;

    public static Review create(String content, Book book) {
        Review review = new Review();
        review.content = content;
        review.book = book;
        return review;
    }

    public void updateInfo(String content, Book book) {
        this.content = content;
        this.book = book;
        markUpdatedNow();
    }
}
