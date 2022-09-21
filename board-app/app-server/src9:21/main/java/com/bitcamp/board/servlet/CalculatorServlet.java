package com.bitcamp.board.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.servlet.annotation.WebServlet;

@WebServlet(value="/calc")
public class CalculatorServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {


    resp.setContentType("text/html; charset=UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("</head>");
    out.println("<body>");

    try {
      int a = Integer.parseInt(req.getParameter("a"));
      int b = Integer.parseInt(req.getParameter("b"));
      String op = req.getParameter("op");

      switch (op) {
        case "+": out.println(a + b); break;
        case "-": out.println(a - b); break;
        case "*": out.println(a * b); break;
        case "/": out.println(a / b); break;
        default: out.println("해당 연산자를 지원하지 않습니다.");
      }
    } catch (Exception e) {
      out.println("</body>");
      out.println("</html>");
    }
  }
}

