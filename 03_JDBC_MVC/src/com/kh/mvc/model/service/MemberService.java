package com.kh.mvc.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.kh.mvc.model.dao.MemberDao;
import com.kh.mvc.model.vo.Member;

/*
	Service
		- 컨트롤러에 의해 호출되는 최초의 메서드
		- 여러 dao에 존재하는 메서드들을 호출하여 논리적으로 연관이 있는 비지니스 로직을 만든다.
		(dao에 있는 기능들을 효율적으로 처리)
		- 처리결과값을 컨트롤러에게 반환해주는 역할을 한다.
		
*/

public class MemberService {
	
	private MemberDao mDao = new MemberDao();
	

	public int insertMember(Member m) {
		
		int result = 0;
		//Connection 객체 생성
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = 
					DriverManager
					.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			conn.setAutoCommit(false);

			result = mDao.insertMember(conn, m);
			
			if(result > 0) {
				conn.commit();
			}
			else {
				conn.rollback();
			}
			
			conn.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
