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
<form action="CheckInServlet" style="width:1159px;" method=post> <br><br><!-- method=post -->
<table align="center"> 
<tr>
<th>Enter Book Id : </th>
<th style="color:black" ><input type="text" id="bookid" name="bookid"/></th>
</tr>
<tr>
<th>Enter Name : </th>
<th style="color:black"><input type="text" name="borrower_name"/></th>
</tr>
<tr>
<th>Card No : </th>
<th style="color:black"><input type="text" name="cardno"/></th>
</tr>
<tr>
<th colspan=2><input type="submit" value="Search" style="color:black"/></th>
</tr>
</table>
</form>
<br><br>
<%
if(request.getAttribute("loans")!=null)
{
%>

<form action="CheckInServlet2" style="width:1159px;" method=post> <br><br><!-- method=post -->
<table align="center"> 
<tr>
<th></th>
<th>Card No</th>
<th>Borrower Name</th>
<th>Book Id</th>
<th>Book Title</th>
<th>Branch Id</th>

</tr>

<%
ResultSet rs = (ResultSet) request.getAttribute("loans");
while(rs.next())
{
String str = rs.getString("book_id")+","+rs.getString("branch_id")+","+rs.getString("card_no")+","+rs.getString("Loan_id");
%>
<tr>
<th style="color:black" ><input type="radio" id="book_radio" name="book_radio" value=<%=str%>></th>
<th><%=rs.getString("card_no") %></th>
<th> <%=rs.getString("name") %></th>
<th><%=rs.getString("book_id") %> </th>
<th> <%=rs.getString("title") %></th>
<th> <%=rs.getString("branch_id") %></th>

<%}%>
<tr>
<th colspan=2><input type="submit" value="Check In"  style="color:black"/></th>
</tr>
</table>
</form>
<%
}%>

<br><br><br>
<%
if(request.getAttribute("message")!=null)
{
%>
<p style="color: white"> <%=request.getAttribute("message")%>

<%} %>
</body>
</html>