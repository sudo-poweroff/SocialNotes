<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
<style>
.footer {
   position: fixed;
   left: 0;
   bottom: 0;
   width: 100%;
 
}
</style>
</head>
<body><% 
String aboutUsURL="aboutUs.jsp";
if(session.getAttribute("username")!=null){
	aboutUsURL = response.encodeURL(aboutUsURL);
	
}
%>

<footer class="text-white" style="background-color:#9697e6">
<br>

<div class="container">
       <div class="row">
	   
          <div class="col-md-3">
            <img class="mb-2" src="img/logo.png" alt="" width="100" height="100">
			         <small class="d-block mb-3">&copy;SocialNotes 2020-2021</small>
          </div>
          <div class="col-md-3">
            <h5>About</h5>
            <ul class="list-unstyled text-small">
              <li><a class="text-white" href="<%=aboutUsURL %>">Chi siamo</a></li>
              <li><a class="text-white" href="PrivacyPolicy.html">Privacy & Terms</a></li>
            </ul>
          </div>
          <div class="col-md-3">
            <h5>Resources</h5>
            <ul class="list-unstyled text-small">
              <li><a class="text-white" href="#top">Back to top</a></li>
 
            </ul>
          </div>
          <div class="col-md-3">
            <h5>Contattaci</h5>
            <ul class="list-unstyled text-small">
              <li><a class="text-white" href="mailto:socialnotes@gmail.com">E-mail</a></li>
              <li><a class="text-white" href="https://www.instagram.com">Instagram</a></li>
              <li><a class="text-white" href="https://www.facebook.com">Facebook</a></li>
              <li><a class="text-white" href="https://www.twitter.com">Twitter</a></li>
            </ul>
          </div>
        </div>
		</div>
	  <br>
    </footer>
</body>
</html>