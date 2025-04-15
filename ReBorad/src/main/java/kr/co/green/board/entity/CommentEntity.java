package kr.co.green.board.entity;



import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.green.member.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "COMMENTS", schema = "REBOARD")
public class CommentEntity {
	
	protected CommentEntity() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
	
	@Lob
    @Column(nullable = false)
    private String content;
	
	@Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSTS_NO", nullable = false)
    private BoardEntity post;  // 게시글과의 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private MemberEntity author;  // 작성자와의 관계
    
    @Builder
    public CommentEntity(Long no, String content, MemberEntity author, BoardEntity post, LocalDateTime createdAt) {
        this.no = no;
        this.content = content;
        this.author = author;
        this.post = post;
        this.createdAt = createdAt;
    }
    
}
