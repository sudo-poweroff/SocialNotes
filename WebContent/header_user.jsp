	<%@page import="it.unisa.model.UserBean"%>
<%@page import="it.unisa.model.UserModelDS"%>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
	<%@page import="javax.sql.DataSource"%>
	<%@page import="java.util.Collection"%>
	<%@page import="it.unisa.model.MaterialBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">

<style type="text/css">
@import url(css/navbar.css);
</style>
<style>
.icona {
	margin-left: 10px;
	margin-right: 10px;
}
</style>
</head>
<body>


	<%
	
	String chatLink = "chat.jsp";
	String priceLink = "prezzi.jsp";
	String newsLink = "news.jsp";
	String homeLink = "homepage.jsp";
	String changeLink = "change.jsp";
	String logoutLink = "homepage.jsp";
	String search = "searchFriend.jsp";
	String cartLink="cart.jsp";
	int coin = 0;
	int numeroElementi=0;
	 String adminURL = "admin.jsp";
	
	//Sessione: non posso fare la sendRedirect probabilmente perchè questo header viene incluso in jsp dove già ne viene fatta un'altra
	 if(session.getAttribute("username")!=null){
		  if (((int)session.getAttribute("role"))==1){
			  adminURL = response.encodeURL(adminURL);
			  response.sendRedirect(adminURL);
			  return;
		  }
		 
	    coin = (int)session.getAttribute("coin");
		homeLink = "homepage_user.jsp";
		logoutLink = "Logout";
		homeLink = response.encodeURL(homeLink);
		chatLink = response.encodeURL(chatLink);
		priceLink = response.encodeURL(priceLink);
		newsLink = response.encodeURL(newsLink);
		changeLink = response.encodeURL(changeLink);
		search = response.encodeURL(search);
		cartLink=response.encodeURL(cartLink);
		if ((session.getAttribute("cart")!=null)){
		Collection<MaterialBean>carrello=(Collection<MaterialBean>)session.getAttribute("cart");
		if(carrello!=null){
			 numeroElementi=carrello.size();
		}}
	}
	 
	
