/*
 *게시판 관리 애플리케이션
 *비트캠프-20220704
 */
package com.bitcamp.board;

public class App {
  public static void main(String[] args) {

    System.out.println("[게시판 애플리케이션]"); // 코드가 길어지더라도 코드를 직관적으로 읽을수 있는 코드가 좋다.
    System.out.println();
    System.out.println("환영합니다!");
    System.out.println();

    java.util.Scanner keyboardInput = new java.util.Scanner(System.in);

    String title = "";  // 블럭안에 있는 변수는 블럭이 끝난후 사라지기 때문에 블럭밖에 변수를 선언해준것이다. => while문안에 넣었더니 값이 초기화되지 않아
    String content = ""; // 변수에 초기값을 설정하지 않으면 컴파일 오류가 떠서 빈값을 넣어준것이다.
    String writer = "";
    String password = "";


    String[] titles = new String[1000]; 
    // 제목(title)변수를 1000개 만들어주는 string 문법이다. 안그럼 일일이 title1 = "", title2 = "", ... 이런식으로 만들어줘야하는 불편함이 있기 때문에 string 문법을 사용한다.


    while (true) { // 반복문
      System.out.println("메뉴:");
      System.out.println("  1: 게시글 목록");
      System.out.println("  2: 게시글 상세보기");
      System.out.println("  3: 게시글 등록");
      System.out.println();
      System.out.print("메뉴를 선택하세요[1..3](0: 종료) ");

      int menuNo = keyboardInput.nextInt(); //변수명은 소문자로 기재한다. 단어와 단어사이에 오는 단어는 첫단어를 대문자로 작성한다. 사용자가 키보드를 입력할때까지 기다린다.
      keyboardInput.nextLine(); // 입력한 숫자 뒤에 남아 있는 줄바꿈 코드 제거

      if (menuNo == 0) {
        break; // break 는 자기랑 가장 가까운 반복문이나 스위치 문을 만나면 그 블록을 탈출한다
      } else if (menuNo == 1) {
        System.out.println("[게시글 목록]");
        System.out.println("번호 제목 조회수 작성자 등록일");

        System.out.print(1);
        System.out.print("\t");
        System.out.print("제목입니다1");
        System.out.print('\t');
        System.out.print(20 + '\t');
        System.out.print("홍길동\t");
        System.out.print("2022-07-08\r\n");

        System.out.print(2 + "\t" + "제목입니다2\t" + 11 + "\t" + "홍길동\t" + "2022-07-08\n");

        System.out.println(3 + "\t제목입니다3\t" + 31 + "\t" + "임꺽정\t2022-07-08");
        System.out.printf("%d\t%s\t%d\t%s\t%s\n", 4, "제목입니다4", 45, "유관순", "2022-07-08");
      } else if (menuNo == 2) {
        System.out.println("게시글 상세보기");

        System.out.printf("번호: %d\n", 1);
        System.out.printf("제목: %s\n", title);
        System.out.printf("내용: %s\n", content);
        System.out.printf("조회수: %d\n", 100);
        System.out.printf("작성자: %s\n", writer);
        System.out.printf("등록일: %s\n", "2022-07-08");
      } else if (menuNo == 3) {
        System.out.println("[게시글 등록]");

        System.out.print("제목? ");
         title = keyboardInput.nextLine();

        System.out.print("내용? ");
         content = keyboardInput.nextLine();

        System.out.print("작성자? ");
         writer = keyboardInput.nextLine();

        System.out.print("암호? ");
         password = keyboardInput.nextLine();

      } else {
        System.out.println("메뉴 번호가 옳지 않습니다!");
      }

      System.out.println(); // 실행시 너무 붙어서 가독성이 떨어지니 메뉴를 처리한 후 빈 줄 출력 하려고 넣은 코드
    } // while

    System.out.println("안녕히가세요!!");
    keyboardInput.close();
  }
}
