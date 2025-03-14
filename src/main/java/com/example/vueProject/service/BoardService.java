package com.example.vueProject.service;
import com.example.vueProject.entity.Board;
import com.example.vueProject.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BoardService {
	
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }
}
