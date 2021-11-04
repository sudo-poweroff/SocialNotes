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

                        

.card{
    -moz-border-radius: 2%;
    -webkit-border-radius: 2%;
    border-radius: 2%;
    box-shadow: 5px 5px 0 rgba(0,0,0,0.08);
    margin-top:20px;
    margin-bottom:25%;
}

.profile .profile-body {
    padding: 20px;
    background: #f7f7f7;
   
}


.profile .profile-bio {
    background: #fff;
    position: relative;
    padding: 15px 10px 5px 15px;
}

.profile .profile-bio a {
    left: 50%;
    bottom: 20px;
    margin: -62px;
    text-align: center;
    position: absolute;
}

.profile .profile-bio h2 {
    margin-top: 0;
    font-weight: 200;
}

h1, h2, h3, h4, h5, h6 {
    margin-top: 5px;
    text-shadow: none;
    font-weight: normal;
    font-family: 'Open Sans', sans-serif;
}
h2 {
    font-size: 24px;
    line-height: 33px;
}

p, li, li a {
    color: #555;
}

.bottone-principale{
	color: #fff;
	background-color: #9697e7;
	border-color: #9697e7;
	margin-top:20px;
}                                                            
</style>
</head>
<body>

 <%    
   if(session.getAttribute("username")==null){
	  System.out.println("L'utente non è loggato");
	  %>
	  <jsp:include page="header.jsp"></jsp:include>
	  <% 
  }else{
	  synchronized(session) {
	 
	  %>
	  <jsp:include page="header_user.jsp"></jsp:include>
	 <%  
	  }
  } %>
  
  <%
  DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
  NewsModelDS model= new NewsModelDS(ds);
  FileModelDS fmodel = new FileModelDS(ds);
  String idNews = (String)request.getParameter("newsId");
  String imageNews = (String)request.getParameter("newsImage");
  if(idNews==null||imageNews==null) {
	String link = "news.jsp";
	String encodedURL = response.encodeRedirectURL(link);
	response.sendRedirect(encodedURL);
  }
  int id = Integer.parseInt(idNews);
  NewsBean nbean = model.doRetrieveByCodiceNews(id);
  FileBean fb = fmodel.doRetrieveByKey(imageNews);
  %>
                        
	 <div class="container">
	 		<div class="btn-toolbar justify-content-between">
			<div class="btn-group">
				<button class="btn bottone-principale text-light" style="background-color: #9697e7" onclick="history.back()">Torna indietro</button>
			</div>
		</div>
		
	    <div class="profile card">
	        <div class="profile-body">
	            <div class="profile-bio">
	                <div class="row">
	                    <div class="col-md-5 text-center">
	                    <%
	                    		if(fb!=null){
					      %>
					        		<img class="img-thumbnail md-margin-bottom-10" src="PrintNewsImage?filename=<%=fb.getFilename()%>"  width="300" height="182" alt="SocialNotes News">
					        			<%
					
					        	}else{
					        			%>
					        		<img class="img-thumbnail md-margin-bottom-10" src="img/logo.png" alt="SocialNotes News">
					        		<%
					        		}
	                   				 %>
	                    
	                        
	                    </div>
	                    <div class="col-md-7">
	                        <h2 style="color: #585f69;"><%=nbean.getTitolo() %></h2>
	                        <span><strong><%=nbean.getArgomento() %></strong></span>
	                        <hr>
	                        <p><%=nbean.getContenuto()%></p>
	                        <div class="text-right">
	                        	<small>-<%=nbean.getUsername()%></small>
	                        </div>
	                    </div>
	                </div>    
	            </div>
	    	</div>
	    </div>

	</div>                                                            

<%@ include file="footer.jsp" %>
</body>
</html>