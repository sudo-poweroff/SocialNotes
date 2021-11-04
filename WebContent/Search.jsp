<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, it.unisa.model.*,javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cerca Materiale - SocialNotes</title>
<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

<style>
/* use reverse flexbox to take advantage of flex-direction: reverse */
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
}  
</style>

</head>
<body>
<% if(session.getAttribute("username")!=null){
	if ((int)session.getAttribute("role")==1){
		
%>
<jsp:include page="header_admin.jsp"></jsp:include>
<%}else{ %>
<jsp:include page="header_user.jsp"></jsp:include>
<% 
}
}else{
	%>
	<jsp:include page="header.jsp"></jsp:include>
	<% 
}
	
	%>


<div class="container">
<br><br>
<div class="row">
<div class="col-md-2"></div>
<div class="col-md-8">
<%
DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");

  String stringRicerca = (String)request.getAttribute("ricercaNew");
  System.out.println("STAMPA:" +stringRicerca);
%>
<form class="form shadow p-3 mb-5 bg-white rounded" action=<%="SearchServlet;jsessionid="+session.getId() %>>
<input type="search" id="ricerca" name="ricerca" hidden value="<%=stringRicerca%>">
<div class="form-row">
  <div class="form-group col-md-4">
    <label for="email">Ordine di caricamento</label>
     <select class="custom-select d-block w-100" id="date" name="date">
       <option value="novalue"> - </option>
     <option value="ASC">Crescente</option>
     <option value="DESC">Decrescente</option>
     </select>
  </div>
<br>
  <div class="form-group col-md-4">
    <label for="email">Ordine in base al Rating:</label>
     <select class="custom-select d-block w-100" id="ratingOrder" name="ratingOrder">
     <option value="novalue"> - </option>
     <option value="ASC">Crescente</option>
     <option value="DESC">Decrescente</option>
     </select>
  </div>
<br>
       
     <div class="form-group col-md-4" >
   <label for="stella">Rating</label>
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
  </div>
</div>
<button type="submit" class="btn btn-default" style="background-color:#9697e7; color:white">Cerca</button>
</form>

</div>
<div class="col-md-2"></div>


<%
  Collection <MaterialModelDS> collection = (Collection<MaterialModelDS>) request.getAttribute("materiale");
  MaterialModelDS material = new MaterialModelDS(ds);
  CourseModelDS cModel = new CourseModelDS(ds);
  
if(collection!=null&&collection.size()>0){
	Iterator<?> it=collection.iterator();

	while(it.hasNext()){
		MaterialBean mbean=(MaterialBean)it.next();
		
		String keyCourse = String.valueOf(mbean.getCodiceCorso());
		//System.out.println("CODICE CORSO : "+keyCourse);
		CourseBean cBean = cModel.doRetrieveByKey(keyCourse);
	    
		
		int feedback = material.doRetrieveFeedback(mbean.getCodiceMateriale());
		%>


           
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
          
            <div class="well search-result">
                <div class="row">
                        <div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
                            <img class="img-responsive" src="PrintAnteprima?codice=<%=mbean.getCodiceMateriale() %>" width="160" height="160" alt="hello">
                        </div>
                        <div class="col-xs-6 col-sm-9 col-md-9 col-lg-10 title">
                        <% 
						String documentPreviewLink="documentPreview.jsp";
	  
   						if(session.getAttribute("username")!=null){
						  
						   documentPreviewLink=response.encodeURL(documentPreviewLink);
						   }%>
                            <a href="documentPreview.jsp;jsessionid=<%=session.getId() %>?codice=<%=mbean.getCodiceMateriale()%>"> <h4><%=mbean.getDescrizione() %></h4></a>
                            <span>Utente: <%=mbean.getUsername() %></span><br>
                            <span>Costo :<%=mbean.getCosto() %></span><br>
                            <span>Data caricamento : <%=mbean.getDataCaricamento() %></span><br>
                            <span>Feedback :<%=feedback %></span><br>
                            <span>Corso :  <%=cBean.getNome() %></span><br>
                        </div>
                    </div>
            
          
           
        </div>
         <hr class="my-4">
    </div>
        


		<% 
		
	}
	 %> 
	 
	 
	 <% 
}else{
	response.sendRedirect(response.encodeURL("errorSearch.jsp"));
}
%>

</div>


</div>




<%@ include file="footer.jsp" %>
</body>


<script src="js/search.js" type="text/javascript"></script>

</html>