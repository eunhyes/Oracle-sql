package dao;

import java.sql.*;
import java.util.*;
import vo.Dept;

public class DeptDAO {

	// q002Case.jsp
	public static ArrayList<HashMap<String, String>> selectJobCaseList() throws Exception {

		/*
		 * 빨 -> 주 -> 노 -> 초 -> 파 출력 
		 * SELECT ename, job, CASE 
		 * WHEN job = 'PRESIDENT' Then '빨강' 
		 * WHEN job = 'MANAGER' THEN '주황'
		 * WHEN job = 'ANALYST' THEN '노랑' 
		 * WHEN job = 'CLERK' THEN '초록' 
		 * ELSE '파랑' END color 
		 * FROM emp 
		 * ORDER BY (CASE 
		 * WHEN color = '빨강' THEN 1
		 * WHEN color = '주황' THEN 2 
		 * WHEN color = '노랑' THEN 3 
		 * WHEN color = '초록' THEN 4
		 * ELSE 5 END) ASC;
		 */
		
		ArrayList<HashMap<String, String>> list = new ArrayList<>();
		
		Connection conn = DBHelper.getConnection();
		
		String sql = "SELECT ename, job, CASE \n"
				+ "WHEN job = 'PRESIDENT' Then '빨강' \n"
				+ "WHEN job = 'MANAGER' THEN '주황' \n"
				+ "WHEN job = 'ANALYST' THEN '노랑' \n"
				+ "WHEN job = 'CLERK' THEN '초록' \n"
				+ "ELSE '파랑' END color \n"
				+ "FROM emp \n"
				+ "ORDER BY (CASE \n"
				+ "WHEN color = '빨강' THEN 1 \n"
				+ "WHEN color = '주황' THEN 2 \n"
				+ "WHEN color = '노랑' THEN 3 \n"
				+ "WHEN color = '초록' THEN 4 \n"
				+ "ELSE 5 END) ASC";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt .executeQuery();
		
		while(rs.next()) {
			HashMap<String, String> m = new HashMap<String, String>();
			
			m.put("ename", rs.getString("ename"));
			m.put("job", rs.getString("job"));
			m.put("color", rs.getString("color"));
			
			list.add(m);
			
		}
		
		conn.close();
		return list;
	}

	// Map 사용
	public static ArrayList<HashMap<String, Object>> selectDeptOnOffList() throws Exception {

		ArrayList<HashMap<String, Object>> list = new ArrayList<>();

		Connection conn = DBHelper.getConnection();
		String sql = "SELECT" 
				+ " deptno deptNo, dname, loc, 'ON' onOff"
				+ " FROM dept";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("deptNo", rs.getInt("deptNo"));
			m.put("dname", rs.getString("dname"));
			m.put("loc", rs.getInt("loc"));
			m.put("onOff", rs.getString("onOff"));

			list.add(m);
		}

		conn.close();
		return list;
	}
	
	
	// DEPTNO 뒤에 인원까지 출력하는 메서드(GROUP BY + COUNT 사용)
		public static ArrayList<HashMap<String, Integer>> selectDeptNoCntList() throws Exception {
		
			ArrayList<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
			
			Connection conn = DBHelper.getConnection();
			
			String sql = "SELECT deptno deptNo, COUNT(*) cnt\n"
					+ "FROM emp\n"
					+ "WHERE deptno IS NOT NULL\n"
					+ "GROUP BY deptno\n"
					+ "ORDER BY deptno ASC";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				HashMap<String, Integer> m = new HashMap<>();
				m.put("cnt", rs.getInt("cnt"));
				m.put("deptNo", rs.getInt("deptNo"));
				list.add(m);
			}
			
			conn.close();
			return list;
		}
		
		
		// 중복을 제외한 DEPTNO 목록을 출력하는 메서드(DISTINCT 사용)
		public static ArrayList<Integer> selectDeptNoList() throws Exception {
		
			ArrayList<Integer> list = new ArrayList<>();
			
			Connection conn = DBHelper.getConnection();
			
			// deptno 이 중복인 것과 null인 것을 제외하고 오름차순 정렬하는 쿼리 
			String sql = "SELECT DISTINCT deptno deptNo"
					+ " FROM emp"
					+ " WHERE deptno IS NOT NULL"
					+ " ORDER BY deptno ASC";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Integer i = rs.getInt("deptNo"); 
				// 랩퍼타입과 기본타입 Auto Boxing
				list.add(i);
				
			}
			
			conn.close();
			
			return list;
		}
		
		
	// VO 사용
	public static ArrayList<Dept> selectDeptList() throws Exception {

		ArrayList<Dept> list = new ArrayList<>();

		Connection conn = DBHelper.getConnection();
		
		String sql = "SELECT"
				+ " deptno deptNO, dname, loc" 
				+ " FROM dept";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Dept d = new Dept();
			d.deptNo = rs.getInt("deptNo");
			d.dname = rs.getString("dname");
			d.loc = rs.getString("rs");
			list.add(d);
		}

		return list;
	}

}
