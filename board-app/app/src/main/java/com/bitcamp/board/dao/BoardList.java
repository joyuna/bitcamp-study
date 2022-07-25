package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Board;

// 게시글 목록을 관리하는 역할
//
public class BoardList extends ObjectList{

  private int no = 0;

  // 수퍼 클래스의 get() 메서드는 인덱스로 항목을 찾는다.
  // 그래서 Board 객체를 다루기에 적합하지 않다.
  // 따라서 다음 메서드처럼 Board 객체를 조회하는데 적합한 메서드를 추가한다.
  // 이 메서드는 게시글 번호에 해당하는 Board 인스턴스를 찾아 리턴한다.
  public Board getByBoardNo(int boardNo) {

    for (int i = 0; i < this.length; i++) {
      //Object 배열에 실제 들어 있는 것은 Board 라고 컴파일러에게 알린다.
      Board board = (Board) this.list[i]; 

      if (board.no == boardNo) {
        return board;
      }
    }
    return null;
  }

  // 수퍼 클래스의 add(Object) 대신 Board 객체를 저장할 수 있도록
  // 같은 이름의 유사 기능을 수행하는 메서드를 추가 정의한다. => Overloading 이라 부른다.

  public void add(Board board) {
    if (this.boardCount == this.boards.length) {
      grow();
    }
    board.no = nextNo();
    // 수퍼 클래스의 add()를 사용하여 처리
    super.add(board);
  }
  @Override
  public boolean removeByBoardNo(int boardNo) {
    int boardIndex = -1;
    for (int i = 0; i < this.length; i++) {
      Board board = (Board) this.list[i];
      if (board.no == boardNo) {
        boardIndex = i;
        break;
      }
    }

    return this.remove(boardIndex);
  }


  private int nextNo() {
    return ++no;
  }
}














