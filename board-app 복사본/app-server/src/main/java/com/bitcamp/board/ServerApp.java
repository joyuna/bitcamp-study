package com.bitcamp.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.dao.MariaDBBoardDao;
import com.bitcamp.board.dao.MariaDBMemberDao;
import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.handler.BoardHandler;
import com.bitcamp.board.handler.MemberHandler;
import com.bitcamp.handler.Handler;
import com.bitcamp.util.BreadCrumb;





public class ServerApp {


  //8-4)중복되는 코드 밖으로 꺼내 & 
  // -> 8-5)static 붙여// 7-2) 메인메뉴 자리이동 -> 메인 메뉴 목록 준비 
  private String[] menus = {"게시판", "회원"}; // 11-2)static -> instance로 변경
  private int port;
  ArrayList<Handler> handlers = new ArrayList<>();

  public static void main(String[] args) { 
    try {
      ServerApp app = new ServerApp(8888);
      app.execute();

    } catch (Exception e) {
      System.out.println("서버 실행 오류!");
    }
  }

  public ServerApp(int port) throws Exception {
    this.port = port;

    // DAO 가 사용할 커넥션 객체 준비
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");

    // DAO 객체를 준비한다.
    BoardDao boardDao = new MariaDBBoardDao(con);
    MemberDao memberDao = new MariaDBMemberDao(con);

    handlers.add(new BoardHandler(boardDao));
    handlers.add(new MemberHandler(memberDao));
  }

  public void execute() { //11)리팩토링 ing //2) 메인 메서드 생성 
    try (ServerSocket serverSocket = new ServerSocket(this.port)) { // 3) 
      System.out.println("서버 실행 중...");

      // 핸들러를 담을 컬렉션을 준비한다.

      while (true) { // 5-1) 무한반복을 위해 삽입
        new Thread(new ServiceProcessor(serverSocket.accept())).start();
        System.out.println("클라이언트 접속!");
      }
      //  System.out.println("서버 종료!");
    } catch (Exception e) { //4) 
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
  /* 메인2 메서드 모두 막아 버리기
  public static void main2(String[] args) { // 1) main -> main2 로 바꾸면서 메인메서드가 아니게 

      System.out.println("[게시글 관리 클라이언트]");

      welcome();




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
  static void welcome(DataOutputStream out) throws Exception {
    try (StringWriter strOut = new StringWriter();  // 7-6) // 1))
        PrintWriter tempOut = new PrintWriter(strOut)) { //7-6) // 2))스트링라이터를 프린라이터와 연결
      // 3)) 괄호안 프린트스트림 넣어주기
      tempOut.println("[게시판 애플리케이션]"); 
      tempOut.println();    
      tempOut.println("환영합니다!");
      tempOut.println();
      out.writeUTF(strOut.toString());
    }
  }

  static void error(DataOutputStream out, Exception e) { // 7-1) 프린트메뉴로 이름 병
    try (StringWriter strOut = new StringWriter();  // 7-6) // 1))
        PrintWriter tempOut = new PrintWriter(strOut)) {
      tempOut.printf("실행 오류: %s\n", e.getMessage()); // system은 지워버리
      out.writeUTF(strOut.toString());
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }

  void printMainMenus(DataOutputStream out) throws Exception { // 7-1) 프린트메뉴로 이름 병
    try (StringWriter strOut = new StringWriter();  // 7-6) // 1))
        PrintWriter tempOut = new PrintWriter(strOut)) {

      tempOut.println(BreadCrumb.getBreadCrumbOfCurrentThread().toString()); // ** 요런코드에 계속 노출돼야행~

      // 7-3 자리는 그대로 -> 메인 목록 출력 
      for (int i = 0; i < menus.length; i++) {
        tempOut.printf("  %d: %s\n", i + 1, menus[i]); // system은 지워버리
      }
      // 7-3) 자리이동후 -> 소스 정정 -> 메인 메뉴 번호 입력을 요구하는 문장 출
      tempOut.printf("메뉴를 선택하세요[1..%d](quit: 종료) ", menus.length);
      out.writeUTF(strOut.toString());
    }
  }

  void processMainMenu(DataInputStream in, DataOutputStream out, String request) {
    try {
      int menuNo = Integer.parseInt(request);
      if (menuNo < 1 || menuNo > menus.length) {
        throw new Exception("메뉴 번호가 옳지 않습니다."); 
      }

      BreadCrumb breadcrumb = BreadCrumb.getBreadCrumbOfCurrentThread();
      breadcrumb.put(menus[menuNo - 1]);

      handlers.get(menuNo -1).execute(in, out);

      breadcrumb.pickUp();

      // 11-?) 하위 메뉴에서 빠져 나오면 현재의 경로를 출력한다.
      out.writeUTF(breadcrumb.toString());

    } catch (Exception e) {
      error(out, e);
    }
  }

  private class ServiceProcessor implements Runnable {

    Socket socket;

    public ServiceProcessor(Socket socket) {
      this.socket = socket;
    }


    @Override
    public void run() {
      try (Socket s = this.socket;
          DataOutputStream out = new DataOutputStream(s.getOutputStream());
          DataInputStream in = new DataInputStream(s.getInputStream())) {  

        BreadCrumb breadcrumb =new BreadCrumb(); // 현재 스레드 보관소에 저장된다.
        breadcrumb.put("메인");

        // 11-4) 접속하는 순간 클라이언트에게 환영 메시지를 보낸다.

        welcome(out);

        while(true) {
          // 11-5) 클라이언트가 보낸 요청 정보를 읽는다.
          String request = in.readUTF();
          if (request.equals("quit")) { // 6-1)
            break; 

          } else if (request.equals("menu")) {
            printMainMenus(out);

          } else {
            processMainMenu(in, out, request);
          }
        }
        System.out.println("클라이언트와 접속 종료!"); //5-2)주석막아버리기 -> 6-2) 내용변

      } catch (Exception e) {//6) 
        System.out.println("클라이언트와 통신하는 중 오류 발생!");
        e.printStackTrace();
      }
    }
  }
}



