/*
 * 게시글 메뉴 처리 클래스
 */
package com.bitcamp.board;

import java.util.Date;

public class BoardHandler {



  // 모든 게시판이 공유하는 데이터는 클래스 변수에 저장한다.
  // why? 클래스 변수는 클래스를 로딩할대 한번만 생성되기 때문이다.
  static final int DEFAULT_SIZE = 3;

  // 각 게시판이 별도로 관리해야 할 데이터는 인스턴스 변수에 저장한다.
  //Why?  인스턴스 변수는 게시판 별로 생성할 수 있기 때문이다.
  int boardCount = 0; // 저장된 게시글의 개수
  // Board 인스턴스의 주소를 저장할 레퍼런스 배열을 만든다.
  Board[] boards = new Board[DEFAULT_SIZE];

  void execute() {
    // App 클래스에서 이 메서드를 호출할때 BoardHandler의 인스턴스 주소를 줄것이다.
    // 그 주소는 this라는 내장 변수에 보관된다.

    while (true) {
      System.out.println("게시판:");
      System.out.println("  1: 목록");
      System.out.println("  2: 상세보기");
      System.out.println("  3: 등록");
      System.out.println("  4: 삭제");
      System.out.println("  5: 변경");
      System.out.println();

      int menuNo = Prompt.inputInt("메뉴를 선택하세요[1..5](0: 이전) ");
      displayHeadline(); //인스턴스멤버로 쓰지 않으면 구냥 클래스로 두세영 //무엇을 인스턴스로 할건지 스태틱으로 할건지 ..

      // this라는 내장 변수에 보관이 되면 다른 인스턴스 메서드를 호출할때 this에 보관된 인스턴스 주소를 사용한다.
      // App이라는 클래스에서 보드핸들러의 execute 를 호출할때 보드핸들러의 익스큐드 주소를 줄꺼다. 그럼 그 주소를 프로 세스 리스트를 호출할때 
      // 인스턴스 주소가 필요하니 this라는 주소가 필요하다 왜냐면 그안에 익스큐트 주소가 저장돼있기 때문에



      switch (menuNo) {
        case 0: return;
        case 1: BoardHandler.processList(); break;
        case 2: BoardHandler.processDetail(); break;
        case 3: BoardHandler.processInput(); break;
        case 4: BoardHandler.processDelete(); break;
        case 5: BoardHandler.processUpdate(); break;
        default: System.out.println("메뉴 번호가 옳지 않습니다!");
      }

      displayBlankLine();
    } // 게시판 while
  }

  static void displayHeadline() {
    System.out.println("=========================================");
  }

  static void displayBlankLine() {
    System.out.println(); // 메뉴를 처리한 후 빈 줄 출력
  }

  void processList() {
    // 인스턴스 메서드는 호출될대 넘겨 받은 인스턴스 주소를
    // this라는 내장 변수에 보관한다.
    //인스턴스 메서드를 쓰는데 this를 안붙이면 자동으로 this가 붙는다. 
    // ==> this를 안붙여도 되는게 아니라 안붙이면 컴파일러가 대신 붙이는것뿐이다.


    java.text.SimpleDateFormat formatter = 
        new java.text.SimpleDateFormat("yyyy-MM-dd");

    System.out.println("[게시글 목록]");
    System.out.println("번호 제목 조회수 작성자 등록일");

    for (int i = 0; i < this.boardCount; i++) {  
      Board board = boards[i];

      // 밀리초 데이터 ==> Date 도구함으로 날짜 정보를 설정
      Date date = new Date(board.createdDate);

      // 날짜 정보 ==> "yyyy-MM-dd" 형식의 문자열
      String dateStr = formatter.format(date); 

      System.out.printf("%d\t%s\t%d\t%s\t%s\n",
          board.no, board.title, board.viewCount, board.writer, dateStr);
    }

  }

