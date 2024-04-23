package dao;

import java.sql.*;
import java.util.*;

public class OrdersDAO {
	
	// 고객 - 자신의 주문을 확인(페이징)
	public static ArrayList<HashMap<String, Object>> selectOrdersListByCustomer(String mail, int startRow, int rowPerPage) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		String sql = "SELECT o.orders_no ordersNo, o.goods_no goodsNo, g.goods_title goodsTitle"
				+ " FROM orders o INNER JOIN goods g"
				+ " ON o.goods_no = g.goods_no"
				+ " WHERE o.mail = ?"
				+ " ORDER BY o.orders_no desc"
				+ " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		
		return list;
	}
	
	// 관리자 - 전체주문 확인(페이징)
	public static ArrayList<HashMap<String, Object>> selectOrdersListAll(
			int startRow, int rowPerPage) throws Exception {
		ArrayList<HashMap<String, Object>> list 
				= new ArrayList<HashMap<String, Object>>();
		String sql = "SELECT o.orders_no ordersNo, o.goods_no goodsNo, g.goods_title goodsTitle"
				+ " FROM orders o INNER JOIN goods g"
				+ " ON o.goods_no = g.goods_no"
				+ " ORDER BY o.orders_no desc"
				+ " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		
		return list;
	}

}
