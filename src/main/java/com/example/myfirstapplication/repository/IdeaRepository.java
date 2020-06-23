package com.example.myfirstapplication.repository;

import com.example.myfirstapplication.entity.Idea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
	
}