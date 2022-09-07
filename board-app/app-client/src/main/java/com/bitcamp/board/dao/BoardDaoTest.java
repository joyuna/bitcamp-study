package com.bitcamp.board.dao;

import java.util.List;
import com.bitcamp.board.domain.Board;


public class BoardDaoTest {

  public static void main(String[] args) throws Exception {
    MariaDBBoardDao dao = new MariaDBBoardDao();
    List<Board> list = dao.findAll();
    for (Board b : list) {
      System.out.println(b);
    }
    System.out.println("----------------");

    //    Board board = new Board();
    //    board.title = "aaaa";
    //    board.content = "bbbb";
    //    board.memberNo = 2;
    //    dao.insert(board);
    //
    //    dao.delete(6);

    Board board = new Board();
    board.no = 12;
    board.title = "xxxx";
    board.content = "xxx@test.com";
    board.password = "2222";
    dao.update(board);

    Board board2 = dao.findByNo(12);
    System.out.println(board2);

    list = dao.findAll();
    for (Board b : list) {
      System.out.println(b);

    }
    System.out.println("----------------");
  }
}
