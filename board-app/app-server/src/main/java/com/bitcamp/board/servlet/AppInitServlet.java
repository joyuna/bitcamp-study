package com.bitcamp.board.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.MariaDBBoardDao;

// 이 서블릿은 다른 서블릿이 사용할 객체를 준비하는 일을 한다.
// 
@WebServlet(
    value="/init",
    loadOnStartup = 1)
public class AppInitServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public AppInitServlet() {
    ServletContext ctx = getServletContext() ;
  }
  @Override
  public void init() throws ServletException {
    // 톰캣서버 --> init(ServletConfig0 --> init()

    System.out.println("공유 자원을 준비 중!");

    try {
      Class.forName("org.mariadb.jdbc.Driver");

      Connection con = DriverManager.getConnection(
          "jdbc:mariadb://localhost:3306/studydb","study","1111");

      // 생성자에서 getServletContext()를 호출하면 오류 발생!
      // 왜? 
      // - ServletContext는 ServletConfig 객체를 통해 꺼낼 수가 있는데,
      // - ServletConfig는 아직 주입되지 않은 상태이다.
      // ServletConfig 객체가 언제 주입되는가?
      // - 생성자 다음에 호출되는 init()가 호출될 때 ServletConfig 객체가 주입된다.
      //
      ServletContext ctx = this.getServletContext();
      ctx.setAttribute("boardDao", new MariaDBBoardDao(con));
      ctx.setAttribute("memberDao", new MariaDBBoardDao(con));
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  // 이 서블릿의 역할은 다른 서블릿이 사용할 자원을 준비하는 것이기 때문에
  // 굳이 요청을 처리하는 메서드를 정의할 필요가 없다. 

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    // 콘텐트를 출력하는 출력 스트림을 준비하기 전에
    // 어떤 인코딩으로 콘텐트를 출력할 것인지 먼저 설정해야 한다.
    res.setContentType("text/html; charset=UTF-8");

    PrintWriter out = res.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>웹 애플리케이션 준비!</h1>");
    out.println("</body>");
    out.println("</html>");
  }
}