%>

	<nav
		class="navbar navbar-expand-lg navbar-dark colore-principale justify-content-end sticky-top">
		<a class="navbar-brand" href="<%=homeLink%>"> <img
			src="img/LogoDefinitivo1.png" height="25" width="150">
		</a>
		<button class="navbar-toggler my-1" type="button"
			data-toggle="collapse" data-target="#navbarText"
			aria-controls="navbarText" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse justify-content-end float-right"
			id="navbarText">
			<form class="form-inline " id="form1" style="padding-bottom:5px;" action="SearchServlet;jsessionid=<%=session.getId() %>" >
				 <input class="form-control mr-sm-2"  type="search" id="ricerca" name="ricerca" placeholder="Cerca Materiale" aria-label="Search">
				<button class="btn btn-dark btn-md my-2 my-sm-0 " type="submit">
					<i class="fas fa-search"></i> Cerca
				</button>
			</form>

			<!-- Magico-->
			<div class="ml-auto mr-3">

				<ul class="navbar-nav mr-auto">
					<!-- Chat -->

					<li class="nav-item icona"><a class="nav-link"
						href="<%=chatLink %>"> <svg style="color: white"
								xmlns="http://www.w3.org/2000/svg" width="24" height="24"
								fill="currentColor" class="bi bi-chat-text-fill"
								viewBox="0 0 16 16">
  							<path
									d="M16 8c0 3.866-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.584.296-1.925.864-4.181 1.234-.2.032-.352-.176-.273-.362.354-.836.674-1.95.77-2.966C.744 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7zM4.5 5a.5.5 0 0 0 0 1h7a.5.5 0 0 0 0-1h-7zm0 2.5a.5.5 0 0 0 0 1h7a.5.5 0 0 0 0-1h-7zm0 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4z" />
						</svg>
						
					</a>
				
					</li>



					<!-- Carrello -->
					<li class="nav-item icona"><a class="nav-link" href="<%=cartLink%>"> 
					<svg style="color: white"  width="24" height="24" focusable="true" data-prefix="fas" data-icon="cart-arrow-down" 
					class="svg-inline--fa fa-cart-arrow-down fa-w-18" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512">
					<path fill="currentColor" d="M504.717 320H211.572l6.545 32h268.418c15.401 0 26.816 14.301 23.403 29.319l-5.517 24.276C523.112 414.668 536 433.828 536 456c0 31.202-25.519 56.444-56.824 55.994-29.823-.429-54.35-24.631-55.155-54.447-.44-16.287 6.085-31.049 16.803-41.548H231.176C241.553 426.165 248 440.326 248 456c0 31.813-26.528 57.431-58.67 55.938-28.54-1.325-51.751-24.385-53.251-52.917-1.158-22.034 10.436-41.455 28.051-51.586L93.883 64H24C10.745 64 0 53.255 0 40V24C0 10.745 10.745 0 24 0h102.529c11.401 0 21.228 8.021 23.513 19.19L159.208 64H551.99c15.401 0 26.816 14.301 23.403 29.319l-47.273 208C525.637 312.246 515.923 320 504.717 320zM403.029 192H360v-60c0-6.627-5.373-12-12-12h-24c-6.627 0-12 5.373-12 12v60h-43.029c-10.691 0-16.045 12.926-8.485 20.485l67.029 67.029c4.686 4.686 12.284 4.686 16.971 0l67.029-67.029c7.559-7.559 2.205-20.485-8.486-20.485z">
					</path></svg>
			    	<span class="position-absolute top-0 start-0 translate-middle badge rounded-pill bg-danger"><%=numeroElementi %></span>
							
					</a></li>
					<!-- Coins -->
					<li class="nav-item icona" style="margin-right:30px"><a class="nav-link" href="#"> <svg
								style="color: yellow;" aria-hidden="true" focusable="false"
								width="24" height="24" data-prefix="fas" data-icon="coins"
								class="svg-inline--fa fa-coins fa-w-16" role="img"
								xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
						<path fill="currentColor"
									d="M0 405.3V448c0 35.3 86 64 192 64s192-28.7 192-64v-42.7C342.7 434.4 267.2 448 192 448S41.3 434.4 0 405.3zM320 128c106 0 192-28.7 192-64S426 0 320 0 128 28.7 128 64s86 64 192 64zM0 300.4V352c0 35.3 86 64 192 64s192-28.7 192-64v-51.6c-41.3 34-116.9 51.6-192 51.6S41.3 334.4 0 300.4zm416 11c57.3-11.1 96-31.7 96-55.4v-42.7c-23.2 16.4-57.3 27.6-96 34.5v63.6zM192 160C86 160 0 195.8 0 240s86 80 192 80 192-35.8 192-80-86-80-192-80zm219.3 56.3c60-10.8 100.7-32 100.7-56.3v-42.7c-35.5 25.1-96.5 38.6-160.7 41.8 29.5 14.3 51.2 33.5 60 57.2z"></path>
							
									  <span
									class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"><%=coin %>
									</span>
							
									
							</svg>
					</a></li>

					<li class="nav-item active"><a class="nav-link"
						href="<%=homeLink %>"> Home <span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="<%=newsLink %>">News</a></li>

					<li class="nav-item"><a class="nav-link"
						href="<%=priceLink %>">Pricing</a></li>
						
				    					<li class="nav-item"><a class="nav-link"
						href="<%=logoutLink %>">Logout</a></li>


					<!-- Icona utente -->

					<li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <svg style="color: white;"
										xmlns="http://www.w3.org/2000/svg" width="24" height="24"
										fill="currentColor" class="bi bi-person-lines-fill"
										viewBox="0 0 16 16">
  								<path
											d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5zm.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2z" />
							</svg>
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="left:-120px;">
          <a class="dropdown-item" href="<%=changeLink%>">Modifica profilo</a>
          <a class="dropdown-item" href="<%=search %>">Cerca persone</a>

        </div>
      </li>


				</ul>

			</div>

		</div>

	</nav>

 <% %>
</body>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>

</html>