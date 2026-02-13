package com.kh.mvc.common.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* JDBC 과정 중 자주 등장하는 구문들을 각각의 메서드로 정의한 클래스
		1. DB와 접속된 Connection을 생성하여 반환하는 메서드
 		2. close 메서드
 		3. commit / rollback 메서드


*/
public class JDBCTemplate {

	public static Connection getConnection() {

		Connection conn = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return conn;

	}

	// 전달받은 JDBC용 객체를 반납시켜주는 메서드
	public static void close(Connection conn) {

		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void close(Statement conn) {

		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void close(ResultSet conn) {

		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void commit(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
