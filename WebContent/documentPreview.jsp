<%@page import="it.unisa.model.CourseBean"%>
<%@page import="it.unisa.model.CourseModelDS"%>
<%@page import="it.unisa.model.MaterialBean"%>
<%@page import="it.unisa.model.FeedbackBean"%>
<%@page import="it.unisa.model.UserBean"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.util.Collection"%>
<%@page import="it.unisa.model.MaterialModelDS"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.unisa.model.FeedbackModelDS"%>
<%@page import="it.unisa.model.UserModelDS"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" href="img/favicon.ico">
<title>Anteprima Materiale</title>
<link rel="stylesheet" type="text/css" href="css/documentPreview.css">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/friendResearch.css">        

<style>
.star-rating {
  display: flex;
  align-items: center;
  width: 160px;
  flex-direction: row-reverse;
  justify-content: space-between;
  position: relative;
}
/* hide the inputs */
.star-rating input {
  display: none;
}
/* set properties of all labels */
.star-rating > label {
  width: 30px;
  height: 30px;
  font-family: Arial;
  font-size: 30px;
  transition: 0.2s ease;
  color: orange;
}
/* give label a hover state */
.star-rating label:hover {
  color: #9697e7;
  transition: 0.2s ease;
}
.star-rating label:active::before {
  transform:scale(1.1);
}

/* set shape of unselected label */
.star-rating label::before {
  content: '\2606';
  position: absolute;
  top: 0px;
  line-height: 26px;
}
/* set full star shape for checked label and those that come after it */
.star-rating input:checked ~ label:before {
  content:'\2605';
}

@-moz-document url-prefix() {
  .star-rating input:checked ~ label:before {
  font-size: 36px;
  line-height: 21px;
  }
}  </style>

