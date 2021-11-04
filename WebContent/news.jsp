<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, it.unisa.model.*,javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SocialNotes - News</title>
<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

<style>
.paginate_button:hover{
	background:none !important;
	border:none !important;
}
.paginate_button {
	padding: .5em .2em !important;
}

</style>

<style>


.btn-principale{
	background-color: #9697e7 !important;
}

@media screen and (max-device-width:600px){
	.img-resp{
		width:225px;
		height:182px;
	}
}
@media screen and (min-device-width:601px){
	.img-resp{
		width:300px;
		height:182px;
	}
	.card-horizontal {
  		display: flex;
  		flex: 1 1 auto;
	}
}
</style>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.0/datatables.min.css"/>
 
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.11.0/datatables.min.js"></script>
<script>

	
$(document).ready(function() {
	$.noConflict();
    $("#table").DataTable({
    		"sPaginationType": "full_numbers",
    		"info":true,
    		//"ordering":false,
    		//"searching":false,
    		"pageLength":6,
    		"lengthChange": false,
    	    language: {
    	        "emptyTable": "La ricerca non ha prodotto risultati",
    	        "infoEmpty": "Nessun risultato disponibile",
    	        "zeroRecords": "La ricerca non ha prodotto risultati",
	              "paginate": {
	            	  'first':    '<<',
	            	  'last':     '>>',
	                  'previous': 'Precedente',
	                  'next':     'Successivo',
	              },
	              "info": "Pagina _PAGE_ di _PAGES_",
	              "search": "Cerca:",
	  	          "aria": {
		          }
    	      },

    });
} );

</script>

</head>
<body>

 <%    
 
 String adminURL = "admin.jsp";
 String showNewsUrl = "showNews.jsp";
 
   if(session.getAttribute("username")==null){
	  System.out.println("L'utente non è loggato");
	  %>
	  <jsp:include page="header.jsp"></jsp:include>
	  <% 
  }else{
	  if (((int)session.getAttribute("role"))==1){
		  adminURL = response.encodeURL(adminURL);
		  response.sendRedirect(adminURL);
		  return;
		  
	  }
                
	  synchronized(session) {
	  System.out.println("ID SESSIONE NEWS:"+session.getId());
	  Date dataSessione = new Date(session.getCreationTime());
	  System.out.println("Data creazione "+ dataSessione);
	   
	  showNewsUrl = response.encodeURL(showNewsUrl);
	  %>
	  <jsp:include page="header_user.jsp"></jsp:include>
	 <%  
	  }
  } %>



 <!-- DA FARE IMMAGINI -->



<div class="container">
 <h2 class="pb-2 border-bottom" style="margin-top:15px; font-family: 'Archivo', sans-serif;">News</h2>
   
<%
	DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
	NewsModelDS model= new NewsModelDS(ds);
	ContentModelDS cmodel = new ContentModelDS(ds);
	FileModelDS fmodel = new FileModelDS(ds);
  	Collection <NewsBean> collection = model.doRetrieveAll();
if(collection!=null&&collection.size()>0){
	Iterator<?> it=collection.iterator();
	%>
	<table id="table">
	<thead>
		<tr>
		    <th>Ordina per data caricamento</th>
    	</tr>
    </thead>
    <tbody>
	 <%
	while(it.hasNext()){
		NewsBean nbean=(NewsBean)it.next();
		Collection<String> fileNames = cmodel.doRetrieveByCodiceNews(nbean.getCodiceNews());
		String fileName = null; //Per passarmela dopo nella showNews.jsp
		%>
		
		  <tr>
		   <td>
		  	  <div class="col" style="margin-bottom:3%">
				  <div class="card shadow-sm">
				  			 <div class="card-header">
								<small class="text-muted"><%=nbean.getDataCaricamento()+"  "%>-<%=nbean.getUsername()%></small>
							</div>
							
					    <div class="card-horizontal">
					        <div class="img-square-wrapper">
					        	<% if(fileNames!=null&&fileNames.size()>0){
					        		Iterator<?> itfiles = fileNames.iterator();
					        		if(itfiles.hasNext()){
					        			fileName = (String)itfiles.next();	
					        			FileBean fb = fmodel.doRetrieveByKey(fileName);
					        			if(fb!=null){
					        				%>
					        				<img class="img-resp" src="PrintNewsImage?filename=<%=fb.getFilename()%>"   alt="News">
					        			<%
					        			}
					        		}
					        	}else{
					        			%>
					        	
					        		<img class="img-resp" src="img/logo.png"  alt="News">
					        		<%
					        		}
					        	%>
					        </div>
					        <div class="card-body">
					            <h4 class="card-title"><%=nbean.getTitolo() %></h4>
					            <p class="card-text"><%=nbean.getArgomento() %></p>
					            <br>
					            <a href="<%=showNewsUrl %><%="?newsId="+nbean.getCodiceNews()+"&newsImage="+fileName%>" class="btn btn-principale stretched-link">Vai alla notizia</a>
					            <!-- stretched link rende tutta la card un link -->
					        </div>
					    </div>
					     
						<!--  <div class="card-footer">
							<small class="text-muted"><%=nbean.getDataCaricamento()+" "%>-<%=nbean.getUsername()%></small>
						</div>
						-->
						
				    </div>
				</div>
				</td>
            
  		</tr>
		
	
	 <%	
		}
	 %> 
	  </tbody>
	 </table>
	 <% 
}
%>


</div>






<%@ include file="footer.jsp" %>

</body>


<script src="js/search.js" type="text/javascript"></script>

</html>