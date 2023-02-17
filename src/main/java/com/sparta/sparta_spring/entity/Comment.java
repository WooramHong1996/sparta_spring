package com.sparta.sparta_spring.entity;

import com.sparta.sparta_spring.dto.BlogDto;
import com.sparta.sparta_spring.dto.CommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 클래스에 Property에 대한 getter 메서드를 자동으로 생성해주는 것.
@Entity // Blog라는 클래스가 JPA Entity 클래스로 사용될 것이라는 것, 즉 데이터베이스에 저장할 데이터의 구조를 말한다.
@NoArgsConstructor // 기본생성자를 자동으로 생성할 수 있게하는 Lombok 에서 사용하는 것.
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "user_name",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "blog_id",nullable = false)
    private Blog blog;

    @Column
    private String comment;

    @Builder
    public Comment (CommentDto.Request commentRequestDto, User user, Blog blog){
        this.user = user;
        this.blog = blog;
        this.comment = commentRequestDto.getComment();
    }
    public void update (CommentDto.Request commentRequestDto, User user)
    {
        this.comment = commentRequestDto.getComment();
        this.user = user;
    }
}
