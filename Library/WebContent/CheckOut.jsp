<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:include page="index.html"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Library Management System</title>
</head>
<body>
<form action="CheckOutServlet" style="width:1159px;" method=post> <br><br><!-- method=post -->
<table align="center"> 

<tr>
<th>Enter Book Id : </th>
<th style="color:black" ><input type="text" id="bookid" name="bookid" required/></th>
</tr>
<tr>
<th>Enter Branch ID : </th>
<th style="color:black"><input type="text" name="branchid" required/></th>
</tr>
<tr>
<th>Card No : </th>
<th style="color:black"><input type="text" name="cardno" required/></th>
</tr>
<tr>
<th colspan=2><input type="submit" value="Check Out" style="color:black"/></th>
</tr>
</table>
</form>
<br><br>
<%
if(request.getAttribute("message")!=null)
{
%>
<p style="color: white"> <%=request.getAttribute("message")%>

<%} %>
</p>
</body>
</html>