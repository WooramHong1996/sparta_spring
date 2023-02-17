package com.sparta.sparta_spring.dto;

import com.sparta.sparta_spring.entity.Blog;
import com.sparta.sparta_spring.entity.Comment;
import com.sparta.sparta_spring.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//이너 dto로 수정(하면서 안에 코드들 다 수정했어요 ㅠㅠ)
public class CommentDto {

    @Getter
    public static class Request {
        private String comment;

    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String username;
//        private Blog blog;
        private String comment;
        private LocalDateTime createdat;
        private LocalDateTime modifiedat;

        public Response(Comment comment) {
            this.id = comment.getId();
            this.username = comment.getUser().getUsername();
//            this.blog = comment.getBlog();
            this.comment = comment.getComment();
            this.createdat = comment.getCreatedAt();
            this.modifiedat = comment.getModifiedAt();
        }
    }
}
