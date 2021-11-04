<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="it.unisa.model.UserRoleModelDS"%>
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
  String signUp = "signup.jsp";
  String recoveryPass = "RecoveryPassword.jsp";
  if(session.getAttribute("username")!=null){
	  
	  signUp = response.encodeURL(signUp);
	  recoveryPass = response.encodeURL(recoveryPass);
	  
	    DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		String username=(String)session.getAttribute("username");
		UserRoleModelDS role=new UserRoleModelDS(ds);
		try {
			int userRole=role.doRetrieveByUsername(username);
			if(userRole==1) {
				link = "admin.jsp";
				String encodedURL = response.encodeRedirectURL(link);
				response.sendRedirect(encodedURL);
			}
			else{
				link = "homepage_user.jsp";
				String encodedURL = response.encodeRedirectURL(link);
				response.sendRedirect(encodedURL);
			}
		}catch(SQLException e){}
		
		
	}
  %>
  		<%
	String errore = (String) request.getAttribute("error");
	if (errore != null) {
	%>

	<div class="alert alert-danger alert-dismissible fade show" role="alert">
		<strong>Attenzione!</strong> Nome utente e/o password non corrispondono.
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

	<%
	}
	%>
  		
  		
  		
    <form class="form-signin" action="Login" method="POST" >
      <a href="<%=link%>"><img class="d-block mx-auto mb-4" src="img/logo.png" alt="SocialNotes" width="140" height="140"></a>
      <h1 class="h3 mb-3 font-weight-normal">Effettua il Login</h1>
      <label for="inputEmail" class="sr-only">Email</label>
      <input type="text" name="login"id="inputEmail" class="form-control" placeholder="Indirizzo mail o username" required autofocus>
      <br>
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
      
      <br>
      
      <div class="text-left">
      	<a href="<%=signUp %>" class="text-secondary">Registrati ora</a>
      </div>
      <button class="btn btn-lg btn-principale btn-block" type="submit">Log In</button>
      <button class="bottoneindietro" onclick="history.back()">Torna indietro</button>
      
      <div class="text-left">
      	<a href="<%=recoveryPass %>" class="text-secondary">Hai dimenticato la password?</a>
      </div>
      <p class="mt-5 mb-3 text-muted">&copy; 2021 SocialNotes</p>
    </form>




    
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    
    
    
  </body>
</html>
    