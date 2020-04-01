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
<form action="FineServlet" style="width:1159px;" method=post> <br><br><!-- method=post -->
<table align="center"> 
<tr>
<th>Card No : </th>
<th style="color:black" ><input type="text" id="cardno" name="cardno"/></th>
</tr>

<tr>
<th colspan=2><input type="submit" style="color:black"/></th>
</tr>
</table>
</form>
<br><br>
<%
if(request.getAttribute("fine")!=null)
{
%>

<form action="FineServlet2" style="width:1159px;" method=post> <br><br><!-- method=post -->
<table align="center"> 
<%
if(request.getAttribute("paid")!=null)
{
%>

<tr>

<th>PAID</th>
</tr>
<tr>
<th></th>
<th>Card no</th>
<th>Fine Amount</th>
</tr>
<%
ResultSet rs1 = (ResultSet) request.getAttribute("paid");
while(rs1.next())
{
%>
<tr>
<th></th>
<th> <%=rs1.getString("card_no") %></th>
<th> <%=rs1.getString("fine_amt") %></th></tr>

<%}}%>

<tr>
<th>TO BE PAID</th>
</tr>
<tr>
<th></th>

<th>Card no</th>
<th>Fine Amount</th>

</tr>

<%
ResultSet rs = (ResultSet) request.getAttribute("fine");
while(rs.next())
{
String str = rs.getString("card_no");
%>
<tr>
<th style="color:black" ><input type="radio" id="book_radio" name="book_radio" value=<%=str%>></th>

<th> <%=rs.getString("card_no") %></th>
<th> <%=rs.getString("fine_amt") %></th>

<%}%>
<tr>
<th colspan=2><input type="submit" value="Pay" style="color:black"/></th>
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