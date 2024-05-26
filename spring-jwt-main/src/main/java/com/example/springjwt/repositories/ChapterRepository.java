package com.example.springjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springjwt.model.Chapter;
@Repository
public interface ChapterRepository extends JpaRepository<Chapter,Integer> {
}
