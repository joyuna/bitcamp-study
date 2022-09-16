package com.bitcamp.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value=ElementType.TYPE) // 1-1) 애노테이션을 붙일 수 있는 범위를 설정
@Retention(value=RetentionPolicy.RUNTIME) // 1-2) 애노테이션 값을 추출할 때를 지
public @interface WebServlet {
  String value(); // 1-3) 애노테이션의 필수 속성을 설정한다.
}
