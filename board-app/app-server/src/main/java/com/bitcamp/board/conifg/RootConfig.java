package com.bitcamp.board.conifg;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

// DAO와 Service만 설정
@ComponentScan(
    value="com.bitcamp.board",
    excludeFilters = @Filter(
        type = FilterType.REGEX,
        pattern = "com.bitcamp.board.controller.*") // controller패키지를 다 제외 시킴
    )
public class RootConfig {

  public RootConfig() {
    System.out.println("RootConfig() 생성자 호출됨!");
  }
}
