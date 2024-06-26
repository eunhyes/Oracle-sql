package dao;

import java.sql.*;
import java.util.*;

public class CustomerDAO {

		// 비밀번호 수정
		// 호출 : editPwAction.jsp
		// param : String(mail), String(수정전 pw = oldPw), String(수정할 pw = newPw)
		// return : int(1 = 수정성공, 0 = 수정실패)
		public static int updatePw(String mail, String oldPw, String newPw) throws Exception {
			int row = 0;
			
			Connection conn = DBHelper.getConnection();
			String sql = "UPDATE customer"
					+ " SET pw = ?"
					+ " WHERE mail = ? AND pw = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, newPw);
			stmt.setString(2, mail);
			stmt.setString(3, oldPw);
			row = stmt.executeUpdate();
			
			return row;
		}
		
		
		// 회원탈퇴
		// dropCustomerAction.jsp
		// param : String(세션안의 mail), String(pw)
		// return : int(1 = 탈퇴성공, 0 = 탈퇴실패)
		public static int deleteCustomer(String mail, String pw) throws Exception {
			int row = 0;
			
			Connection conn = DBHelper.getConnection();
			String sql = "DELETE FROM customer"
					+ " WHERE mail = ? AND pw = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, mail);
			stmt.setString(2, pw);
			row = stmt.executeUpdate();
			
			return row;
		}
		
		
		// 로그인 메서드
		// loginAction.jsp
		// param : String(mail), String(pw)
		// return : HashMap(메일, 이름)
		public static HashMap<String, String> login(String mail, String pw) throws Exception {
			HashMap<String, String> map = null;
			
			Connection conn = DBHelper.getConnection();
			String sql = "SELECT mail, name"
					+ " FROM customer"
					+ " WHERE mail = ? AND pw = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, mail);
			stmt.setString(2, pw);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				map = new HashMap<String, String>();
				map.put("mail", rs.getString("mail"));
				map.put("name", rs.getString("name"));
			}
			
			conn.close();
			
			return map;
		}
		
		
		
		// 회원가입 액션
		// addCustomerAction.jsp
		// param : customer(mail, pw, name, birth, gender)
		// return : int(1 = 가입 성공, 0 = 가입 실패)
		public static int insertCustomer(String mail, String pw, String name, String birth, String gender) throws Exception {
			int row = 0;
			
			Connection conn = DBHelper.getConnection();
			String sql = "INSERT INTO customer("
					+ "mail, pw, name, birth, gender,"
					+ " update_date, create_date) VALUES ("
					+ "?,?,?,?,?, sysdate, sysdate)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, mail);
			stmt.setString(2, pw);
			stmt.setString(3, name);
			stmt.setString(4, birth);
			stmt.setString(5, gender);
			row = stmt.executeUpdate();
			
			conn.close();
			
			return row;
		}
		
		// 회원가입시 mail 중복확인
		// checkMailAction.jsp
		// param : String(mail)
		// return : boolean(사용가능 = true, 불가능 = false)
		public static boolean checkMail(String mail) throws Exception {
			boolean result = false;
			
			Connection conn = DBHelper.getConnection();
			String sql = "SELECT mail"
					+ " FROM customer"
					+ " WHERE mail = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, mail);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) { // 사용불가
				result = true;
			}		
			conn.close();
			
			return result;
		}
		
		// 관리자 페이지 전체 회원정보 리스트(pw제외)
		// /emp/customerList.jsp
		// param : void
		// return : Customer배열(리스트) -> ArrayList<HashMap<String, Object>>
		public static ArrayList<HashMap<String, Object>> selectCustomerListByPage(
				int startRow, int rowPerPage) throws Exception {
			// currentPage + rowPerPage -> startRow를 구하는 알고리즘 코드구현 액션에서...
			// startRow를 구하는 알고리즘 코드구현을 DAO에 하지 않도록...
			
			ArrayList<HashMap<String, Object>> list =
					new ArrayList<HashMap<String, Object>>();
			
			Connection conn = DBHelper.getConnection();
			String sql = "SELECT "
					+ "mail, name, birth, gender, update_date updateDate, create_date createDate "
					+ " FROM customer"
					+ " ORDER BY mail"
					+ " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, startRow);
			stmt.setInt(2, rowPerPage);
			ResultSet rs = stmt.executeQuery();
			
			// JDBC Resulst(자바에서 일반적이지 않은 자료구조) 
			//  -> (자바에서 평이한 자료구조) Collections API 타입 -> List, Set, Map 
			while(rs.next()) {
				
				HashMap<String, Object> m = new HashMap<String, Object>();
				
				m.put("mail", rs.getString("mail"));
				m.put("name", rs.getString("name"));
				list.add(m);
			}
			
			conn.close();
			return list;
		}
		
		
		// 디버깅용 메인 메서드
		public static void main(String[] args) throws Exception {
			// 메일 중복 확인 메서드 디버깅
			//System.out.println(CustomerDAO.checkMail("a@goodee.com")); // false
			 
			// 회원가입 메서드 디버깅
			//System.out.println(CustomerDAO.insertCustomer("z@goodee.com","1234","zzz","1999/09/09","여")); // 1
			
			// 로그인 메서드 디버깅
			// System.out.println(CustomerDAO.login("a@goodee.com", "1234")); // 성공
			
			// 회원탈퇴 메서드 디버깅
			//System.out.println(CustomerDAO.deleteCustomer("a@goodee.com", "1234")); 
			
			// 회원 리스트 메서드 디버깅 
			System.out.println(CustomerDAO.selectCustomerListByPage(10, 10));	
		}
				
			
}
