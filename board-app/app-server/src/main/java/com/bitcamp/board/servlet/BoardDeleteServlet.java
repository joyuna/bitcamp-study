/*
 * 게시글 메뉴 처리 클래스
 */
package com.bitcamp.board.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.BoardDao;

// @WebServlet(value="/board/delete")
public class BoardDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  BoardDao boardDao;

  @Override
  public void init() throws ServletException {
    boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='1; url=list'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 삭제</h1>");

    try {
      int no = Integer.parseInt(req.getParameter("no"));

      if (boardDao.delete(no) == 0) {
        out.println("<p>해당 번호의 게시글이 없습니다.</p>");

      } else {
        out.println("<p>해당 게시글을 삭제했습니다.</p>");
      }
    } catch (Exception e) {
      out.println("<p>실행 중 오류 발생!</p>");
    }

    out.println("</body>");
    out.println("</html>");

  }
}




