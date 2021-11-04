<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ERROR 500!</title>
<link rel="icon" href="img/favicon.ico">
<script src="https://cdn.jsdelivr.net/particles.js/2.0.0/particles.min.js"></script> 
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/error500.css">
</head>
<body>
<% 
  String homepageUrl = "homepage_user.jsp";
  String homepageUrlAdmin = "admin.jsp";
  String link = "homepage.jsp";
  if ((session.getAttribute("username"))!=null){
	  if (((int)session.getAttribute("role"))==1){
		  link = response.encodeURL(homepageUrlAdmin);
	  }
		  link = response.encodeURL(homepageUrl);
  }
%>
<!--dust particel-->
<div>
  <div class="starsec"></div>
  <div class="starthird"></div>
  <div class="starfourth"></div>
  <div class="starfifth"></div>
</div>
<!--Dust particle end--->

<br>
<div class="lamp__wrap">
  <div class="lamp">
    <div class="cable"></div>
    <div class="cover"></div>
    <div class="in-cover">
      <div class="bulb"></div>
    </div>
    <div class="light"></div>
  </div>
</div>
<!-- END Lamp -->
<section class="error">
  <!-- Content -->
  <div class="error__content">
    <div class="error__message message">
      <h1 class="message__title">ERROR 500</h1>
      <p class="message__text">Spiacenti, errore interno del server. Riprova pi&ugrave; tardi.</p>
    </div>
    <div class="error__nav e-nav">
      <a href="<%=link %>" class="e-nav__link"></a>
    </div>
  </div>
  <!-- END Content -->

</section>
</body>
</html>