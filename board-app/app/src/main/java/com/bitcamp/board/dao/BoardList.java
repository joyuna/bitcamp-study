package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Board;

// 게시글 목록을 관리하는 역할
//
public class BoardList extends ObjectList{

  private int no = 0;

  // 수퍼 클래스의 get() 메서드를 BoardList에 맞게 재정의 한다.
  // => 파라미터는 인덱스가 아닌 게시글 번호가 되게 한다.
  // => Overriding 이라 부른다.
  @Override
  public Board get(int boardNo) {
    for (int i = 0; i < this.length; i++) {
      //Object 배열에 실제 들어 있는 것은 Board 라고 컴파일러에게 알린다.
      Board board = (Board) this.list[i]; 

      if (board.no == boardNo) {
        return board;
      }
    }
    return null;
  }

  // 수퍼 클래스의 add()를 BoardList에 맞게끔 재정의한다.
  // => 파라미터로 받은 Board 인스턴스의 no 변수값을 설정한 다음 배열에 추가한다.
  // => 수퍼 Overriding 이라 부른다.

  @Override
  public void add(Object obj) {
    Board board = (Board) obj;
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














