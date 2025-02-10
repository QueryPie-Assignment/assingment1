package assignment.application.infrastructure.book.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOK")
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
	@Id
	@Column(name = "book_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookSeq;

	@Column(nullable = false, name = "title")
	private String title;

	@Column(nullable = false, name = "author")
	private String author;

	@Column(nullable = false, name = "publish_date")
	private LocalDate publishedDate;

	@Column(nullable = false, name = "is_available", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isAvailable;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "book_tag")
	private BookTag bookTag;
}
