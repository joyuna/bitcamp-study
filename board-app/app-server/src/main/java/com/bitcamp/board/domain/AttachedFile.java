package com.bitcamp.board.domain;

public class AttachedFile {
  private int no;
  private String filepath;

  public AttachedFile() {} // 기본 생성자

  public AttachedFile(String filepath) {
    this.filepath = filepath; // 파일명을 받아 생성하는 생성자
  }

  @Override
  public String toString() {
    return "AttachedFile [no=" + no + ", filepath=" + filepath + "]";
  }
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getFilepath() {
    return filepath;
  }
  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }



}
