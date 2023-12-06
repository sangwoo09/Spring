package com.weavus.board.repo;

import com.weavus.board.entity.Board1;
import com.weavus.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Board1Repository extends JpaRepository<Board1, Integer> {

}
