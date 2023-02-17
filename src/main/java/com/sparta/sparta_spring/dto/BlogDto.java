package com.sparta.sparta_spring.dto;

import com.sparta.sparta_spring.entity.Blog;
import com.sparta.sparta_spring.dto.CommentDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//이너 dto로 수정(하면서 안에 코드들 다 수정했어요 ㅠㅠ)
public class BlogDto {

    @Getter
    public static class Request{
//        private String author;
        private String content;
        private String title;
//        private String password;

    }

    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long id;
        private String title;
        private String content;
        private String username;

        //        private String author;
        private LocalDateTime createdat;
        private LocalDateTime modifiedat;

        private List<CommentDto.Response> commentList = new ArrayList<>();

        public Response(Blog blog) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.content = blog.getContent();
            this.username = blog.getUser().getUsername();
//            this.author = blog.getAuthor();
            this.createdat = blog.getCreatedAt();
            this.modifiedat = blog.getModifiedAt();
        }

        public Response(Blog blog, List<CommentDto.Response> commentList) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.content = blog.getContent();
            this.username = blog.getUser().getUsername();
            this.createdat = blog.getCreatedAt();
            this.modifiedat = blog.getModifiedAt();
            this.commentList = commentList;
        }
    }

    @Getter
    public static class SendMessage{
        private String msg;
        private int statusCode;
        @Builder
        public SendMessage(String msg, int statusCode)
        {
            this.msg = msg;
            this.statusCode = statusCode;
        }
    }
}
