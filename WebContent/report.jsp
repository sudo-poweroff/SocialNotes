<%@page import="materiale.*"%>
<%@page import="profilo.*"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" href="img/favicon.ico">
<title>Gestione Materiale</title>
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
	
	String linkSegnalazione = "ReportMaterial";
	if((session.getAttribute("username")!=null)&&(((int)session.getAttribute("role"))==2)){
		linkSegnalazione = response.encodeURL(linkSegnalazione);
%>
<jsp:include page="headerUsersNotesManager.jsp"></jsp:include>
<%}else{ 

  response.sendRedirect(response.encodeUrl("homepage.jsp"));

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
                        <h3 class="heading heading-5 strong-600 text-capitalize"><%=material.getNomeFile() %></h3>
                  	    <div class="widget-26-job-starred">
	             			<canvas class="myCanvas" data-rating="<%=feed %>" width="100" height="20">not support the canvas tag.</canvas>
	            		</div>
                        

                    </div>
                </div>
                <br><br>
         	<%
	if((session.getAttribute("username")!=null)&&(((int)session.getAttribute("role"))==2)){

		%>
	<div class="card">
	 <div class="card-header">
    <div class="h6 text-muted">Motivo segnalazione</div>
  </div>
  <form method="POST" action="<%=linkSegnalazione %>">
  <div class="form-group">

  
  
    <label for="exampleFormControlTextarea1">motivo:</label>
    <textarea class="form-control" id="exampleFormControlTextarea1" name="segnalazione" rows="3" required ></textarea>
    <input type="search" name="username" hidden value="<%=material.getUsername()%>">
    <input type="search" name="codmateriale" hidden value="<%=material.getCodiceMateriale()%>">
  </div>
  <button type="submit" class="btn btn-primary mb-2" style="background-color:#9697e7">Invia Segnalazione</button>
</form>
</div>
  <%} %>
  

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
	            </div>
	           <br>
            </div>
            
			</div>
			<br><br>
			
		
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