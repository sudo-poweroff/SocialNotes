<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error - SocialNotes</title>

<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<link rel="stylesheet" href="css/errorSearch.css">

</head>
<body>

<%
    String homeURL = "homepage.jsp";
    if(session.getAttribute("username")!=null){
    	homeURL="homepage_user.jsp";
    	String encodedURL = response.encodeRedirectURL(homeURL);
    	homeURL = encodedURL;
    	%>
    	
    	 <jsp:include page="header_user.jsp"></jsp:include>
    	<% 
    }else{
%>

    	 <jsp:include page="header.jsp"></jsp:include>

<%} %>
<br><br><br>
<div class="container">
<div class="row">
    <div class="col-md-12 col-sm-12">
        <div class="card shadow-lg border-0 rounded-lg mt-5 mx-auto" style="width: 70%;">
            <h3 class="card-header display-1 text-muted text-center">
                OPS!!
            </h3>

            <span class="card-subtitle mb-2 text-muted text-center">
                Il risulato non è stato trovato
            </span>

            <div class="card-body mx-auto">
                <a type="button" href="<%=homeURL %>"
                class="btn btn-sm btn-info text-white"> Back To Home </a>
            </div>
        </div>
    </div>
</div>
</div>

<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<%@include file="footer.jsp" %>

</body>
</html>