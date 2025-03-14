package com.example.vueProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.vueProject.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	

}
