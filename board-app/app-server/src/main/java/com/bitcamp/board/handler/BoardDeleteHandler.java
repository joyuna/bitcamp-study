/*
 * 게시글 메뉴 처리 클래스
 */
package com.bitcamp.board.handler;

import java.io.PrintWriter;
import java.util.Map;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.Board;
import com.bitcamp.servlet.Servlet;

public class BoardDeleteHandler implements Servlet{

  private BoardDao boardDao;

  public BoardDeleteHandler(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='3; url=list'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 삭제</h1>");

    int no = Integer.parseInt(paramMap.get("no"));

    if (boardDao.delete(no) == 0) {
      out.println("<p>해당 번호의 게시글이 없습니다.</p>");

    } else {
      out.println("<p>해당 게시글을 삭제했습니다.</p>");
    }

    out.println("</body>");
    out.println("</html>");

  }

  public void update(Map<String,String> paramMap, PrintWriter out) throws Exception {

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='3; url=list'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 변경</h1>");

    Board board = new Board();
    board.no = Integer.parseInt(paramMap.get("no"));
    board.title = paramMap.get("title");
    board.content = paramMap.get("content");

    if (boardDao.update(board) == 0) {
      out.println("<p>해당 번호의 게시글이 없습니다.</p>");

    } else {
      out.println("<p>해당 게시글을 변경했습니다.</p>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}





