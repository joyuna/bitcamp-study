package com.bitcamp.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bitcamp.board.domain.Member;

@Repository // DAO 역할을 수행하는 객체에 붙이는 애노테이션
public class MybatisMemberDao implements MemberDao {

  @Autowired DataSource ds;
  @Autowired SqlSessionFactory sqlSessionFactory; // setter가 안붙어도 Autowired 애노테이션이 붙으면 알아서 주입해준다.

  // 실무에 가면 생성자를 못볼것이다. 대신 Autowired 애노테이션을 보게 될 것이다. 하지만 생성자 통해 엄격하게 주입하는걸 권장하긴 한다.
  //  public MybatisMemberDao(DataSource ds, SqlSessionFactory sqlSessionFactory) {
  //    System.out.println("MybatisMemberdDao() 호출됨!");
  //    this.ds = ds;
  //    this.sqlSessionFactory = sqlSessionFactory;
  //  }

  @Override
  public int insert(Member member) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.insert("MemberDao.insert", member); //네임스페이스.sqlID => 인터페이스 이름.메서드 이름이 아니라 일관성을 주려고 이름을 같게 한거다.
    }
  }

  @Override
  public Member findByNo(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("MemberDao.findByNo", no);
    }
  }

  @Override
  public int update(Member member) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.update("MemberDao.update", member);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("MemberDao.delete", no);
    }
  }

  @Override
  public List<Member> findAll() throws Exception {
    try (SqlSession sqlSession =sqlSessionFactory.openSession()) {
      return sqlSession.selectList("MemberDao.findAll");
    }
  }

  @Override
  public Member findByEmailPassword(String email, String password) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
      Map<String,Object> paramMap = new HashMap<>();
      paramMap.put("email", email);
      paramMap.put("password", password);

      //      Member member = sqlSession.selectOne(
      return sqlSession.selectOne(
          "MemberDao.findByEmailPassword", // SQL 문의 ID
          paramMap // SQL 문의 in-parameter(#{})에 들어 갈 값을 담고 있는 객체
          );

      //      pstmt.setString(1, email);
      //      pstmt.setString(2, password);
      //
      //      try (ResultSet rs = pstmt.executeQuery()) {
      //        if (!rs.next()) {
      //          return null;
      //        }
      //
      //        Member member = new Member();
      //        member.setNo(rs.getInt("mno"));
      //        member.setName(rs.getString("name"));
      //        member.setEmail(rs.getString("email"));
      //        member.setCreatedDate(rs.getDate("cdt"));
      //        return member;
      //    }
    }
  }
}














