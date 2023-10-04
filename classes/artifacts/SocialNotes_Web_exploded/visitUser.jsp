<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="profilo.*,acquisto.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="materiale.*"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.sql.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" href="img/favicon.ico">
<title>Profilo Amico</title>
 <link rel="stylesheet" type="text/css" href="css/visitUser.css">
 <link rel="stylesheet" type="text/css" href="css/documento1.css">
</head>
<body>
<%@include file="header_user.jsp" %>

<%
String cartError = (String)request.getAttribute("cartError");
if(cartError!=null){
	%>
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
	<strong>Attenzione!</strong> Il file &egrave; gi&agrave; presente nel carrello
	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
<%
}
%>

<%
    String addFriendUrl = "AddFriend";
    String removeFriendUrl = "RemoveFriend";
    String chatCreateUrl = "ChatCreateServlet";
    String documentPreviewUrl = "documentPreview.jsp";
    String addCartUrl = "AddToCart";
    if(((session.getAttribute("username"))!=null)&&(((int)session.getAttribute("role"))==0)){
    	chatCreateUrl = response.encodeURL(chatCreateUrl);
    	addFriendUrl = response.encodeURL(addFriendUrl);
    	removeFriendUrl = response.encodeURL(removeFriendUrl);
    	documentPreviewUrl = response.encodeURL(documentPreviewUrl);
    	addCartUrl = response.encodeURL(addCartUrl);
    }else{
    	response.sendRedirect("homepage.jsp");
    	return;
    }

	DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
	String friendname;
	if((String)request.getParameter("friendname")!=null){
		friendname=(String)request.getParameter("friendname");
	}
	else{
		friendname=(String)request.getAttribute("friendname");
	}
	UserModelDS user=new UserModelDS(ds);
	UserBean bean=user.doRetrieveByUsername(friendname);
	String username=(String)session.getAttribute("username");
	
	
	MaterialModelDS material=new MaterialModelDS(ds);
	int quantitaMateriale=material.getQuantitaMaterialeCondiviso(friendname);
	float media=user.getValutazione(friendname);
	
	Collection<MaterialBean> materials=material.doRetrieveByUsername(friendname);
%>

<div class="container">
		<div class="main-body">
			<div class="row">
				<div class="col-lg-4">
					<div class="card">
						<div class="card-body">
							<div class="d-flex flex-column align-items-center text-center">
								<img src="PrintImage?username=<%=friendname %>" alt="Admin" class="rounded-circle p-1 bg-primary" width="110">
								<div class="mt-3">
									<h4><%=bean.getUsername() %></h4>
									<p class="text-secondary mb-1"><%=bean.getDenominazione() %></p>
									<p class="text-muted font-size-sm"><%=bean.getDipName() %></p>
									<%
										FriendsModelDS friend=new FriendsModelDS(ds);
										if(friend.isFriend(friendname, username)){
									%>
									<a href="<%=removeFriendUrl%>?friendname=<%=bean.getUsername() %>"><button class="btn btn-primary" id="rimuovi">Rimuovi Amico</button></a>
									<%}else{ %>
									<a href="<%=addFriendUrl %>?friendname=<%=bean.getUsername() %>"><button class="btn btn-primary" id="aggiungi">Aggiungi Amico</button></a>
									<%} %>
								
									<form method="post" action="<%=chatCreateUrl %>" >
									<br>
									<input type="text" name="titolo" id="titolo" hidden value='Chat tra <%=session.getAttribute("username")%> e  <%=bean.getUsername() %>'>
									<input type="text" name="amici" id="amici" hidden value="<%=bean.getUsername() %>">
									<button class="btn btn-outline-primary" id="messaggio">Invia Messaggio</button></a>
									</form>
								</div>
							</div>
							<hr class="my-4">
							<ul class="list-group list-group-flush">
								<li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
									<h6 class="mb-0"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-globe me-2 icon-inline"><circle cx="12" cy="12" r="10"></circle><line x1="2" y1="12" x2="22" y2="12"></line><path d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"></path></svg>Website</h6>
									<a href="homepage.jsp"><span class="text-secondary">SocialNotes</span></a>
								</li>
								<li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
									<h6 class="mb-0"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-github me-2 icon-inline"><path d="M9 19c-5 1.5-5-2.5-7-3m14 6v-3.87a3.37 3.37 0 0 0-.94-2.61c3.14-.35 6.44-1.54 6.44-7A5.44 5.44 0 0 0 20 4.77 5.07 5.07 0 0 0 19.91 1S18.73.65 16 2.48a13.38 13.38 0 0 0-7 0C6.27.65 5.09 1 5.09 1A5.07 5.07 0 0 0 5 4.77a5.44 5.44 0 0 0-1.5 3.78c0 5.42 3.3 6.61 6.44 7A3.37 3.37 0 0 0 9 18.13V22"></path></svg>Github</h6>
									<a href="https://github.com/"><span class="text-secondary">Github</span></a>
								</li>
								<li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
									<h6 class="mb-0"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-twitter me-2 icon-inline text-info"><path d="M23 3a10.9 10.9 0 0 1-3.14 1.53 4.48 4.48 0 0 0-7.86 3v1A10.66 10.66 0 0 1 3 4s-4 9 5 13a11.64 11.64 0 0 1-7 2c9 5 20 0 20-11.5a4.5 4.5 0 0 0-.08-.83A7.72 7.72 0 0 0 23 3z"></path></svg>Twitter</h6>
									<a href="https://twitter.com/?lang=it"><span class="text-secondary">Twitter</span></a>
								</li>
								<li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
									<h6 class="mb-0"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-instagram me-2 icon-inline text-danger"><rect x="2" y="2" width="20" height="20" rx="5" ry="5"></rect><path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"></path><line x1="17.5" y1="6.5" x2="17.51" y2="6.5"></line></svg>Instagram</h6>
									<a href="https://www.instagram.com/"><span class="text-secondary">Instagram</span></a>
								</li>
								<li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
									<h6 class="mb-0"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-facebook me-2 icon-inline text-primary"><path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"></path></svg>Facebook</h6>
									<a href="https://www.facebook.com/"><span class="text-secondary">Facebook</span></a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-lg-8">
					<div class="card">
						<div class="card-body">
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0">Nome</h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<h5 class="mb-0"><%=bean.getNome() %></h5>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0">Cognome</h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<h5 class="mb-0"><%=bean.getCognome() %></h5>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0">Nome Utente</h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<h5 class="mb-0"><%=bean.getUsername() %></h5>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0">Email</h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<h5 class="mb-0"><%=bean.getEmail() %></h5>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col-sm-3">
									<h6 class="mb-0">Data di Nascita</h6>
								</div>
								<div class="col-sm-9 text-secondary">
									<h5 class="mb-0"><%=bean.getDataNascita() %></h5>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-3"></div>
								<div class="col-sm-9 text-secondary">
									
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="card">
								<div class="card-body">
									<h5 class="d-flex align-items-center mb-3"> Progressi Materiale</h5>
									<p>Materiale Caricato</p>
									<div class="progress mb-3" style="height: 5px">
										<div class="progress-bar bg-primary" role="progressbar" style="width: <%=quantitaMateriale %>%" aria-valuenow="<%=quantitaMateriale %>" aria-valuemin="0" aria-valuemax="100"></div>
									</div>
									<p>Valutazione</p>
									<li class="list-group-item">
								<canvas class="myCanvas" data-rating="<%=media %>" width="100"
									height="20">
