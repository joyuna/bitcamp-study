package com.bitcamp.board.conifg;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

// DatabaseConfig와 DataSource만 설정
public class MybatisConfig {

  public MybatisConfig() {
    System.out.println("MybatisConfig() 생성자 호출됨!");
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(
      DataSource ds,
      ApplicationContext iocContainer
      ) throws Exception {

    System.out.println("sqlSessionFactory() 호출됨!");

    // SqlSessionFactory를 만들어 줄 객체를 준비한다.
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

    // Mybatis가 사용할 DB 커넥션풀을 설정한다.
    factoryBean.setDataSource(ds);

    // Mybatis가 실행할 SQL 문이 들어 있는 파일의 위치를 설정한다.
    //  Resource mapperLocation = iocContainer.getResource("com/bitcamp/board/mapper/*Mapper.xml"); => 쓸데없는 객체를 만들지 않기 위해 아래와 같이 줄임.
    //    factoryBean.setMapperLocations(mapperLocation);
    factoryBean.setMapperLocations(
        iocContainer.getResource("classpath:com/bitcamp/board/mapper/*Mapper.xml")); // classpath: 접두어를 붙여주는 경우도 있고, 붙여도 된다.

    return factoryBean.getObject();
  }
}


