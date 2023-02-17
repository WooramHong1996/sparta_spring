package com.sparta.sparta_spring.service;
import com.sparta.sparta_spring.dto.BlogDto;
import com.sparta.sparta_spring.dto.CommentDto;
import com.sparta.sparta_spring.entity.Blog;
import com.sparta.sparta_spring.entity.Comment;
import com.sparta.sparta_spring.entity.User;
import com.sparta.sparta_spring.jwt.JwtUtil;
import com.sparta.sparta_spring.repository.BlogRepository;
import com.sparta.sparta_spring.repository.CommentRepository;
import com.sparta.sparta_spring.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    private final JwtUtil jwtUtil;

    // 요구사항 1)  댓글 작성
    @Transactional
    public ResponseEntity<Object> createComment(Long id, CommentDto.Request commentRequestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        // token 의 정보를 임시로 저장하는 곳.
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
//            Optional<Blog> blog = blogRepository.findById(id);
//            if(blog.isEmpty()){
//                throw new IllegalArgumentException("글이 존재하지 않습니다.");
//            }
            Blog blog = blogRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Comment comment = commentRepository.saveAndFlush(Comment.builder()
                    .commentRequestDto(commentRequestDto)
                    .user(user)
                    .blog(blog)
                    .build());

            return ResponseEntity.ok()
                    .body(new CommentDto.Response(comment));
        } else {
            return ResponseEntity.badRequest()
                    .body(BlogDto.SendMessage.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("토큰이 존재하지 않습니다.")
                            .build());
        }
    }


//    // 요구사항 3)  선택한 게시글 조회
//    @Transactional(readOnly = true)
//    public ResponseEntity<Object> getComment(Long id) {
//        Comment comment = commentRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("해당글이 없습니다.")
//        );
//        return ResponseEntity.ok()
//                .body(new CommentDto.Response(comment));
//    }

    // 요구사항4. 선택한 댓글 수정
    @Transactional
    public ResponseEntity<Object> updateComment(Long id, CommentDto.Request commentRequestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Optional <User> user = userRepository.findByUsername(claims.getSubject());
            if (user.isEmpty()) {
                return responseException("사용자가 존재하지 않습니다.");
            }

            Optional<Comment> comment = commentRepository.findByIdAndUser(id, user.get());
            if (comment.isEmpty()) { // 일치하는 게시물이 없다면
                return responseException("본인이 작성한 게시글만 수정이 가능합니다.");
            }

            comment.get().update(commentRequestDto, user.get());

            return ResponseEntity.ok()
                    .body(new CommentDto.Response(comment.get()));
        } else {
            return ResponseEntity.badRequest()
                    .body(BlogDto.SendMessage.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("토큰이 존재하지 않습니다.")
                            .build());
        }
    }

    // 요구사항5. 선택한 게시글 삭제
    @Transactional
    public ResponseEntity<Object> deleteComment(Long id, HttpServletRequest request) {

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findByIdAndUser(id, user).orElseThrow(
                    () -> new NullPointerException("본인이 작성한 게시글이 아닙니다.")
            );

            commentRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body(BlogDto.SendMessage.builder()
                            .statusCode(HttpStatus.OK.value())
                            .msg("게시글 삭제 성공.")
                            .build()
                    );
        } else {
            return ResponseEntity.badRequest()
                    .body(BlogDto.SendMessage.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("토큰이 존재하지 않습니다.")
                            .build());
        }

    }

    private static ResponseEntity<Object> responseException(String message) {
        return ResponseEntity   // ResponseEntity 를 반환
                .badRequest()   // status : bad request
                .body(BlogDto.SendMessage.builder()  // body : SuccessResponseDto
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .msg(message)
                        .build());
    }

}