not support the canvas tag.</canvas>
							</li>
									
									
									
									
								</div>
								
							</div>
							
						</div>
					</div>
					
					
					
					<%
									if(materials!=null&&materials.size()>0){
										Iterator<?> it=materials.iterator();
										while(it.hasNext()){
											MaterialBean mat=(MaterialBean)it.next();
											Date dataAttuale = new Date(System.currentTimeMillis());
											Date dataCaricamento=mat.getDataCaricamento();
											long diffInMillies=dataAttuale.getTime()-dataCaricamento.getTime();
											long diff=TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
								%>
					<br><br>
					<div class="card social-timeline-card">
					<div class="card-header">
						<div class="d-flex justify-content-between align-items-center">
							<div class="d-flex justify-content-between align-items-center">
								<div class="mr-2">
									<img class="rounded-circle" src="PrintImage?username=<%=friendname %>" alt="ciao" width="45">
								</div>
								<div class="ml-2">
									<div class="h5 m-0 text-blue"><%=bean.getUsername() %></div>
									<div class="h7 text-muted"><%=bean.getNome() %> <%=bean.getCognome() %></div>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<div class="text-muted h7 mb-2">
							<i class="fa fa-clock-o"></i><%=diff %> days ago
						</div>
						<a style="color:black" class="card-link" href="<%=documentPreviewUrl %>?codice=<%=mat.getCodiceMateriale()%>"><h5 class="card-title"><%=mat.getDescrizione() %></h5> </a>
						<img src="PrintAnteprima?codice=<%=mat.getCodiceMateriale() %>" class="img-fluid" width="500px" height="500px">
					</div>
					<div class="card-footer">
										<%
						//verifico se l'utente ha gi� comprato quel materiale
					
						PurchaseModelDS acquistiModel = new PurchaseModelDS(ds);
						Collection<PurchaseBean> acquistiEffettuati = acquistiModel.doRetrieveByUsername(username);
						
						boolean acquistato=false;
						for(PurchaseBean acquisto : acquistiEffettuati){
							if(acquisto.getCodiceMateriale()==mat.getCodiceMateriale())
								acquistato=true;
						}
						
						if(acquistato){
						
						%>
							<a href="<%=response.encodeURL("storicoMateriale.jsp")%>" style="color:#9697e7">Visualizza tra gli acquisti </a>
						
						<%
						}else{
						%>
					
					
						<a href="<%=addCartUrl %>?codice=<%=mat.getCodiceMateriale() %>&url=visitUser.jsp&friendname=<%=bean.getUsername() %>" style="color:#9697e7">Aggiungi al carello <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-plus-fill" viewBox="0 0 16 16">
  <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM9 5.5V7h1.5a.5.5 0 0 1 0 1H9v1.5a.5.5 0 0 1-1 0V8H6.5a.5.5 0 0 1 0-1H8V5.5a.5.5 0 0 1 1 0z"/>
</svg></a>
						<%}
						%>
					</div>
				</div>
				<%}} %>
				</div>
			</div>
		</div>
	</div>
	<br><br><br><br>



<%@include file="footer.jsp" %>
</body>
<script src="js/homepage_user.js"></script>

</html>