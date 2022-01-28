<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="img/favicon.ico">
    <title>Gestione News</title>
    <link rel="stylesheet" type="text/css" href="css/notizie.css">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="js/notizie.js" charset="utf-8"></script>
  </head>
  <body>
  <%
String homeUser = "homepage_user.jsp";
	if (session.getAttribute("username")==null){
		  response.sendRedirect("login.jsp");
		  return;
	}
	else if (((int)session.getAttribute("role"))!=1){
    	String encodeHomeUserURL = response.encodeRedirectURL(homeUser);
    	response.sendRedirect(encodeHomeUserURL);
    	return;
    }
	
	
	%>
<%@include file="header_admin.jsp" %>	
	<%
	String error = (String)request.getAttribute("error");
	if(error!=null){
		%>
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
		<strong>Attenzione!</strong> Il file non &egrave; stato caricato correttamente. Si prega di riprovare.
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
	<%
	}
	%>
	<%
	String success = (String)request.getAttribute("success");
	if(success!=null){
	%>
					<div class="alert alert-success alert-dismissible fade show" role="alert">
		 Il file &egrave; stato caricato correttamente.
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
	<%
	}
	%>
	



<div class="container">
<div class="row">
<div class="col-md-3"></div>
    <div class="col-md-6">
<div class="file-upload">
<form method="post" action=<%="NewsUploadServlet;jsessionid="+session.getId()%> enctype="multipart/form-data">
	  <div class="image-upload-wrap">
	    <input class="file-upload-input" name="img" type='file' onchange="readURL(this);" accept="image/*" required/>
	    <div class="drag-text">
	      <h3>TRASCINA E RILASCIA UNA FOTO</h3>
	    </div>
	  </div>
	  <div class="file-upload-content">
	    <img class="file-upload-image" src="#" alt="your image" />
	    <div class="image-title-wrap">
	      <button type="button" onclick="removeUpload()" class="remove-image">RIMUOVI <span class="image-title">FILE CARICATI</span></button>
	    </div>
	  </div>
	  <br>
	  <textarea placeholder="Inserisci il titolo della news" rows="3" class="form-control" name="titolo" required></textarea>
	  <br>
	  <textarea placeholder="Inserisci l'argomento trattato" rows="3" class="form-control" name="argomento" required></textarea>
	  <br>
	  <textarea placeholder="Inserisci il contenuto" rows="4" class="form-control" name="contenuto" required></textarea>
	  <br>
	  	<div class="btn-toolbar justify-content-between">
			<div class="btn-group">
				<button type="submit" class="btn bottone-principale text-light" style="background-color: #9697e7" onclick="prova()">Pubblica News</button>
			</div>
	    </div>
</form>
</div>
</div>
<div class="col-md-3"></div>
</div>
</div>
<br>

<br>


<%@include file="footer.jsp" %>
  </body>
  <script class="jsbin" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
</html>
