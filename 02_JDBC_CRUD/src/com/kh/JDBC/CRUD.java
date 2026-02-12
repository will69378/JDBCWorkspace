package com.kh.JDBC;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CRUD {
	public static void main(String[] args) {
		CRUD crud = new CRUD();
		//crud.insert();
		//crud.update();
		//crud.delete("user01");
		//crud.selectOne("user01");
		//crud.selectAll();
		//crud.execPlSql();
		crud.execProcedure();
		
		// ' OR 1=1 --
	}
	
	public void insert() {
		/*
		 	DML의 JDBC 코딩 흐름
		 	
		 		1) Driver 등록
		 		
		 		2) DBMS 연결
		 		
		 		3) autoCommit 설정
		 		
		 		4) Statement 객체 생성
		 		
		 		5) SQL문 실행
		 		
		 		6) 트랜잭션 처리
		 		
		 		7) 자원반납
		 		
		*/
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection conn = 
			DriverManager.
			getConnection("jdbc:oracle:thin:@localhost:1521:xe",
					"JDBC", "JDBC");
			//autoCommit 설정
			// - 기본값 : true
			conn.setAutoCommit(false);
			
			// 4) Statement 생성
			Statement stmt = conn.createStatement();

			// DB에 완성된 형태의 SQL문을 전달하여 실행 후 결과 받기
			// Statement ? 실행할 SQL문을 "완전한 문장" 형태로 만들어서 실행해야하는 클래스
			int result = stmt.executeUpdate("INSERT INTO MEMBER VALUES('user07','pass02','홍길동','M','user01@naver.com','010-4121-3393','서울시 목동 ','20', SYSDATE)");
			
			//트랜잭션 처리
			if(result > 0) { // 1개 이상의 행이 insert 되었다면 성공
				conn.commit();
			}else {
				conn.rollback();
			}
			
			// 자원반납
			// 사용한 자원의 역순으로 반납
			conn.close();
			stmt.close();
			
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}
	
	}
	
	public void update() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection conn = 
					DriverManager.
					getConnection("jdbc:oracle:thin:@localhost:1521:xe",
							"JDBC", "JDBC");
					//autoCommit 설정
					// - 기본값 : true
					conn.setAutoCommit(false);
					
			/* 
			 4) PreparedStatement 생성
			 	- 쿼리문을 미리 준비해둬야하는 객체
			 	- Statement 인터페이스를 상속받은 인터페이스로, Statement의 단점을 개선한 클래스
			 	
			 	- Statement의 단점
			 		1) 하드코딩한 데이터가 그대로 들어가 있어서 가독성이 좋지 못함.
			 		2) 재사용성이 나쁘다. 동일한 SQL문을 수행하더라도, 매번 SQL문을 다시
			 		검사하고, 실행계획을 새로 짜게 된다.
			 		3) 스스로 SQL 인젝션을 방어하지 못한다.
			 	
			 	- PreparedStatement 객체 생성시 "미완성된" 상태의 SQL문을 미리
			 	전달하여 실행계획을 준비시키고, 실행하기 전에 완성된 형태로 만들어야 한다.
			
				- 미완성된 형태의 SQL문
				- 쿼리문에 실제로 대입되어야 할 값들은 ?(위치홀더)로 표시만 해둔다.
			*/	
				String sql = "UPDATE MEMBER SET EMAIL = ?, PHONE = ?, ADDRESS = ?"
						+ "WHERE MEMBER_ID = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				//미완성된 쿼리문을 실행가능한 상태로 완성시키기
				// pstmt.set~~~(?의 위치, 값)
				// ~~~는 추가할 값의 자료형
				pstmt.setString(1, "rudals@naver.com");
				pstmt.setString(2, "111-4111-1111");
				pstmt.setString(3, "양천구 목동");
				pstmt.setString(4, "user07");
				
				int updateCount = pstmt.executeUpdate();
				
				if(updateCount > 0) {
					conn.commit();
				}
				else {
					conn.rollback();
				}
				
				//사용한 자원의 반납(만든 순서의 역순으로 반납할 것)
				pstmt.close();
				conn.close();
					
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	}
	
	public void delete(String userId) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = 
					DriverManager.
					getConnection("jdbc:oracle:thin:@localhost:1521:xe",
							"JDBC", "JDBC");
					//autoCommit 설정
					// - 기본값 : true
					conn.setAutoCommit(false);
					
					
					//Statement 객체 생성
					Statement stmt = conn.createStatement();
					
					//int result = stmt.executeUpdate
					//		("DELETE FROM MEMBER WHERE MEMBER_ID = '"+userId+"'");
					//System.out.println(result);
					
					
					//PreparedStatement
					String sql = "DELETE FROM MEMBER WHERE MEMBER_ID = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					
					
					//Statement는 그대로 사용 : ' OR 1=1 --
					//PrepareStatement는 문자열로 사용 : "' OR 1=1 --"
					//바인딩 과정에서 sql 인젝션과 관련된 키워드는 안전한 코드로 변환
					pstmt.setString(1, userId); 
					
					int result = pstmt.executeUpdate();
					System.out.println(result);
					
					
					if(result > 0) {
						conn.commit();
					}
					else {
						conn.rollback();
					}
					
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	
	/*
		DQL JDBC 실행 흐름
			1) Driver 등록
			
			2) DBMS 연결
			
			3) Statement 객체 생성
			
			4) SQL 실행
			
			5) SQL 실행 결과값 반환
			
			6) ResultSet을 vo 객체로 변환
			
			7) 자원반납
	 */
	
	
	public void selectOne(String userId) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection conn = 
					DriverManager.
					getConnection("jdbc:oracle:thin:@localhost:1521:xe",
							"JDBC", "JDBC");
			
			String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			ResultSet rset = pstmt.executeQuery();
			
			/*
				ResultSet
					- Select를 호출한 결과값이 담긴 객체
					- 커서(Cursor)를 사용하여 ResultSet의 각 행에 접근하여 데이터를
					읽어올 수 있다.
					- 커서의 초기 위치는 0번행으로, 위치를 변경하는 메서드를 통해 조작가능
					
				커서의 위치를 변경하는 메서드들
					1) next() : boolean
						- 커서를 다음행으로 이동시키고, 행이 있다면 true 없다면 false를 반환
						
					2) previous() : boolean
						- 커서를 이전행으로 이동. 있다면 true 없다면 false;
						
					3) first() : boolean
						- 커서를 첫번째 행으로 이동 ""
					
					4) last() : boolean
						- 커서를 마지막 행으로 이동 ""
					
					5) absolute(int row) : boolean
						- 커서를 지정된 row 위치로 이동. ""
					
					6) relative(int row) : boolean
						- 커서를 현재위치에서 지정된 행의 수 만큼 이동. ""		
			 */
			
			if(rset.next()) {
				//현재 커서가 가리키고 있는 행의 데이터를 추출하여 vo 객체로 변환
				//rset으로 어떤 칼럼, 어떤 순번에 위치하는 칼럼값을 뽑을지 결정
				Member m = new Member();
				m.setMemberId(rset.getString("MEMBER_ID"));
				m.setMemberPwd(rset.getString("MEMBER_PWD"));
				m.setMemberName(rset.getString("MEMBER_NAME"));
				m.setEmail(rset.getString("EMAIL"));
				m.setAge(rset.getInt("AGE"));
				m.setEnrollDate(rset.getDate("ENROLL_DATE"));
				
				System.out.println(m);
				
			}
		
			rset.close();
			pstmt.close();
			conn.close();
			
		
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}

	}
	
	public void selectAll() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			String sql = "SELECT * FROM member";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
			
			ResultSet rset = pstmt.executeQuery();
			
			List<Member> list = new ArrayList<>();
			while(rset.next()) {
				Member m = new Member();
				m.setMemberId(rset.getString("MEMBER_ID"));
				m.setMemberPwd(rset.getString("MEMBER_PWD"));
				m.setMemberName(rset.getString("MEMBER_NAME"));
				m.setEmail(rset.getString("EMAIL"));
				m.setAge(rset.getInt("AGE"));
				m.setEnrollDate(rset.getDate("ENROLL_DATE"));
				
				list.add(m);
			}
			
			System.out.println(list);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void execPlSql() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection conn = 
					DriverManager.getConnection
					("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// PreparedStatement로 pl / sql문 실행
			String sql = "BEGIN UPDATE MEMBER SET ENROLL_DATE = ? WHERE MEMBER_ID = ?; END;";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "2026/02/11");
			pstmt.setString(2, "user01");
			
			pstmt.execute();
			
			pstmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	
	
	//JDBC로 프로시져 실행
	public void execProcedure() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection conn = 
					DriverManager.getConnection
					("jdbc:oracle:thin:@localhost:1521:xe", "KH", "KH");
			
			// Callablestatement 생성
			//		- 저장된 프로시져를 호출할 때 사용
			//		- ? 홀더를 사용할 수 있다.
			String sql =  "{call PRO_EMP(?,?,?,?)}";
			
			CallableStatement cstmt = conn.prepareCall(sql);
			
			cstmt.setInt(1, 200);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.registerOutParameter(4, Types.DOUBLE);
			
			cstmt.execute();
			
			String name = cstmt.getString(2);
			int salary = cstmt.getInt(3);
			double bonus = cstmt.getDouble(4);
			
			System.out.println(name + " " + salary + " "+ bonus);
			
			cstmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		

	}
	
	
	
	
	
	
	
	
}
