<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet" %>
    <jsp:include page="index.html"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Library Management System</title>
</head>
<body>
<form action="SearchServlet" style="width:1159px;" method=post>
<table align="center"> 
<tr>
<th>Enter Book Id : </th>
<th style="color:black" ><input type="text" id="bookid" name="bookid"/></th>
</tr>
<tr>
<th>Enter Book Title : </th>
<th style="color:black"><input type="text" id="booktitle" name="booktitle"/></th>
</tr>
<tr>
<th>Author Name : </th>
<th style="color:black"><input type="text" id="author" name="author"/></th>
</tr>
<tr>
<th colspan=2><input type="submit" value="Search" style="color:black"/></th>
</tr>
</table>
</form>
<br><br>
<%
if((request.getAttribute("result")!=null))
{
ResultSet rs= (ResultSet)request.getAttribute("result");
%>
<p>
<table>
<tr><th>Book Id </th><th>Title </th> <th>Author </th>
<th>Branch_id </th><th>No of copies </th><th>No of available copies </th></tr>
<%
while(rs.next())
{
%>

<tr>

<th><%=rs.getString("book_id") %> </th>
<th> <%=rs.getString("title") %></th>
<th> <%=rs.getString("author_name")%></th>
<th> <%=rs.getString("Branch_id")%></th>
<th> <%=rs.getString("No_of_copies")%></th>
<th> <%=rs.getString("Available_COUNT")%></th>


 </tr>
 
<%
}
}
%>

</table>
</body>
</html>