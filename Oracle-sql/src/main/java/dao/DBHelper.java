package dao;

import java.io.*;
import java.sql.*;

public class DBHelper {
	
	public static Connection getConnection() throws Exception {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		// Oracle Cloud
		// jdbc:oracle:thin:@DB이름_속성?TNS_ADMIN=전자지갑경로
		String dbUrl = "jdbc:oracle:thin:@eunhyesss_high?TNS_ADMIN=/Users/eunhye/data/oracle-wallet/Wallet_eunhyesss";
		String dbUser = "admin";
		String dbPw = "Eunhye123Eunhye";
		
		Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
		System.out.println(conn);

		return conn;
		
	}
	
}
