package com.bitcamp.board.domain;

public class AttachedFile {
  private int no;
  private String filepath;
  private int boardNo;
  //* private Board board; // 쌍방향 참조 ..=> 이렇게 하면 안됩니다.

  public AttachedFile() {} // 기본 생성자

  public AttachedFile(String filepath) {
    this.filepath = filepath; // 파일명을 받아 생성하는 생성자
  }

  @Override
  public String toString() {
    return "AttachedFile [no=" + no + ", filepath=" + filepath + ", boardNo=" + boardNo + "]";
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

  public int getBoardNo() {
    return boardNo;
  }

  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }



}
