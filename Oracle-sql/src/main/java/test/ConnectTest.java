package test;

import java.sql.*;

public class ConnectTest {
	
	public static void main(String[] args) {
		
		String driver = "oracle.jdbc.dirver.OracleDriver";
		
		try {
			
			Class.forName(driver);
			System.out.println("성공");
			
		} catch	(Exception e) {
			
			System.out.println("실패");
			e.printStackTrace();
			
		}
	}
	
}