</head>
<body>
<%


	DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
    String code ="" ;
    if(request.getParameter("codice")!=null)
	code=request.getParameter("codice");
    else
    	code = String.valueOf((int)request.getAttribute("codice"));
 
    
	int codiceMateriale=Integer.parseInt(code);
	MaterialModelDS materialModel=new MaterialModelDS(ds);
	MaterialBean material=materialModel.doRetrieveByKey(code);
	int feed=materialModel.doRetrieveFeedback(codiceMateriale);
	UserModelDS userModel=new UserModelDS(ds);
	UserBean user=userModel.doRetrieveByUsername(material.getUsername());
	CourseModelDS courseModel=new CourseModelDS(ds);
	String codiceCorso=""+material.getCodiceCorso();
	CourseBean course=courseModel.doRetrieveByKey(codiceCorso);
	
	String addCartLink ="AddToCart";
	String addFeedback = "AddFeedback";
	if(session.getAttribute("username")!=null){
		addFeedback = response.encodeURL(addFeedback);
		addCartLink = response.encodeURL(addCartLink);
%>
<jsp:include page="header_user.jsp"></jsp:include>
<%}else{ %>

<jsp:include page="header.jsp"></jsp:include>
<% 
} %>
<br>
<br>
<div class="container">
    <div class="shop-default shop-cards shop-tech">
        <div class="row">
        
            <div class="col-md-6">
                <div class="block product no-border z-depth-2-top z-depth-2--hover">
                    <div class="block-image">
                     
                            <img src="PrintAnteprima?codice=<%=material.getCodiceMateriale() %>" class="img-center">
                        
                    </div>
                    <div class="block-body text-center">
                        <h3 class="heading heading-5 strong-600 text-capitalize"><%=material.getFileName() %></h3>
                  	    <div class="widget-26-job-starred">
	             			<canvas class="myCanvas" data-rating="<%=feed %>" width="100" height="20">not support the canvas tag.</canvas>
	            		</div>
                        
                        <div class="product-buttons mt-4">
                            <div class="row align-items-center">
                               
                               <% if (session.getAttribute("username")!=null) { %>
                                <div class="col-8">
                                    <button type="button" class="btn btn-block btn-primary btn-circle btn-icon-left" id="scarica">
                                        <a href="<%=addCartLink %>?codice=<%=material.getCodiceMateriale() %>&url=documentPreview.jsp" style="color:white">Aggiungi al carello <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-plus-fill" viewBox="0 0 16 16">
  <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM9 5.5V7h1.5a.5.5 0 0 1 0 1H9v1.5a.5.5 0 0 1-1 0V8H6.5a.5.5 0 0 1 0-1H8V5.5a.5.5 0 0 1 1 0z"/>
</svg></a>
                                    </button>
                                </div>
                                <%} %>
                            </div>
                        </div>
                    </div>
                </div>
    
            </div>
            <div class="col-md-6">
                <div class="block product no-border z-depth-2-top z-depth-2--hover">
                    <div class="block-image">
                       
                            <div class="product-content product-wrap clearfix">
					
				

				<div class="product-deatil">
				</div> 
				<div class="description"><br><br>
				<h4 align=center><b><%=material.getDescrizione() %></b></h4>
				</div>
				<div class="project-info-box">
                <h5>&nbsp;<b>Caricato da: </b><%=user.getNome()%> <%=user.getCognome() %></h5>
                <h5>&nbsp;<b>Data: </b><%=material.getDataCaricamento() %></h5>
                <h5>&nbsp;<b>Corso: </b><%=course.getNome() %></h5>
                <h5>&nbsp;<b>Universita': </b><%=user.getDenominazione() %></h5>
                <h5>&nbsp;<b>Coin: </b><%=material.getCosto() %></h5>
	            </div>
	           <br>
            </div>
            
			</div>
			<br><br>
			
		
	</div>
	<%
	FeedbackModelDS fModel = new FeedbackModelDS(ds);
	Collection<FeedbackBean> feeds= fModel.doRetrieveByKeyMaterial(String.valueOf(material.getCodiceMateriale()));
	Iterator<?> it=feeds.iterator();

	while(it.hasNext()){
	FeedbackBean fbean = (FeedbackBean)it.next();
	System.out.println("Feed: "+fbean.getValutazione());
	%>
	<div class="card">
  <div class="card-header">
    <div class="h6 text-muted">Valutazione</div>
<canvas class="myCanvas" data-rating="<%=fbean.getValutazione() %>" width="100" height="20">not support the canvas tag.</canvas>
  </div>
  <div class="card-body">
    <blockquote class="blockquote mb-0">
      <p><%=fbean.getCommento() %></p>
      <footer class="blockquote-footer"><%=fbean.getUsername() %>  DATA: <%=fbean.getDataFeed().getYear()+1900 %>-<%=fbean.getDataFeed().getMonth()+1 %>-<%=fbean.getDataFeed().getDate() %> <%=fbean.getDataFeed().getHours() %>:<%=fbean.getDataFeed().getMinutes() %> </footer>
    </blockquote>
  </div>
</div>
<br>
<%} %>
	<%
	   if(session.getAttribute("username")!=null){
	if ((((String)session.getAttribute("username")).compareTo(material.getUsername()))!=0)
	{
		%>
	<div class="card">
	 <div class="card-header">
    <div class="h6 text-muted">Effettua una recensione</div>
  </div>
  <form method="POST" action="<%=addFeedback %>">
  Feedback:
  <div class="form-group">
  <div class="star-rating" id="stella">
      <input type="radio" name="stars" id="star-a" value="5"/>
      <label for="star-a"></label>

      <input type="radio" name="stars" id="star-b" value="4"/>
      <label for="star-b"></label>
  
      <input type="radio" name="stars" id="star-c" value="3"/>
      <label for="star-c"></label>
  
      <input type="radio" name="stars" id="star-d" value="2"/>
      <label for="star-d"></label>
  
      <input type="radio" name="stars" id="star-e" value="1"/>
      <label for="star-e"></label>
</div>
  
  
    <label for="exampleFormControlTextarea1">Recensione:</label>
    <textarea class="form-control" id="exampleFormControlTextarea1" name="commento" rows="3"></textarea>
    <input type="search" name="username" hidden value="<%=session.getAttribute("username")%>">
    <input type="search" name="codmateriale" hidden value="<%=material.getCodiceMateriale()%>">
  </div>
  <button type="submit" class="btn btn-primary mb-2" style="background-color:#9697e7">Invia</button>
</form>
  <%}} %>
  
</div>
	
                        
                    </div>
  
                
                </div>
                  
            </div>
        </div>

            
      

<br><br><br><br><br>
<%@include file="footer.jsp" %>

</body>
<script src="js/homepage_user.js"></script>
</html>