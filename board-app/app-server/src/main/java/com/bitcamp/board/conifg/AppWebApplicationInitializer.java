package com.bitcamp.board.conifg;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import com.bitcamp.board.filter.AdminCheckFilter;
import com.bitcamp.board.filter.LoginCheckFilter;

// 서블릿 컨테이너
// ===> SppringServletContainerInitializer.onStartup() 호출
//   ===> WebApplicationInitializer 구현체의 onStartup() 호출

// @MultipartConfig(maxFileSize = 1024 * 1024 * 10) 
public class AppWebApplicationInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException{
    System.out.println("AppWebApplicationInitializer.onStartup()");

    // 웹관련 컴포넌트를 다룰 수 있는 기능이 포함된 스프링 IoC 컨테이너 준비
    AnnotationConfigWebApplicationContext iocContainer =
        new AnnotationConfigWebApplicationContext();
    iocContainer.register(AppConfig.class);

    // 웹 애플리케이션의 루트 경로를 ServletContext 보관소에 저장해둔다. => 저장해두면 JSP에서 사용함.
    servletContext.setAttribute("contextPath", servletContext.getContextPath());

    // 자바 코드로 서블릿 객체를 직접 생성하여 서버에 등록하기
    DispatcherServlet servlet = new DispatcherServlet(iocContainer);
    Dynamic config = servletContext.addServlet("app", servlet);
    config.setLoadOnStartup(1); // 웹 애플리케이션을 시작할 대 프론트 컨트롤러를 자동 생성한다.
    config.addMapping("/app/*");

    // 1) 멀티파트 설정 정보를 애노테이션에서 가져오기
    // config.setMultipartConfig(new MultipartConfigElement(
    //      this.getClass().getAnnotation(MultipartConfig.class)));
    // 2) 멀티파트 설정 정보를 직접 지정하기
    config.setMultipartConfig(new MultipartConfigElement(
        System.getProperty("java.io.tmpdir"), // 클라이언트가 보낸 파일을 임시 저장할 디렉토리
        1024 * 1024 * 5,  // 한 개 파일의 최대 크기
        1024 * 1024 * 10, // 첨부 파일을 포함한 전체 요청 데이터의 크기
        1024 * 1024 // 첨부 파일 데이터를 일시적으로 보관할 버퍼 크기 
        ));

    // "app" 이름의 프론트 컨트롤러에 필터를 붙인다.
    CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8");
    FilterRegistration.Dynamic filterConfig = servletContext.addFilter("CharacterEncodingFilter", filter);
    filterConfig.addMappingForServletNames(
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE),  // EumSet 상수들의 배열 
        false, 
        "app");

    // 특정 URL에 필터를 붙인다.
    AdminCheckFilter adminFilter = new AdminCheckFilter();
    FilterRegistration.Dynamic adminFilterConfig = servletContext.addFilter("AdminCheckFilter", adminFilter);
    adminFilterConfig.addMappingForUrlPatterns(
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE),  // EumSet 상수들의 배열 
        false, 
        "/app/member/*");

    LoginCheckFilter loginFilter = new LoginCheckFilter();
    FilterRegistration.Dynamic loginFilterConfig = servletContext.addFilter("LoginCheckFilter", loginFilter);
    loginFilterConfig.addMappingForUrlPatterns(
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE),  // EumSet 상수들의 배열 
        false, 
        "/app/*");
  }
}
