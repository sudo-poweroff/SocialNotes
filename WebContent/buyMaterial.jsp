<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Acquisto materiale</title>
<link href="https://fonts.googleapis.com/css?family=Nunito+Sans:400,400i,700,900&display=swap" rel="stylesheet">
<link rel="icon" href="img/favicon.ico">

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">


<link href="css/login.css" rel="stylesheet">

	<style>
body {
	text-align: center;
	padding: 40px 0;
	background: #f8f9fa;
}

h1 {
	color: #88B04B;
	font-family: "Nunito Sans", "Helvetica Neue", sans-serif;
	font-weight: 900;
	font-size: 40px;
	margin-bottom: 10px;
}

p {
	color: #404F5E;
	font-family: "Nunito Sans", "Helvetica Neue", sans-serif;
	font-size: 20px;
	margin: 0;
}

i {
	color: #9ABC66;
	font-size: 100px;
	line-height: 200px;
	margin-left: -15px;
}

.card {
	background: white;
	padding: 60px;
	border-radius: 4px;
	box-shadow: 0 2px 3px #C8D0D8;
	display: inline-block;
	margin: 0 auto;
}
</style>

</head>



<body>
<%

if (session.getAttribute("username")==null)
	response.sendRedirect("signup.jsp");
else{
	
	String linkHomepage= "homepage_user.jsp";
	 String encodedURL = response.encodeURL(linkHomepage);
	 String priceUrl= response.encodeURL("prezzi.jsp");
	 String provaZipUrl=response.encodeURL("ProvaZip");
	 String cartUrl=response.encodeURL("cart.jsp");
	 %>

		
	<div class="card">
	<% 
			String tot=request.getParameter("tot");
			int totale=Integer.parseInt(tot);
			int coin=(int)session.getAttribute("coin");
			if(totale==0){
				
				
				
	%>
	<div style="border-radius: 200px; height: 200px; width: 200px; background: #F8FAF5; margin: 0 auto;">
			<i>	&#10060;</i>
		</div>

		<h1 style=" color:#ff0000">OPS!</h1>
		<p style="color:#ff0000">
			Inserisci prima qualcosa nel tuo carrello!
		</p>
		<br>
		<button class="btn btn-principale btn-lg" onclick="window.location.href='<%=cartUrl%>'">Vai al carrello</button>
		<br>
		<br>
		<button class="btn btn-light btn-lg" onclick="window.location.href='<%=encodedURL%>'">Vai alla Home</button>
	<%		
			}
			
			else if(totale<=coin) {
				
		
		%>
		<div style="border-radius: 200px; height: 200px; width: 200px; background: #F8FAF5; margin: 0 auto;">
			<i>&#10003;</i>
		</div>

		<h1>Acquisto ultimato</h1>
		<p>
			Effettua il download!
		</p>
		<br>
		<button class="btn btn-principale btn-lg" onclick="window.location.href='<%=provaZipUrl%>?tot=<%=totale%>'">Scarica i tuoi appunti</button>
		<br>
		<br>
		<button class="btn btn-light btn-lg" onclick="window.location.href='<%=encodedURL%>'">Vai alla Home</button>
		<%
			}
			else{
		%>
		<div style="border-radius: 200px; height: 200px; width: 200px; background: #F8FAF5; margin: 0 auto;">
			<i style="color:red">&#10060;</i>
		</div>

		<h1 style=" color:#ff0000">OPS!</h1>
		<p style="color:#ff0000">
			Non hai abbastanza coin!
		</p>
		<br>
		<button class="btn btn-principale btn-lg" onclick="window.location.href='<%=priceUrl%>'">Acquista coin</button>
		<br>
		<br>
		<button class="btn btn-light btn-lg" onclick="window.location.href='<%=encodedURL%>'">Vai alla Home</button>
		
		<%	} %>
	</div>
<%
		
}
%>
</body>
</html>