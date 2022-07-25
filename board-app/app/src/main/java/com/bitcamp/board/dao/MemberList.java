package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Member;

// 회원 목록을 관리하는 역할
//
public class MemberList extends ObjectList{

  private int no = 0;

  private int nextNo() {
    return ++no;
  }

  @Override
  public void add(Object obj) {
    Member member = (Member) obj;
    member.no = nextNo();
    super.add(obj);

  }
}














