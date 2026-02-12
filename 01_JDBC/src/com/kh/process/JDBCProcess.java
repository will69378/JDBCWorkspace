package com.kh.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
	JDBC(Java DataBase Connectivity)
		- 자바 어플리케이션과 데이터베이스 서버간의 연동시 필요한 연결방법 및
			SQL문을 전달하고 결과값을 돌려받는 방법들을 정의한 자바 기본 API
		
		- JDBC는 연결하고자 하는 데이터베이스가 무엇이든간에 일관된 방법을 통신이
		가능하도록 표준화하였다.
		
	
	JDBC를 알아야 하는 이유
		- Spring, MyBaits, JPA등 JDBC를 내부적으로 활용하여 소스코드를
		자동화 시켰기 때문에 그 동작방식의 이해를 위해서 알아야 한다.

	
	JDBC의 주요 객체들
		- DBMS와 "연결"하기 위한 객체, 실행할 SQL문을 DBMS에 "전달"하는
		 객체, 결과값을 "반환"받는 객체로 나뉘어져 있다.
		 
		 1) ~~~Driver : DB와 연결을 담당하는 클래스. 각 회사에서 Driver 
		 인터페이스를 구현한 클래스. (기본 자바 api에 없기때문에 별도의 다운로드 필요)
		 
		 2) DriverManger : Driver들을 관리하는 클래스. 여러개의 Driver들 중
		  어떤 Driver를 통해 DB와 연결할지 선택할 수 있다.
		  
		 3) Connection : DB와 연결된 상태임을 나타내는 객체. 연결 설정 및 해제와
		 관련된 메서드, State 생성 및 트랜잭션 관리가 가능하다.
		 
		 4) (Prepared)Statement : 연결된 DB에 SQL문을 전달하고 실행한 후
		 	결과를 받아내는 객체.
		 
		 5) ResultSet : 실행한 SQL문이 DQL문일 경우 반환되는 결과를 저장하는 객체
 */

public class JDBCProcess {

	public static void main(String[] args) {
		/*
			JDBC API 코딩 프로세스
			
				1) Driver 등록 -> 연결하고자 하는 DBMS사의 Driver를 등록
				2) DBMS 연결 -> 접속하고자 하는 DB정보를 입력하여 DB에 접속
				3) Statement 생성 -> Connecttion 객체를 통해서 SQL문을
				저장 및 관리하는 Statement 생성
				
				4) SQL 실행
				5) 결과값 반환
				6) [트랜잭션처리 / VO 객체로 변환]
				
		 */
		
		// 1) 오라클 드라이버 등록
		//		- 현재 어플리케이션에 오라클 드라이버가 추가되어 있어야 한다.
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			/*
			 	오라클 드라이버 등록방법
			 		1. 프로젝트 우클릭 -> Properties -> Build Path로 이동
			 		2. Libraries 이동 -> Module Path 선택
			 		3. ADD External Jars 선택후 ojdbc 파일 등록
			 	
			 	OracleDriver와 같은 class 파일의 소스를 확인하고자할때
			 	사용하는 방법.
			 		- 디컴파일러 설치
			 		1. help => 이클립스 마켓플레이스 선택 -> decompiler 검색
			 		2. window -> preference -> general -> editors
			 		-> file Association으로 이동
			 */
			
			// 2) 드라이버 등록 확인
				DriverManager
				.drivers()
				.forEach(System.out::println);
				
			// 3) Connection 객체 생성하기
			// - getConnection(url, 접속계정, 비밀번호);
				Connection conn = 
						DriverManager.getConnection(
						"jdbc:oracle:thin:@localhost:1521:xe",
						"JDBC", "JDBC"
					);
			
			// 4) Statement 객체 생성
			Statement stmt = conn.createStatement();
				
			
			// 5) DB에 SQL문을 전달함과 동시에 SQL문 실행
			boolean result = 
			stmt.execute("SELECT 'HELLO JDBC' AS TEST FROM DUAL");
			
			
			// 6) 결과값 받기
			// - DQL문을 실행한 경우 getResultSet()
			// - DML문을 실행한 경우 getUpdateCount()
			ResultSet rset = stmt.getResultSet();
			
			if(rset.next()) {
				System.out.println(rset.getString("TEST"));
			}
			
			
			// 5번 6번 함께 쓰기) execute + getResultset => executeQuery;
			// execute + getUpdateCount => executeUpdate;
			ResultSet rset2 = 
			stmt.executeQuery("SELECT 'HELLO JDBC' AS TEST2 FROM DUAL");
			
			if(rset2.next()) {
				System.out.println(rset2.getString(1));
			}
			
			
			// 7) 사용한 자원 반납
			conn.close();
			
			
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}	
	}
	
}
