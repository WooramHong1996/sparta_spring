package com.sparta.sparta_spring.dto;

import com.sparta.sparta_spring.entity.Blog;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
    public static class Response{
        private Long id;
        private String title;
        private String content;
//        private String author;
        private LocalDateTime createdat;
        private LocalDateTime modifiedat;

        public Response(Blog blog) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.content = blog.getContent();
//            this.author = blog.getAuthor();
            this.createdat = blog.getCreatedAt();
            this.modifiedat = blog.getModifiedAt();
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
