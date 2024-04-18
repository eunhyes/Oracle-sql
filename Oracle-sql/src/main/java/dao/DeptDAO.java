package dao;

import java.sql.*;
import java.util.*;
import vo.Dept;

public class DeptDAO {
	// Map 사용
	public ArrayList<HashMap<String, Object>> selectDeptOnOffList() throws Exception {
			
			ArrayList<HashMap<String, Object>> list = new ArrayList<>();
			
			Connection conn = DBHelper.getConnection();
			String sql = "SELECT"
					+ " deptno deptNo, dname, loc, 'ON' onOff"
					+ " FROM dept";
			PreparedStatement stmt = conn.prepareStatement("");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				HashMap<String, Object> m = new HashMap<String, Object>();
				m.put("deptNo", rs.getInt("deptNo"));
				m.put("dname", rs.getString("dname"));
				m.put("loc", rs.getInt("loc"));
				m.put("onOff", rs.getString("onOff"));
			
				list.add(m);
			}
			
			return list;
		}
	
	
	// VO 사용
	public ArrayList<Dept> selectDeptList() throws Exception {
		
		ArrayList<Dept> list = new ArrayList<>();
		
		Connection conn = DBHelper.getConnection();
		String sql = "SELECT"
				+ " deptno deptNo, dname, loc"
				+ " FROM dept";
		PreparedStatement stmt = conn.prepareStatement("");
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Dept d = new Dept();
			d.deptNo = rs.getInt("deptNo");
			d.dname	= rs.getString("dname");
			d.loc = rs.getString("rs");
			list.add(d);
		}
		
		return list;
	}

}
