<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="profilo.UserModelDS"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>Login</title>

    <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    	<link rel="stylesheet" href="css/login.css">
    
  </head>



  <body class="text-center">
  <%
  String link ="homepage.jsp";
  if(session.getAttribute("username")!=null){
	    DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		String username=(String)session.getAttribute("username");
		UserModelDS role=new UserModelDS(ds);
		
		
		
	}
  %>
 
 	<%
	String errorePin = (String) request.getAttribute("errorPin");
	if (errorePin != null) {
	%>

	<div class="alert alert-danger alert-dismissible fade show" role="alert">
		<strong>Attenzione!</strong> Il pin inserito non &egrave; corretto.
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

	<%
	}
	%>
	
	 	<%
	String erroreUsername = (String) request.getAttribute("errorUsername");
	if (erroreUsername != null) {
	%>

	<div class="alert alert-danger alert-dismissible fade show" role="alert">
		<strong>Attenzione!</strong> Username non trovato.
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

	<%
	}
	%>
  		
  		
  		
    <form class="form-signin" action="RecoveryPass" method="POST" >
      <a href="<%=link %>"><img class="d-block mx-auto mb-4" src="img/logo.png" alt="SocialNotes" width="140" height="140"></a>
      <h1 class="h3 mb-3 font-weight-normal">Recupero Password</h1>
      
		<%
      if(session.getAttribute("pin")!=null){
      %>
      
	  <h2 class="h3 mb-3 font-weight-normal">Inserisci nuovamente il tuo Username e il pin che ti abbiamo inviato via mail</h2>
	  <label for="inputUser" class="sr-only">Username</label>
      <input type="text" name="inputUser" id="inputUser" class="form-control" placeholder="username" required autofocus>
	  <br>
      <label for="inputPin" class="sr-only">Pin</label>
      <input type="text" name="inputPin" id="inputPin" class="form-control" placeholder="PIN" required autofocus>
     
      <br>
	<%
      }else{
	%>
	   <label for="inputUser" class="sr-only">Username</label>
      <input type="text" name="inputUser" id="inputUser" class="form-control" placeholder="username" required autofocus>
		<br>
      <%} %>
	
      <button class="btn btn-lg btn-principale btn-block" type="submit">Recupera password</button>
      <button class="bottoneindietro" onclick="history.back()">Torna indietro</button>
      <p class="mt-5 mb-3 text-muted">&copy; 2021 SocialNotes</p>
    </form>




    
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    
    
    
  </body>
</html>
