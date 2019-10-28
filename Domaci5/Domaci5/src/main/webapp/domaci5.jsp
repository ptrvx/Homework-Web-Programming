<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map" %>
<%@page import="beans.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="scopeR" class="beans.Data" scope="request"/>
<jsp:useBean id="scopeS" class="beans.Data" scope="session"/>
<jsp:useBean id="scopeA" class="beans.Data" scope="application"/>

<% 
	if (request.getMethod().equals("POST") && request.getParameter("scope") != null) {
		String scope = request.getParameter("scope");
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		if (key != null && value != null) {
			if (scope.equals("request")) {
				scopeR.setter(key, value);
			} else if (scope.equals("session")) {
				scopeS.setter(key, value);
			} else if (scope.equals("application")) {
				scopeA.setter(key, value);
			}
		}
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Domaci5</title>

</head>

<body>

<div>
	<h2>Unos</h2>
	<form action="<%=request.getRequestURI() %>" method='POST'>
		<select name="scope">
			<option value="request">REQUEST</option>
			<option value="session">SESSION</option>
			<option value="application">APPLICATION</option>
		</select>
		<br><br>
		Key: <input type="text" name="key">
		<br><br>
		Value: <input type="text" name="value">
		<br><br>
		<input type="submit" value="Submit">
	</form>
	
</div>

<div>
	<h2>Prikaz</h2>
	
	<% 
		HashMap <String,String> mapR = scopeR.getter();
		HashMap <String,String> mapS = scopeS.getter();
		HashMap <String,String> mapA = scopeA.getter();
		
		Cookie[] cookies = request.getCookies();
		Cookie jsid = null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("JSESSIONID")) {
				jsid = cookies[i];
				break;
			}
		}
	%>
	
	<% if (mapR.size() > 0) { %>
		<h4>Request: </h4>
			<% for (Map.Entry<String, String> entry : mapR.entrySet()) { %>
				<%= entry.getKey() %> : <%= entry.getValue() %> <br>
			<% } %>
	<% } %>
	<% if (mapS.size() > 0) { %>
		<h4>Session: </h4>
			<% for (Map.Entry<String, String> entry : mapS.entrySet()) { %>
				<%= entry.getKey() %> : <%= entry.getValue() %> <br>
			<% } %>
			<p><%= jsid.getName() + " : " + jsid.getValue() %></p>
	<% } %>
	<% if (mapA.size() > 0) { %>
		<h4>Application: </h4>
			<% for (Map.Entry<String, String> entry : mapA.entrySet()) { %>
				<%= entry.getKey() %> : <%= entry.getValue() %> <br>
			<% } %>
	<% } %>
	
</div>

</body>
</html>