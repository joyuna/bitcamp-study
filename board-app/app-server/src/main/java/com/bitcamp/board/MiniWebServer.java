package com.bitcamp.board;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer; 

public class MiniWebServer {

  public static void main(String[] args) throws Exception {
    // 클라이언트 요청을 처리하는 객체
    class MyHttpHandler implements HttpHandler {
      @Override
      public void handle(HttpExchange exchange) throws IOException {

        // 1-4) 클라이언트 요청이 들어왔을 때 마다 호출된다.
        System.out.println("클라이언트가 요청함!");

        //1-5) 응답할 콘텐트 준비
        String response = "ABCabc123가각간";
        byte[] bytes = response.getBytes("UTF-8");

        // 1-9) 보내는 콘텐트의 MIME 타입이 무엇인지 응답 헤더에 추가한다.
        Headers responseHeaders =exchange.getResponseHeaders();
        responseHeaders.add("Content-Type", "text/plain; charset=UTF-8");
        // 1-6) 응답헤더 전송 
        exchange.sendResponseHeaders(200, bytes.length);
        // 1-7) 콘텐트 출력 스트림 준비
        OutputStream out = exchange.getResponseBody();// 1-8)
        out.write(response.getBytes());
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