package com.bitcamp.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;



public class ServerApp {


  public static Stack<String> breadcrumbMenu = new Stack<>();

  //8-4)중복되는 코드 밖으로 꺼내 & 
  // -> 8-5)static 붙여// 7-2) 메인메뉴 자리이동 -> 메인 메뉴 목록 준비 
  static String[] menus = {"게시판", "회원"};    


  public static void main(String[] args) { // 2) 메인 메서드 생성 
    try (ServerSocket serverSocket = new ServerSocket(8888)) { // 3) 

      System.out.println("서버 실행 중...");

      while (true ) { // 5-1) 무한반복을 위해 삽입
        Socket socket = serverSocket.accept();

        new Thread(() -> {
          // 스레드를 시작하는 순간, 별도의 실행 흐름에서 병행으로 실행된다.
          try ( // 5) 
              // 7) 입력받는거 클라이언트에서 복붙 
              DataOutputStream out = new DataOutputStream(socket.getOutputStream());
              DataInputStream in = new DataInputStream(socket.getInputStream())) {  
            System.out.println("클라이언트 접속!");

            // 8-6) 대이동 & 대삭제 & 추가
            // 접속 후 환영 메세지와 메인 메뉴 출력
            try (StringWriter strOut = new StringWriter();  // 7-6) // 1))
                PrintWriter tempOut = new PrintWriter(strOut);) { //7-6) // 2))스트링라이터를 프린라이터와 연결
              welcome(tempOut);
              printMainMenus(tempOut); // 7-4)
              out.writeUTF(strOut.toString()); // 7-5) 버퍼에 출력내용이 쌓여 있는 상태 
            }

            while (true) {
              //4-1))) 클라이언트가 보낸 값을 그대로 돌려준다.
              String request = in.readUTF();
              if (request.equals("quit")) { // 6-1)
                break; 
              }

              // 8-7) 응답 내용을 출력할 임시 출력 스트림 준비,// 클라이언트에게 응답한다.
              try (
                  StringWriter strOut = new StringWriter();  
                  PrintWriter tempOut = new PrintWriter(strOut);) { 


                try { // 8-10) 예외 발생을 처리하기 위해
                  //8-3) 추가 입력
                  int mainMenuNo = Integer.parseInt(request);
                  //8-2) 이동
                  if (mainMenuNo >= 1 && mainMenuNo <= menus.length) {
                    tempOut.println("해당 기능을 준비 중입니다.");
                  } else { //8-9)
                    tempOut.println("해당 번호의 메뉴가 옳지 않습니다!");
                  }

                } catch (Exception e) {
                  tempOut.println("입력 값이 옳지 않습니다.");
                }

                // 메뉴 번호로 Handler 레퍼런스에 들어있는 객체를 찾아 실행한다.

                tempOut.println();
                printMainMenus(tempOut);// 8-8)
                out.writeUTF(strOut.toString());
              }
            }

            System.out.println("클라이언트와 접속 종료!"); //5-2)주석막아버리기 -> 6-2) 내용변

          } catch (Exception e) {//6) 
            System.out.println("클라이언트와 통신하는 중 오류 발생!");
            e.printStackTrace();
          }
        }).start(); 
      }// while


      // Thread t = new Thread();// 1))) 스레드 객체 생성하고 스타트를 호출하면 런메서드를 별도로 만들거야 그걸 생성자에 주면 스사트를 하라하면 스타트는 파라미터를 받아 별도로 실행된다.
      // t.start();


      //  System.out.println("서버 종료!");
    } catch (Exception e) { //4) 
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
  /* 메인2 메서드 모두 막아 버리기
  public static void main2(String[] args) { // 1) main -> main2 로 바꾸면서 메인메서드가 아니게 
    try (// DAO 가 사용할 커넥션 객체 준비
        Connection con = DriverManager.getConnection(
            "jdbc:mariadb://localhost:3306/studydb","study","1111");
        ) {
      System.out.println("[게시글 관리 클라이언트]");

      welcome();

      // DAO 객체를 준비한다.
      MariaDBMemberDao memberDao = new MariaDBMemberDao(con);
      MariaDBBoardDao boardDao = new MariaDBBoardDao(con);



      // "메인" 메뉴의 이름을 스택에 등록한다.
      breadcrumbMenu.push("메인");



      loop: while (true) {

        printTitle();
        printMenus(menus);
        System.out.println();

        try {




          // 메뉴에 진입할 때 breadcrumb 메뉴바에 그 메뉴를 등록한다.
          breadcrumbMenu.push(menus[mainMenuNo - 1]);



          breadcrumbMenu.pop();

        } catch (Exception ex) {
          System.out.println("입력 값이 옳지 않습니다.");
        }


      } // while
      Prompt.close();

      System.out.println("종료!");

    } catch (Exception e) {
      System.out.println("시스템 오류 발생!");
      e.printStackTrace();
    }
  }
   */
  static void welcome(PrintWriter out) throws Exception { // 3)) 괄호안 프린트스트림 넣어주기
    out.println("[게시판 애플리케이션]"); 
    out.println();    
    out.println("환영합니다!");
    out.println();
  }

  static void printMainMenus(PrintWriter out) { // 7-1) 프린트메뉴로 이름 병

    // 7-3 자리는 그대로 -> 메인 목록 출력 
    for (int i = 0; i < menus.length; i++) {
      out.printf("  %d: %s\n", i + 1, menus[i]); // system은 지워버리
    }
    // 7-3) 자리이동후 -> 소스 정정 -> 메인 메뉴 번호 입력을 요구하는 문장 출
    out.printf("메뉴를 선택하세요[1..%d](quit: 종료) ", menus.length);
  }

  protected static void printTitle() {
    StringBuilder builder = new StringBuilder();
    for (String title : breadcrumbMenu) {
      if (!builder.isEmpty()) {
        builder.append(" > ");
      }
      builder.append(title);
    }
    System.out.printf("%s:\n", builder.toString());
  }
}