package dao;

import java.sql.*;
import java.util.*;

import vo.Emp;

public class EmpDAO {

	// q004WhereIn.jsp
	public static ArrayList<Emp> selectEmpListByGrade(ArrayList<Integer> ckList, PreparedStatement stmt) throws Exception	{
		
		ArrayList<Emp> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		
		String sql = "SELECT ename, grade FROM emp"
				+ " WHERE grade IN ";
		
		if(ckList.size() == 1)	{
			
			sql = sql + "(?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
			
		} else if (ckList.size() == 2) {
			
			sql = sql + "(?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
			stmt.setInt(2, ckList.get(1));
			
		} else if (ckList.size() == 3)	{
			
			sql = sql + "(?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
			stmt.setInt(2, ckList.get(1));
			stmt.setInt(3, ckList.get(2));
			
		} else if (ckList.size() == 4)	{
			
			sql = sql + "(?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
			stmt.setInt(2, ckList.get(1));
			stmt.setInt(3, ckList.get(2));
			stmt.setInt(4, ckList.get(3));
			
		} else if (ckList.size() == 5)	{
			
			sql = sql + "(?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ckList.get(0));
			stmt.setInt(2, ckList.get(1));
			stmt.setInt(3, ckList.get(2));
			stmt.setInt(4, ckList.get(3));
			stmt.setInt(5, ckList.get(4));
		}
		
		ResultSet rs = stmt.executeQuery();
		while(rs.next())	{
			
			Emp e = new Emp();
			e.setEname(rs.getString("ename"));
			e.setGrade(rs.getInt("grade"));
			list.add(e);
			
		}
		
		conn.close();
		return list;
	}
	
	
	
	// 조인으로 Map을 사용하는 겨우
		public static ArrayList<HashMap<String, Object>> selectEmpAndDeptList()
														throws Exception {
			ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		
			Connection conn = DBHelper.getConnection();
			String sql = "SELECT"
					+ " emp.empno empNo, emp.ename ename, emp.deptno deptNo,"
					+ " dept.dname dname"
					+ " FROM emp INNER JOIN dept"
					+ " ON emp.deptno = dept.deptno";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				HashMap<String, Object> m = new HashMap<>();
				m.put("empNo", rs.getInt("empNo"));
				m.put("ename", rs.getString("ename"));
				m.put("deptNo", rs.getInt("deptNo"));
				m.put("dname", rs.getString("dname"));
				list.add(m);
			}
			return list;
		}
		
		// VO 사용
		public static ArrayList<Emp> selectEmpList() throws Exception {
			ArrayList<Emp> list = new ArrayList<>();
			
			Connection conn = DBHelper.getConnection();
			String sql = "SELECT"
					+ " empno empNo, ename, sal"
					+ " FROM emp";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Emp e = new Emp();
				e.empNo = rs.getInt("empNo");
				e.ename = rs.getString("ename");
				e.sal = rs.getDouble("sal");
				list.add(e);
			}
			
			return list;
		}
		
		
		
}
