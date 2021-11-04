<%@page import="java.io.InputStream"%>
<%@page import="it.unisa.model.MaterialModelDS"%>
<%@page import="it.unisa.model.FriendsModelDS"%>
<%@page import="it.unisa.model.FriendsBean"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="com.mysql.cj.jdbc.Blob"%>
<%@page import="it.unisa.model.MaterialBean"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SocialNotes - Home</title>

<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">




<link rel="stylesheet" href="package/dist/sweetalert2.min.css">

<style type="text/css">
@import url("css/homepage_user.css");
</style>

<style>
.form-control::file-selector-button {
	padding: 0.55rem 0.75rem;
	margin: -0.375rem -0.75rem;
	-webkit-margin-end: 0.75rem;
	margin-inline-end: 0.75rem;
	color: white;
	background-color: #9697e7;
	pointer-events: none;
	border-color: inherit;
	border-style: solid;
	border-width: 0;
	border-inline-end-width: 1px;
	border-radius: 0;
	transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out,
		border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

#img-2 {
	filter: blur(10px);
	-webkit-filter: blur(10px);
}
</style>



</head>
<body>

	<%
  if (session.getAttribute("username")==null)
	  response.sendRedirect("login.jsp");

  String nome = (String)session.getAttribute("nome");
  String cognome = (String)session.getAttribute("cognome");
  String dipName = (String)session.getAttribute("dipName");
  String universita = (String)session.getAttribute("denominazione");
  String username=(String)session.getAttribute("username");
  Blob img=(Blob)session.getAttribute("img");
  if(img!=null){
  	InputStream imgProfile=img.getBinaryStream();
  }
  DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
  FriendsModelDS friends=new FriendsModelDS(ds);
  int numeroAmici=friends.getNumerFriends(username);
  MaterialModelDS material=new MaterialModelDS(ds);
  int quantitaMateriale=material.getQuantitaMaterialeCondiviso(username);
  Collection<FriendsBean> f=friends.doRetrieveByUsername(username);
  UserModelDS user=new UserModelDS(ds);
  float media=user.getValutazione(username);
  System.out.println("media"+media);
  
  String visitUserLink="visitUser.jsp";
  String addCartLink ="AddToCart";
  String documentPreviewLink =null;
  if(username!=null){
	  visitUserLink=response.encodeURL(visitUserLink);
	  documentPreviewLink=response.encodeURL(documentPreviewLink);
	  addCartLink = response.encodeURL(addCartLink);
  }
  
  
  Collection<MaterialBean> materials=material.doRetrieveByOrderDate();
