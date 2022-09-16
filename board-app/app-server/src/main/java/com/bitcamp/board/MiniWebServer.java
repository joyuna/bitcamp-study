package com.bitcamp.board;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.dao.MariaDBBoardDao;
import com.bitcamp.board.dao.MariaDBMemberDao;
import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.handler.BoardHandler;
import com.bitcamp.board.handler.ErrorHandler;
import com.bitcamp.board.handler.WelcomeHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

// 1) 기본 웹 서버 만들기
// 2) 한글 콘텐트를 출력하기
// 3) HTML 콘텐트를 출력하기
// 4) 메인 화면을 출력하는 요청처리 객체를 분리하기
// 5) 요청 자원의 경로를 구분하여 처리하기
// 6) 게시글 요청 처리하기
//
public class MiniWebServer {

  public static void main(String[] args) throws Exception {
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");

    BoardDao boardDao = new MariaDBBoardDao(con);
    MemberDao memberDao = new MariaDBMemberDao(con);

    WelcomeHandler welcomeHandler = new WelcomeHandler();
    ErrorHandler errorHandler = new ErrorHandler();
    BoardHandler boardHandler = new BoardHandler(boardDao);

    class MyHttpHandler implements HttpHandler {
      @Override
      public void handle(HttpExchange exchange) throws IOException {
        System.out.println("클라이언트가 요청함!");

        URI requestUri = exchange.getRequestURI(); //
        String path = requestUri.getPath();
        // String query = requestUri.getQuery(); // getQuery가 디코딩을 제대로 수행하지 못한다.
        String query = requestUri.getRawQuery(); // 디코딩 없이 쿼리스트링을 그대로 리턴 받기 

        byte[] bytes = null;

        try (StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter)) {

          Map<String,String> paramMap = new HashMap<>();
          if (query != null && query.length() > 0) { // 예) no=1&title=aaaa&content=bbb
            String[] entries = query.split("&");
            for (String entry : entries) { // 예) no=1
              String[] kv = entry.split("=");
              // 웹 브라우저가 보낸 파라미터 값은 저장하기 전에 URL 디코딩 한다.
              paramMap.put(kv[0], URLDecoder.decode(kv[1], "UTF-8"));
            }
          }
          System.out.println(query);
          System.out.println(paramMap);

          if (path.equals("/")) {
            welcomeHandler.service(paramMap, printWriter);

          } else if (path.equals("/board/list")) {
            boardHandler.list(paramMap, printWriter);

          } else if (path.equals("/board/detail")) {
            boardHandler.detail(paramMap, printWriter);

          } else if (path.equals("/board/update")) {
            boardHandler.update(paramMap, printWriter);

          } else if (path.equals("/board/form")) {
            boardHandler.form(paramMap, printWriter);

          } else if (path.equals("/board/add")) {
            boardHandler.add(paramMap, printWriter);

          } else if (path.equals("/board/delete")) {
            boardHandler.delete(paramMap, printWriter);

          } else {
            errorHandler.error(paramMap, printWriter);
          }

          bytes = stringWriter.toString().getBytes("UTF-8");

        } catch (Exception e) {
          bytes = "요청 처리 중 오류 발생!".getBytes("UTF-8");
          e.printStackTrace(); // 서버 콘솔 창에 오류에 대한 자세한 내용을 출력한다.
        }

        // 1-9) 보내는 콘텐트의 MIME 타입이 무엇인지 응답 헤더에 추가한다.
        Headers responseHeaders =exchange.getResponseHeaders();
        responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
        // 1-6) 응답헤더 전송 
        exchange.sendResponseHeaders(200, bytes.length);
        // 1-7) 콘텐트 출력 스트림 준비
        OutputStream out = exchange.getResponseBody();// 1-8)
        out.write(bytes);
        out.close();
      }
    }

    HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
    server.createContext("/", new MyHttpHandler());
    server.setExecutor(null); // 1-1) HttpServer에 기본으로 설정되 있는 Executor 사용
    // 1-1)) Executor란 멀티 스레딩을 수행하는 객체이다.
    server.start(); // 1-2)HttpServer를 시작시킨 후 즉시 리턴한다.
    System.out.println("서버 시작!");
    // 1-3) main() 메서드 호출이 끝났다 하더라도
    // 1-3))내부에서 생성한 스레드(예: HttpServer)가 종료되지 않으면 JVM도 종료되지 않는다.
  }

}