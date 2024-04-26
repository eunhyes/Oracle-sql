package dao;

import java.sql.*;
import java.util.*;

import oracle.jdbc.proxy.annotation.Pre;
import vo.Emp;

public class EmpDAO {
	
	//q007SelfJoin.jsp
	public static ArrayList<HashMap<String, Object>> selectEmpSelfJoin() throws Exception	{
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		String sql = "SELECT e1.empno, e1.ename, e1.grade,\n"
				+ " NVL(e2.ename, '관리자 없음') \"mgrName\", NVL(e2.grade, 0) \"mgrGrade\"\n"
				+ " FROM emp e1 LEFT OUTER JOIN emp e2\n"
				+ " ON e1.mgr = e2.empno\n"
				+ " ORDER BY e1.empno ASC";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		System.out.println(stmt);
		
		while(rs.next())	{
			
			HashMap<String, Object> m = new HashMap<>();
			m.put("empNo", rs.getInt("empNo"));
			m.put("ename", rs.getString("ename"));
			m.put("grade", rs.getInt("grade"));
			m.put("mgrName", rs.getString("mgrName"));
			m.put("mgrGrade", rs.getInt("mgrGrade"));
			list.add(m);
			
		}
		
		conn.close();
		return list;
	}
	
	

	// q006GroupBy.jsp
	public static ArrayList<HashMap<String, Integer>> selectEmpSalStats() throws Exception	{
		
		ArrayList<HashMap<String, Integer>> list = new ArrayList<>();
		Connection conn = DBHelper.getConnection();
		
		String sql = "SELECT grade, COUNT(*) count, SUM(sal) sum, AVG(sal) avg, MAX(sal) max, MIN(sal) min"
				+ " FROM GROUP BY grade"
				+ " ORDER BY grade ASC";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		System.out.println(stmt);
		
		while(rs.next())	{
			
			HashMap<String, Integer> m = new HashMap<>();
			m.put("grade", rs.getInt("grade"));
			m.put("count", rs.getInt("count"));
			m.put("sum", rs.getInt("sum"));
			m.put("avg", rs.getInt("avg"));
			m.put("max", rs.getInt("max"));
			m.put("min", rs.getInt("min"));
			
			list.add(m);
			
		}
		
		conn.close();
		return list;
	}
	
	
	
	// q005OrderBy.jsp
	public static ArrayList<Emp> selectEmpListSort(String col, String sort) throws Exception {
	
		// 매개값 디버깅
		System.out.println(col + " ====== EmpDAO.selectEmpListSort col ");
		System.out.println(sort + " ====== EmpDAO.selectEmpListSort sort ");
		
		ArrayList<Emp> list = new ArrayList<>();
		System.out.println(list.size() + " ====== q005 list.size()");
		
		Connection conn = DBHelper.getConnection();
		
		String sql = "SELECT empno, ename FROM emp";
		
		if(col != null && sort != null)	{
			
			sql = sql + " ORDER BY " + col + " " + sort;
			
			
		}
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		System.out.println(stmt);
		
		while(rs.next())	{
			
			Emp e = new Emp();
			e.setEmpNo(rs.getInt("empno"));
			e.setEname(rs.getString("ename"));
			list.add(e);
			
		}
		
		/* 동적쿼리(쿼리 문자열이 매개값에 분기되어 차이가 나는 경우)
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * */
		
		conn.close();
		return list;
		
	}
	
	
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
		public static ArrayList<HashMap<String, Object>> selectEmpAndDeptList() throws Exception {
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
