<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<!-- Controller -->
<%

	ArrayList<HashMap<String, Object>> list = EmpDAO.selectEmpSelfJoin();

%>

<!-- View -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>직원별 담당 매니저</h1>
	<table>
		<tr>
			<th>empNo</th>
			<th>ename</th>
			<th>grade</th>
			<th>mgrName</th>
			<th>mgrGrade</th>
		</tr>
		<%
			for(HashMap<String, Object> m : list)	{
		%>
			<tr>
				<td><%=(Integer)m.get("empNo") %></td>
				<td><%=(String)m.get("ename") %></td>
				<td>
					<%
						for(int i = 0; i < (Integer)m.get("grade"); i=i+1)	{
					%>
						&#11088;
					<%
						}
					%>
					
				</td>
				<td><%=(String)m.get("mgrName") %></td>
				<td>
					<%
						for(int i = 0; i < (Integer)m.get("marGrade"); i=i+1)	{
					%>
						&#11088;
					<%
						}
					%>
					
				</td>
			
			
			</tr>
		<%
			}
		%>

	</table>
</body>
</html>