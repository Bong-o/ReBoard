package kr.co.green.board.data.entity;

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
import kr.co.green.member.data.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "POSTS", schema = "REBOARD")
public class BoardEntity {
	
	protected BoardEntity() {}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int views = 0;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private MemberEntity author;
    
    @Builder
    public BoardEntity(Long no, String title, String content, MemberEntity author, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public void update(String title, String content, LocalDateTime updatedAt) {
    	this.title = title;
    	this.content = content;
    	this.updatedAt = updatedAt;
    }

}
