package dao;

import java.sql.*;
import java.util.*;

import vo.Emp;

public class EmpDAO {

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
		public ArrayList<Emp> selectDeptList() throws Exception {
			
			ArrayList<Emp> list = new ArrayList<>();
			
			Connection conn = DBHelper.getConnection();
			String sql = "SELECT"
					+ " empno empNo, ename, sal"
					+ " FROM mp";
			
			PreparedStatement stmt = conn.prepareStatement("");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Emp e = new Emp();
				e.empNo = rs.getInt("empNo");
				e.ename	= rs.getString("ename");
				e.sal = rs.getInt("sal");
				list.add(e);
			}
			
			conn.close();
			return list;
		}
		
}
