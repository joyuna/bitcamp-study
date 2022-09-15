package com.bitcamp.board;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.URI;
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

public class MiniWebServer {

  public static void main(String[] args) throws Exception {
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");

    BoardDao boardDao = new MariaDBBoardDao(con);
    MemberDao memberDao = new MariaDBMemberDao(con);

    WelcomeHandler welcomeHandler = new WelcomeHandler();
    ErrorHandler errorHandler = new ErrorHandler();
    BoardHandler boardHandler = new BoardHandler(boardDao);

    // 클라이언트 요청을 처리하는 객체
    class MyHttpHandler implements HttpHandler {
      @Override
      public void handle(HttpExchange exchange) throws IOException {

        // 1-4) 클라이언트 요청이 들어왔을 때 마다 호출된다.
        System.out.println("클라이언트가 요청함!");


        URI requestUri = exchange.getRequestURI();
        String path = requestUri.getPath();
        String query = requestUri.getQuery();
        byte[] bytes = null;

        //1-5) 응답할 콘텐트 준비
        try (StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter)) {

          Map<String,String> paramMap = new HashMap<>();
          if (query != null && query.length() > 0) { // 예) no=1&title=aaaa&content=bbb
            String[] entries = query.split("&");
            for (String entry : entries) { // 예) no=1
              String[] kv = entry.split("=");
              paramMap.put(kv[0], kv[1]);
            }
          }

          System.out.println(paramMap);

          if (path.equals("/")) {
            welcomeHandler.service(paramMap, printWriter);
          } else if (path.equals("/board/list")) {
            boardHandler.list(paramMap,  printWriter);
          } else if (path.equals("/board/detail")) {
            boardHandler.detail(paramMap,  printWriter);
          } else if (path.equals("/board/update")) {
            boardHandler.update(paramMap,  printWriter);
          } else if (path.equals("/board/delete")) {
            boardHandler.delete(paramMap,  printWriter);
          } else if (path.equals("/board/list")) {
            boardHandler.delete(paramMap,  printWriter);
          } else {
            errorHandler.error(paramMap,  printWriter);
          }

          bytes = stringWriter.toString().getBytes("UTF-8"); 
        } catch (Exception e) {
          bytes = "요청 처리 중 오류 발생!!".getBytes("UTF-8"); 
          e.printStackTrace(); 
        }

        // 1-9) 보내는 콘텐트의 MIME 타입이 무엇인지 응답 헤더에 추가한다.
        Headers responseHeaders =exchange.getResponseHeaders();
        responseHeaders.add("Content-Type", "text/plain; charset=UTF-8");
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