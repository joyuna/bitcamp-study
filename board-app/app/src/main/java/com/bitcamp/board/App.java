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

    final int SIZE = 3; // SIZE라고 대문자로 써줘야 

    int[] no = new int[SIZE]; 
    String[] title = new String[SIZE];  // 블럭안에 있는 변수는 블럭이 끝난후 사라지기 때문에 블럭밖에 변수를 선언해준것이다. => while문안에 넣었더니 값이 초기화되지 않아
    String[] content = new String[SIZE]; // 변수에 초기값을 설정하지 않으면 컴파일 오류가 떠서 빈값을 넣어준것이다.
    String[] writer = new String[SIZE];
    String[] password = new String[SIZE];
    int[] viewCount = new int[SIZE];// 변수를 선언하고 변수값을 넣지않고 변수값을 꺼내려고 할때 문제가 생긴다.
    long[] createdDate = new long[SIZE];

    
    // 제목(title)변수를 1000개 만들어주는 string 문법이다. 안그럼 일일이 title1 = "", title2 = "", ... 이런식으로 만들어줘야하는 불편함이 있기 때문에 string 문법을 사용한다.
    int boardCount = 0; // wjwkdehls rptlrmfdml rotn

    while (true) { // 반복문
      System.out.println("메뉴:");
      System.out.println("  1: 게시글 목록");
      System.out.println("  2: 게시글 상세보기");
      System.out.println("  3: 게시글 등록");
      System.out.println();
      System.out.print("메뉴를 선택하세요[1..3](0: 종료) ");

      int menuNo = keyboardInput.nextInt(); //변수명은 소문자로 기재한다. 단어와 단어사이에 오는 단어는 첫단어를 대문자로 작성한다. 사용자가 키보드를 입력할때까지 기다린다.
      keyboardInput.nextLine(); // 입력한 숫자 뒤에 남아 있는 줄바꿈 코드 제거
      
      System.out.println("-----------------------------------");
      if (menuNo == 0) {
        break; // break 는 자기랑 가장 가까운 반복문이나 스위치 문을 만나면 그 블록을 탈출한다

      } else if (menuNo == 1) {
        System.out.println("[게시글 목록]");
        System.out.println("번호 제목 조회수 작성자 등록일");


        // 날짜 정보에서 값을 추출하여 특정 포맷의 문자열로 만들어줄 도구를 준비
        java.text.SimpleDateFormat formatter =
          new java.text.SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < boardCount; i++) {
        // 밀리초 데이터 ==> Date 도구함으로 날짜 정보를 설정 
        java.util.Date date = new java.util.Date(createdDate[i]);

        // 날짜 정보 ==> "yyyy-MM-dd" 형식의 문자열
        String dateStr = formatter.format(date);

        System.out.printf("%d\t%s\t%d\t%s\t%s\n", 
          no[i], title[i], viewCount[i], writer[i],dateStr);

          //i++; // 배열 인덱스를 증가시킨다.
        }

      } else if (menuNo == 2) {
        System.out.println("[게시글 상세보기]");

        System.out.print("조회할 게시글 번호?"); // 입력값이 숫자일거라는 전제하에
        String input = keyboardInput.nextLine(); //inputString => 처음부터 nextLine입력 해본당.!
        int boardNo = Integer.parseInt(input); // 문자 ok라고 입력시에 숫자
        //int boardNo = keyboardInput.nextInt();
        //keyboardInput.nextLine();


        //해당 번호의 게시글이 몇 번 배열에 들어 있는지 알아내기
        int boardIndex = -1; // 보드인덱스는 유효하지 않은 값을 넣는다. => -1을 넣은 이유
        for (int i = 0; i < boardCount; i++) {
          if (no[i] == boardNo) {
            boardIndex = i;
            break;
          }
          // i++;          
        }   

        // 사용자가 입력한 번호에 해당하는 게시글을 못 찾았다면
        if (boardIndex == -1) {
          System.out.println("해당 번호의 게시글이 없습니다!");
          continue;
        }


        System.out.printf("번호: %d\n", no[boardIndex]);
        System.out.printf("제목: %s\n", title[boardIndex]);
        System.out.printf("내용: %s\n", content[boardIndex]);
        System.out.printf("조회수: %d\n", viewCount[boardIndex]);
        System.out.printf("작성자: %s\n", writer[boardIndex]);

        // Date 도구함의 도구를 쓸 수 있도록 데이터를 준비시킨다.
        // new Date(밀리초)
        // => 지정한 밀리초를 가지고 날짜 관련 도구를 사용할 수 있도록 설정한다.
        // Date date
        //   => createdDate밀리초를가지고설정한날짜정보
        java.util.Date date = new java.util.Date(createdDate[boardIndex]);

        // Date 도구함을 통해 설정한 날짜 정보를 가지고 printf()를 실행한다.
        // %Y : date에 설정된 날짜 정보에서 년도만 추출한다.

        System.out.printf("등록일: %tY-%1$tm-%1$td-%1$tH:%1$tM\n", date); //1$첫번째에 있는 변수에서 데이터를 뽑아서 쓸거라는 뜻 
     
      } else if (menuNo == 3) {
        System.out.println("[게시글 등록]");

        // 게시글 등록할 때 배열의 크기를 초과하지 않았는지 검사한다.
        if (boardCount == SIZE) {
          System.out.println("게시글을 더이상 저장할 수 없습니다.");
          continue;
        }



        System.out.print("제목? ");
         title[boardCount] = keyboardInput.nextLine();

        System.out.print("내용? ");
         content[boardCount] = keyboardInput.nextLine();

        System.out.print("작성자? ");
         writer[boardCount] = keyboardInput.nextLine();

        System.out.print("암호? ");
         password[boardCount] = keyboardInput.nextLine();

        /*
        if (boardCount == 0) {
            no[boardCount] = 1;
         } else {
         no[boardCount] = no[boardCount -1] + 1 // 게시글 번호는 1로 하면 안돼!! 게시글 번호는 
        }
        */
        no[boardCount] = boardCount == 0 ? 1 : no[boardCount - 1] + 1;

        viewCount[boardCount] = 0; 
        createdDate[boardCount] = System.currentTimeMillis(); //1970년 기준으로 현재 명령어를 실행하는 시점의 시간을 나타내준다
        
        boardCount++;

      } else {
        System.out.println("메뉴 번호가 옳지 않습니다!");
      }
      
      System.out.println(); // 실행시 너무 붙어서 가독성이 떨어지니 메뉴를 처리한 후 빈 줄 출력 하려고 넣은 코드
    } // while

    System.out.println("안녕히가세요!!");
    keyboardInput.close();
  }
}