  void processDetail() {
    System.out.println("[게시글 상세보기]");

    int boardNo = Prompt.inputInt("조회할 게시글 번호? ");

    // 해당 번호의 게시글이 몇 번 배열에 들어 있는지 알아내기
    Board board = null;
    for (int i = 0; i < this.boardCount; i++) {
      if (boards[i].no == boardNo) {
        board = boards[i];
        break;
      }
    }

    // 사용자가 입력한 번호에 해당하는 게시글을 못 찾았다면
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    System.out.printf("번호: %d\n", board.no);
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("조회수: %d\n", board.viewCount);
    System.out.printf("작성자: %s\n", board.writer);
    java.util.Date date = new java.util.Date(board.createdDate);
    System.out.printf("등록일: %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);

  }

  void processInput() {
    System.out.println("[게시글 등록]");

    // 배열의 크기를 촤가하면 배열 크기를 50% 증가시킨다.
    if (boardCount == boards.length) {
      // 새로 만들 배열의 크기를 계산한다.
      int newSize = boards.length + (boards.length >> 1); //boards.length /2 이거는 비트이동연산자를 통해 boards.length >> 1로 쓸수 있다.

      // 새 배열 준비
      Board[] newArray = new Board[newSize];

      // 기존 배열의 값을 새 배열에 넣는다.
      for (int i = 0; i < boards.length; i++) {
        newArray[i] = boards[i]; 
      }
      // 기존 배열(주소)를 버리고 새 배열(주소)를 사용한다.
      boards = newArray; // boards는 배열주소를 담는 레퍼런스다.

    }

    Board board = new Board();

    board.title = Prompt.inputString("제목? ");
    board.content = Prompt.inputString("내용? ");
    board.writer = Prompt.inputString("작성자? ");
    board.password = Prompt.inputString("암호? ");

    board.no = this.boardCount == 0 ? 1 : this.boards[this.boardCount - 1].no + 1;
    board.viewCount = 0;
    board.createdDate = System.currentTimeMillis();

    // 새로 만든 인스턴스 주소를 레퍼런스 배열에 저장한다.
    this.boards[this.boardCount] = board;

    this.boardCount++;

    System.out.println("게시글을 등록했습니다.");
  }

  void processDelete() {
    System.out.println("[게시글 삭제]");

    int boardNo = Prompt.inputInt("삭제할 게시글 번호? ");

    // 해당 번호의 게시글이 몇 번 배열에 들어 있는지 알아내기
    int boardIndex = -1;
    for (int i = 0; i < boardCount; i++) {
      if (boards[i].no == boardNo) {
        boardIndex = i;
        break;
      }
    }

    // 사용자가 입력한 번호에 해당하는 게시글을 못 찾았다면
    if (boardIndex == -1) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    // 삭제할 게시글의 다음 항목을 앞으로 당긴다.
    for (int i = boardIndex + 1; i < boardCount; i++) {
      boards[i - 1] = boards[i];
    }

    // 게시글 개수를 1개 줄이고 맨 마지막 레퍼런스는 null 로 비운다.
    boards[--boardCount] = null;

    System.out.println("삭제하였습니다.");

  }

  void processUpdate() {
    System.out.println("[게시글 변경]");

    int boardNo = Prompt.inputInt("변경할 게시글 번호? ");

    // 해당 번호의 게시글이 몇 번 배열에 들어 있는지 알아내기
    Board board = null;
    for (int i = 0; i < boardCount; i++) {
      if (boards[i].no == boardNo) {
        board = boards[i];
        break;
      }
    }

    // 사용자가 입력한 번호에 해당하는 게시글을 못 찾았다면
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    String newTitle = Prompt.inputString("제목?(" + board.title + ") ");
    String newContent = Prompt.inputString(String.format("내용?(%s) ", board.content));

    String input = Prompt.inputString("변경하시겠습니까?(y/n) ");
    if (input.equals("y")) {
      board.title = newTitle;
      board.content = newContent;
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }
}




