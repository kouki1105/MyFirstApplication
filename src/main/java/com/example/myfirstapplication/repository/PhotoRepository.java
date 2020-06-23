package com.example.myfirstapplication.repository;

import com.example.myfirstapplication.entity.Photo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}