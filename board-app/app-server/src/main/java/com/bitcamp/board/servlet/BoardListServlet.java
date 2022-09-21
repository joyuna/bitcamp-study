/*
 * 게시글 메뉴 처리 클래스
 */
package com.bitcamp.board.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.dao.MariaDBBoardDao;
import com.bitcamp.board.domain.Board;
import com.bitcamp.servlet.annotation.WebServlet;

@WebServlet(value="/board/list")
public class BoardListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardDao boardDao;

  public BoardListServlet() throws Exception { // (BoardDao boardDao)기본생성자 못받는
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");
    boardDao = new MariaDBBoardDao(con);
  } 
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    super.doGet(req, resp);
  }
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<style>");
    out.println("tr:hover {");
    out.println("  background-color: navy;");
    out.println("  color: white;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글</h1>");
    out.println("<a href='form'>새 글</a>");
    out.println("<table border='1'>");
    out.println("  <tr>");
    out.println("    <th>번호</th>");
    out.println("    <th>제목</th>");
    out.println("    <th>조회수</th>");
    out.println("    <th>작성자</th>");
    out.println("    <th>등록일</th>");
    out.println("  </tr>");

    List<Board> boards = boardDao.findAll();
    for (Board board : boards) {
      out.println("<tr>");
      out.printf("  <td>%d</td>", board.no);
      out.printf("  <td><a href='detail?no=%d'>%s</a></td>", board.no, board.title);
      out.printf("  <td>%d</td>", board.viewCount);
      out.printf("  <td>%d</td>", board.memberNo);
      out.printf("  <td>%s</td>", board.createdDate);
      out.println("</tr>");
    }

    out.println("</table>");
    out.println("<p><a href='/'>메인</a></p>");
    out.println("</body>");
    out.println("</html>");
  }

}




