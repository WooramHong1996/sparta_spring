package com.sparta.sparta_spring.repository;

import com.sparta.sparta_spring.entity.Blog;
import com.sparta.sparta_spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long>{
    // 요청사항 : 수정된순이 아니라, 글이 작성된 순으로 정렬바람.
    List<Blog> findAllByOrderByCreatedAtDesc();
    Optional<Blog> findByIdAndUser(Long id, User user);
}
