<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>

<!-- Controller -->
<%
	// 어떤 컬럼으로 정렬할지 결정
	String col = request.getParameter("col");
	System.out.println(col + " ======= q005 param col");
	// asc / desc 정렬 결정
	String sort = request.getParameter("sort"); 
	System.out.println(sort + " ======= q005 param sort");
	// DAO 호출해서 모델 반환
	ArrayList<Emp> list = EmpDAO.selectEmpListSort(col, sort);
	System.out.println(list.size() + " ===== q005 list.size()");

%>
<!--  View -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>empno
				<a href="./q005OrderBy.jsp?col=empno&sort=asc">오름</a>
				<a href="./q005OrderBy.jsp?col=empno&sort=desc">내림</a>
			</th>
			<th>ename
				<a href="./q005OrderBy.jsp?col=ename&sort=asc">오름</a>
				<a href="./q005OrderBy.jsp?col=ename&sort=desc">내림</a>
			</th>
		</tr>
	
	<%
		for(Emp e : list)	{
	%>
		<tr>
			<td><%=e.getEmpNo() %></td>
			<td><%=e.getEname() %></td>
		</tr>
	
	<%
		}
	%>
	</table>




</body>
</html>