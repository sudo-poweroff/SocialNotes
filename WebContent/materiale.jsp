<%@page import="it.unisa.model.MaterialModelDS"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="com.mysql.cj.jdbc.Blob"%>
 <%@page import="java.io.InputStream"%>  
 <%@page import="java.util.Collection"%>
 <%@page import="it.unisa.model.MaterialBean"%> 
<%@page import="it.unisa.model.UserModelDS"%> 
<%@page import="it.unisa.model.UserBean"%> 
 <%@page import="java.util.Iterator"%>
 <%@page import="java.sql.Date"%>
  <%@page import="javax.sql.DataSource"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <link rel="icon" href="img/favicon.ico">
    <title>Gestione Materiale</title>
    <link rel="stylesheet" type="text/css" href="css/materiale.css">


<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

  </head>
  <body>
  <%
String homeUser = "homepage_user.jsp";
	if (session.getAttribute("username")==null){
		  response.sendRedirect("login.jsp");
	       return;
	}else if (((int)session.getAttribute("role"))!=1){
    	String encodeHomeUserURL = response.encodeRedirectURL(homeUser);
    	response.sendRedirect(encodeHomeUserURL);
    	return;
    }

	  DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
	  MaterialModelDS material=new MaterialModelDS(ds);
	  UserModelDS user=new UserModelDS(ds);
	  Collection<MaterialBean> materials=material.notValidated();
  %>

 <%@include file="header_admin.jsp" %>
 
    <div class="container">
    	<div class="row">
    	 
    	
    	<%
	    	if(materials!=null&&materials.size()>0){
				Iterator<?> it=materials.iterator();
				while(it.hasNext()){
    				MaterialBean mat=(MaterialBean)it.next();
    				UserBean bean=user.doRetrieveByUsername(mat.getUsername());
    				Date dataAttuale = new Date(System.currentTimeMillis());
					Date dataCaricamento=mat.getDataCaricamento();
					long diffInMillies=dataAttuale.getTime()-dataCaricamento.getTime();
					long diff=TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
    				
    	%>
    	<div class="card social-timeline-card">
					<div class="card-header">
						<div class="d-flex justify-content-between align-items-center">
							<div class="d-flex justify-content-between align-items-center">
								<div class="mr-2">
									<img class="rounded-circle"src="PrintImage?username=<%=mat.getUsername()%>" alt="ciao" width="45">
								</div>
								<div class="ml-2">
									<div class="h5 m-0 text-blue"><%=mat.getUsername() %></div>
									<div class="h7 text-muted"><%=bean.getNome() %> <%=bean.getCognome() %></div>
								</div>
							</div>
							<div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<div class="text-muted h7 mb-2">
							<i class="fa fa-clock-o"></i><%=diff %> days ago
						</div>
						<a class="card-link" href="viewFile.jsp?filename=<%=mat.getFileName()%>"><h5 class="card-title"><%=mat.getDescrizione() %></h5> </a> 
						<img src="PrintAnteprima?codice=<%=mat.getCodiceMateriale() %>" height="500px" width="500px"
							class="img-fluid">
					</div>
					<div class="card-footer">
						<form method="post" class="form shadow p-3 mb-5 bg-white rounde" action=<%="SetPrice;jsessionid="+session.getId()%> enctype="multipart/form-data">
							<div class="form-row">
							<div class="form-group col-md-4">
								<input type="hidden" name="codice" value="<%=mat.getCodiceMateriale() %>">
								<input type="hidden" name="username" value="<%=mat.getUsername()%>">
								<input type="number" class="form-control" name="costo" id="costo" placeholder="Costo" required>&nbsp&nbsp&nbsp&nbsp
								
						</div>
						
						
					
							<div class="form-group col-md-3">
								<button type="submit" class="btn bottone-principale text-light" style="background-color: #9697e7" onclick="prova()">Imposta prezzo</button>
								
						</div>
						
						</div>
						</form>
					</div>
				</div>
				
				<br>
				<hr>
				<%}} %>
				
			
    	</div>
    	</div>
   
    <br>
<%@include file="footer.jsp" %>
  </body>
</html>
