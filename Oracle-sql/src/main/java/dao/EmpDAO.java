package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Emp;

public class EmpDAO {

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
			
			return list;
		}
		
}
