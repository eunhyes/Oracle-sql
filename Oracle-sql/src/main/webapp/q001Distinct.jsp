<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<!-- Model -->
<%
	ArrayList<Integer> list = EmpDAO.selectDeptNoList();

 	ArrayList<HashMap<String, Integer>> groupByList = EmpDAO.selectDeptNoCntList();
%>

<!-- View -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
</head>
<body>
	<h2>DISTINCT를 사용하는 경우</h2>
	<select name="deptNo">
		<option value="">:::선택:::</option>
		<%
			for(Integer i: list) {
		%>
		
				<option value="<%=i%>"><%=i%></option>
				
		<%	
			}
		%>
	</select>
	
	<h2> DISTINCT 대신 GROUP BY를 사용하는 경우</h2>
	
	<select name="deptNo">
		<option value="">:::선택:::</option>
		<%
			for(HashMap<String, Integer> m :groupByList) {
		%>
		
				<option value="<%=m.get("deptNo") %>">
				
				<%=m.get("deptNo") %>(<%=m.get("cnt") %>명)</option>
		<%	
			}
		%>
	</select>
	
</body>
</html>
