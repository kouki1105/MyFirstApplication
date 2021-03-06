package com.example.myfirstapplication.repository;

import java.util.List;

import com.example.myfirstapplication.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	 List<Comment> findByIdeaId(Long idea_id);
}