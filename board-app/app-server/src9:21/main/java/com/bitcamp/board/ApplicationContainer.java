package com.bitcamp.board;

import static org.reflections.scanners.Scanners.TypesAnnotated;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import com.bitcamp.board.handler.ErrorHandler;
import com.bitcamp.servlet.Servlet;
import com.bitcamp.servlet.annotation.Repository;
import com.bitcamp.servlet.annotation.WebServlet;

// 1) 기본 웹 서버 만들기
// 2) 한글 콘텐트를 출력하기
// 3) HTML 콘텐트를 출력하기
// 4) 메인 화면을 출력하는 요청처리 객체를 분리하기
// 5) 요청 자원의 경로를 구분하여 처리하기
// 6) 게시글 요청 처리하기
// 7) URL 디코딩 처리
// 8) 회원 요청 처리하기
//

public class ApplicationContainer {

  Map<String,Object> objMap = new HashMap<>(); // 제네릭문법 키를 서블릿에서 오브젝트로 변경 => map안에 dao.servelt 다 담으려
  // 생성자 생성
  Reflections reflections;
  ErrorHandler errorHandler = new ErrorHandler();

  public ApplicationContainer(String packageName) throws Exception {
    reflections = new Reflections(packageName);

    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");

    // DAO 객체를 찾아 맵에 보관한다.
    Set<Class<?>> classes = reflections.get(TypesAnnotated.with(Repository.class).asClass());
    for (Class<?> clazz : classes) {
      String objName= clazz.getAnnotation(Repository.class).value();
      Constructor<?> constructor = clazz.getConstructor(Connection.class);
      objMap.put(objName, constructor.newInstance(con));
    }

    // 서블릿 객체를 찾아 맵에 보관한다.
    Set<Class<?>> servlets = reflections.get(TypesAnnotated.with(WebServlet.class).asClass());
    for (Class<?> servlet : servlets) {
      // 서블릿 클래스의 붙은 WebServlet 애노테이션으로부터 path 를 꺼낸다.
      String servletPath = servlet.getAnnotation(WebServlet.class).value();

      Constructor<?> constructor = servlet.getConstructors()[0];
      Parameter[] params = constructor.getParameters();

      if (params.length == 0) { 
        objMap.put(servletPath, constructor.newInstance());

      } else { 
        Object argument = findObject(objMap,params[0].getType());
        if (argument != null) { 
          objMap.put(servletPath, constructor.newInstance(argument));
        }
      }
    } 
  }
  public void execute(String path, String query, PrintWriter out) throws Exception {
    // query string을 분석하여 파라미터 값을 맵에 저장한다.
    Map<String,String> paramMap = new HashMap<>();
    if (query != null && query.length() > 0) { // 예) no=1&title=aaaa&content=bbb
      String[] entries = query.split("&");
      for (String entry : entries) { // 예) no=1
        String[] kv = entry.split("=");
        paramMap.put(kv[0], URLDecoder.decode(kv[1], "UTF-8"));
      }
    }
    //  System.out.println(paramMap);

    // 경로에 해당하는 서블릿 객체를 찾아 실행한다.
    Servlet servlet = (Servlet)objMap.get(path);
    if (servlet != null) {
      servlet.service(paramMap, out);
    } else {
      errorHandler.service(paramMap, out);
    }
  }// execute()

  private static Object findObject(Map<String, Object> objMap, Class<?> type) {

    Collection<Object> values = objMap.values();
    for (Object value : values) {
      if (type.isInstance(value)) { 
        return value; 
      }
    }
    return null;
  }
}
