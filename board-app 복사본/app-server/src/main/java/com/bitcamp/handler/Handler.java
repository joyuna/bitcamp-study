package com.bitcamp.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;

// 사용자 요청을 다룰 객체의 사용법을 정의한다.
//
public interface Handler {
  // 9-1) 콘솔이 아닌 클라이언트로 출력할 때 사용할 출력 스트림을 파라미터로 받는다. 
  // 9-2) 핸들러가 클라이언트와 통신할수 있도록 입출력 스트림을 파라미터로 전달한다.
  void execute(DataInputStream in, DataOutputStream out) throws Exception;
}
