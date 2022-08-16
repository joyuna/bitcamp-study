/*
 * board 데이터 처리
 */
package com.bitcamp.board.servlet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.servlet.Servlet;

public class BoardServlet implements Servlet {

  // 게시글 목록을 관리할 객체 준비
  private BoardDao boardDao;

  public BoardServlet(String dataName) {
    /*    boardDao = new BoardDao(filename);

    try {
      boardDao.load();
    } catch (Exception e) {
      System.out.printf("%s 파일 로딩 중 오류 발생!\n", filename);
      //      e.printStackTrace();
    } 
     */
  }

  // 템플릿 메서드 패턴(template method pattern) 
  //   - 수퍼 클래스의 execute()에서 동작의 전체적인 흐름을 정의하고(틀을 만들고),
  //   - 서브 클래스의 service()에서 동작을 구제척으로 정의한다.(세부적인 항목을 구현한다)
  @Override
  public void service(DataInputStream in, DataOutputStream out) {
    try {

      String command = in.readUTF();

      switch (command) {
        case "findAll":
          out.writeUTF("success");
          break;
        case "findByNo":
          out.writeUTF("success");
          break;
        case "insert": 
          out.writeUTF("success");
          break;
        case "update": 
          out.writeUTF("success"); 
          break;

        case "delete": 
          out.writeUTF("success");
          break;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}