%>

	<%@ include file="header_user.jsp"%>
	
	<%
	String errore = (String) request.getAttribute("error");
	String success= (String) request.getAttribute("success");
	if (errore != null) {
	%>

	<div class="alert alert-danger alert-dismissible fade show" role="alert">
		<strong>Attenzione!</strong> Il materiale non &egrave; stato caricato correttamente, si prega di riprovare.
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

	<%
	}
	%>
	
	<%
	if(success!=null){
	%>
	
		<div class="alert alert-success alert-dismissible fade show" role="alert">
		 Il materiale &egrave; stato caricato correttamente!
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
	
	<%
	}
	%>
	
	<br>
	<main class="container">


		<script>
			function prova() {
				Swal.fire('Good job!', 'You clicked the button!', 'success')
			}
		</script>

		<div class="row g-5">

			<div class="col-md-4">
				<div class="position-sticky" style="top: 2rem;">
					<div class="card">
						<div class="card-body">
							<div class="d-flex flex-column align-items-center text-center">
									<img src="PrintImage?username=<%=username %>" alt="pippo" class="rounded-circle" width="150">
								<div class="mt-3">
									<h4><%=nome%>
										<%=cognome %></h4>
									<p class="text-secondary mb-1"><%=dipName %></p>
									<p class="text-muted font-size-sm"><%=universita %></p>
								</div>
							</div>
						</div>
					</div>



					<div class="card">

						<ul class="list-group list-group-flush">
							<li class="list-group-item">
								<div class="h6 text-muted">Amici</div>
								<div class="h5"><%=numeroAmici %></div>
							</li>
							<li class="list-group-item">
								<div class="h6 text-muted">Materiale condiviso</div>
								<div class="h5"><%=quantitaMateriale %></div>
							</li>
							<li class="list-group-item">
								<div class="h6 text-muted">Valutazione</div>
								<canvas class="myCanvas" data-rating="<%=media %>" width="100" height="20">not support the canvas tag.</canvas>
							</li>
						</ul>
					</div>


					<div class="card social-timeline-card">
						<div class="card-body">
							<h5 class="card-title">Amici aggiunti di ricente</h5>
							<ul class="friend-list">
								<%
									if(f!=null&&f.size()>0){
										Iterator<?> it=f.iterator();
										while(it.hasNext()){
											FriendsBean bean=(FriendsBean)it.next();
											
								%>
								<li>
									<div class="left">
										<img src="PrintImage?username=<%=bean.getUsername1() %>" alt="ciao">
									</div>
									<div class="right">
										<a style="color:black"  href="<%=visitUserLink%>?friendname=<%=bean.getUsername1() %>" ><h3><%=bean.getUsername1() %></h3></a>
										<p><%=friends.getNumerFriends(bean.getUsername1()) %> Friends</p>
									</div>
								</li>
								
								<%
										}
									}
								%>
							</ul>
						</div>
					</div>
					<br>
				</div>
			</div>

			<div class="col-md-8">
				<div class="card social-timeline-card">

					<div class="card-body">
						<div class="tab-content" id="myTabContent">
							<div class="tab-pane fade active show" id="posts" role="tabpanel"
								aria-labelledby="posts-tab">
								<div class="form-group">
									<form method="post" action=<%="FileUploadServlet;jsessionid="+session.getId()%>
										enctype="multipart/form-data">
										<h4>Condivisione materiale</h4>
										<div class="mb-3">
											<label>File PDF</label> <input class="form-control" required
												type="file" id="formFile" name="Contenuto"
												accept=".pdf,.doc,.docx,.odt,.ppt,.pptx"> <br>
											<label>Immagine anteprima del materiale</label> <input
												class="form-control" type="file" id="formFile" required
												name="Anteprima" accept=".jpeg,.png,.jpg,.PNG">
										</div>

										<br>

										<textarea
											placeholder="Inserisci una descrizione del materiale"
											rows="3" class="form-control" name="Descrizione" required></textarea>

										<br>
										<textarea placeholder="Inserisci il corso" rows="1"
											class="form-control" name="Corso" required></textarea>
										<br>

										<div class="btn-toolbar justify-content-between">
											<div class="btn-group">
												<button type="submit"
													class="btn bottone-principale text-light"
													style="background-color: #9697e7" onclick="prova()">Share</button>
											</div>
										</div>
									</form>

								</div>
							</div>

						</div>

					</div>
				</div>
				<%
				if(materials!=null&&materials.size()>0){
					Iterator<?> it=materials.iterator();
					int i=0;
					while(it.hasNext()&&i<10){
					MaterialBean mat=(MaterialBean)it.next();
					//cart.add(mat);//togliere è solo per provare
					FriendsModelDS friend=new FriendsModelDS(ds);
					if(friend.isFriend(mat.getUsername(), username)){
						UserBean us=user.doRetrieveByUsername(mat.getUsername());
						Date dataAttuale = new Date(System.currentTimeMillis());
						Date dataCaricamento=mat.getDataCaricamento();
						long diffInMillies=dataAttuale.getTime()-dataCaricamento.getTime();
						long diff=TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
						i++;
				%>
				<div class="card social-timeline-card">
					<div class="card-header">
						<div class="d-flex justify-content-between align-items-center">
							<div class="d-flex justify-content-between align-items-center">
								<div class="mr-2">
									<img class="rounded-circle"src="PrintImage?username=<%=mat.getUsername() %>" alt="ciao" width="45">
								</div>
								<div class="ml-2">
									<div class="h5 m-0 text-blue"><%=mat.getUsername() %></div>
									<div class="h7 text-muted"><%=us.getNome() %> <%=us.getCognome() %></div>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<div class="text-muted h7 mb-2">
							<i class="fa fa-clock-o"></i><%=diff %> days ago
						</div>
						
							<%   documentPreviewLink =response.encodeURL("documentPreview.jsp?codice="+mat.getCodiceMateriale()); 
						      
						%>
						
					<a href="<%=documentPreviewLink%>">	<h5 class="card-title"><%=mat.getDescrizione() %></h5></a> <img
							src="PrintAnteprima?codice=<%=mat.getCodiceMateriale() %>" height="500px" width="500px"
							class="img-fluid">
					</div>
					<div class="card-footer">
							<a href="<%=addCartLink%>?codice=<%=mat.getCodiceMateriale() %>&url=homepage_user.jsp" style="color:#9697e7">Aggiungi al carello <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-plus-fill" viewBox="0 0 16 16">
  <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM9 5.5V7h1.5a.5.5 0 0 1 0 1H9v1.5a.5.5 0 0 1-1 0V8H6.5a.5.5 0 0 1 0-1H8V5.5a.5.5 0 0 1 1 0z"/>
</svg></a>
						
					</div>
				</div>
				<%}}} %>
				<br>

			</div>


		</div>


	</main>

	<%@ include file="footer.jsp"%>
</body>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>


<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script src="js/homepage_user.js"></script>



<script src="package/dist/sweetalert2.all.min.js"></script>
<script src="package/dist/sweetalert2.min.js"></script>
</html>