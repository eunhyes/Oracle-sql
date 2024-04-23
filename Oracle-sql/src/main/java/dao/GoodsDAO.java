package dao;

import java.sql.*;
import java.util.*;

public class GoodsDAO {
	
	// 상품 주문 or 주문 취소시 상품 개수 수정 
	// /customer/addOrdersAction.jsp OR /customer/dropOrdersAction.jsp
	// param : int(상품번호), int(변경할 수량 + or - )
	public static int updateGoodsAmount(int goodsNo, int amount) throws Exception {
		int row = 0;
		
		String sql = "UPDATE goods"
				+ " SET goods_amount = ?, update_date = sysdate"
				+ " WHERE goods_no = ?";
		
		return row;
		
	}
	
	// 상품 상세보기
	// /customer/goodsOne.jsp
	// param : int(goods_no)
	// return : Goods -> HashMap
	public static HashMap<String, Object> selectGoodsOne(int goodsNo) throws Exception {
		HashMap<String, Object> map = null;
		
		String sql = "SELECT *"
				+ " FROM goods"
				+ " WHERE gooods_no = ?";
		
		return map;
	}
	
	
	// 고객 로그인 후 상품목록 페이지
	// /customer/goodsList.jsp
	// param : void
	// return : Goods(일부 속성)의 배열 -> ArrayList<HashMap<String, Object>>
	public static ArrayList<HashMap<String, Object>> selectGoodsList(String category, int startRow, int rowPerPage) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		// DB 연결
		Connection conn = DBHelper.getConnection();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		if(category != null || category.equals("")) {
			sql = "SELECT goods_no goodsNo, category, goods_title goodsTitle, goods_price goodsPrice"
					+ " FROM goods"
					+ " WHERE category = ?"
					+ " ORDER BY goods_no desc"
					+ " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, category);
			stmt.setInt(2, startRow);
			stmt.setInt(3, rowPerPage);
			
		} else {
			sql = "SELECT goods_no goodsNo, category, goods_title goodsTitle, goods_price goodsPrice"
					+ " FROM goods"
					+ " ORDER BY goods_no DESC"
					+ " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, startRow);
			stmt.setInt(2, rowPerPage);
		}
		
		rs = stmt.executeQuery();
		
		conn.close();
		return list;
	}

}






