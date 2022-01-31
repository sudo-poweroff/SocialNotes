<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestione Utenti</title>
</head>
<body>
<% 
  String home = "homepage.jsp";
	if (session.getAttribute("username")==null){
		  response.sendRedirect("login.jsp");
		  return;
	}
	else if (((int)session.getAttribute("role"))!=1){
  	String encodeHomeURL = response.encodeRedirectURL(home);
  	response.sendRedirect(encodeHomeURL);
  	return;
  }


  %>

 <%@include file="headerUsersNotesManager.jsp" %>
 
<div class="container">
<br><br><br><br><br><br>
<h3>Benvenuto UsersManager <%=session.getAttribute("username") %></h3>
<br><br><br><br><br><br>
<div class="row">
  <div class="row row-cols-1 row-cols-md-3 g-4">
  <div class="col">
    <div class="card">
      <img src="img/segnalazioni.png" class="card-img-top" alt="...">
      <div class="card-body">
        <a href=<%=response.encodeURL("segnalazioni_eff.jsp") %>><h5 class="card-title">Visualizza segnalazioni</h5></a>
        <p class="card-text">visualizza tutte le segnalazioni riporate dai notes manager</p>
      </div>
    </div>
  </div>
  <div class="col">
    <div class="card">
      <img src="img/archivioReport.jpg" class="card-img-top" alt="...">
      <div class="card-body">
        <a href=<%=response.encodeURL("segnalazione.jsp") %>><h5 class="card-title">Visualizza segnalazioni archiviate</h5></a>
        <p class="card-text">visualizza tutte le segnalazioni che hai già gestito</p>
      </div>
    </div>
  </div>
  <div class="col">
    <div class="card">
      <img src="img/users.png" class="card-img-top" alt="...">
      <div class="card-body">
       <a href=<%=response.encodeURL("segnalazione.jsp") %>> <h5 class="card-title">Visualizza Utenti</h5></a>
        <p class="card-text">Visualizza tutti gli studenti bannati e non bannati</p>
      </div>
    </div>
  </div>

</div>
  
      
</div>
</div>
<br><br><br><br><br><br><br><br><br><br>
<%@include file="footer.jsp" %>
</body>
</html>