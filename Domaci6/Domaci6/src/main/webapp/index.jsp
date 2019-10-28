<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.Asistent" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Domaci6</title>
	</head>
	<body>
		
		<form action="Servlet" method="POST">
			Ime: <input type="text" name="ime" required><br><br>
			Ocena: <input type="number" name="ocena" required><br><br>
			<input type="submit" value="Submit"><br>
		</form>
		
		<c:choose>
		<c:when test="${asistenti.size() > 0 }">
		
		<table>
			<tr>
				<th>Ime</th>
				<th>Ocena</th>
			</tr>
			
			<c:forEach var="asistent" items="${asistenti }">
				<tr>
					<td>
						${asistent.name}
					</td>
					<td>
						<fmt:formatNumber pattern="0.00" value="${asistent.average}" />
					</td>
				</tr>
			</c:forEach>
			
		</table>
		</c:when>
		<c:when test="${asistenti.size() == 0}">
			<p>Nijedan uneti asistent.</p>
		</c:when>
		</c:choose>
		
		<br><br>
		
		<fmt:formatDate value="${currentDate }" type="date" dateStyle="full"/>
		
	</body>
</html>
