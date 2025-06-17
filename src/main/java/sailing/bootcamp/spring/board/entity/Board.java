package sailing.bootcamp.spring.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;   // 게시글 아이디

    @Column(nullable = false)
    private String title;   // 게시글 제목

    @Column(nullable = false)
    private String content; // 게시글 내용

}
