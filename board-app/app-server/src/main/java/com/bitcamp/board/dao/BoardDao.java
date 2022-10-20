package com.bitcamp.board.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.bitcamp.board.domain.AttachedFile;
import com.bitcamp.board.domain.Board;

@Mapper
public interface BoardDao {

  int insert(Board board);

  Board findByNo(int no); // findByNo1,2 삭제하고 3만 남겨둠

  int update(Board board);

  int delete(int no);

  int deleteByMember(int memberNo);

  List<Board> findAll();

  int insertFiles(Board board);

  AttachedFile findFileByNo(int fileNo);

  List<AttachedFile> findFilesByBoard(int boardNo);

  int deleteFile(int fileNo);

  int deleteFiles(int boardNo);

  int deleteFilesByMemberBoards(int memberNo);
}














