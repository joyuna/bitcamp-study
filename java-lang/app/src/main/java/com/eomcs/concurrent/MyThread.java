package com.eomcs.concurrent;

public class MyThread extends Thread{


  int count;// 1. 카운트값을 받기 위해 인스턴스 필드 생성

  public MyThread(int count) {
    this.count = count; // 2. 생성자에 카운트값 담음
  }

  @Override
  public void run() {
    for (int i = 0; i < 1000; i++) {
      System.out.println("==> " + i);
    }
  }
}
