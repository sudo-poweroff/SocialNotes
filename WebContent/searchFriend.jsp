<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, it.unisa.model.*,javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SocialNotes - Cerca Persone</title>

<link rel="icon" href="img/favicon.ico">   

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
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
<link rel="stylesheet" type="text/css" href="css/friendResearch.css">
</head>

	   
<body>

<% 
String visitUserLink="visitUser.jsp";
	  
   if(session.getAttribute("username")==null){
	  
	  String homeGuest = "homepage.jsp";
	  String encodedURL = response.encodeRedirectURL(homeGuest);
	  response.sendRedirect(encodedURL);

	  
   }else{
	   visitUserLink=response.encodeURL(visitUserLink);
	   %>
	   <jsp:include page="header_user.jsp"></jsp:include>
	   <% }%>
	   
	   <div class="container">
<br><br>
<div class="row">
<div class="col-md-2"></div>
<div class="col-md-8">
	   
	   <form class="form shadow p-3 mb-5 bg-white rounded" action=<%="SearchFriend;jsessionid="+session.getId() %>>
	   
	  
<div class="form-row">

  <div class="form-group col-md-4">
  <label for="email">Cerca amico</label>
<input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="search">
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
<button type="reset" class="btn btn-default" style="background-color:#9697e7; color:white">Reset</button>
</form>
</div>
<div class="col-md-2"></div>


<%
DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");

 int feed=0;
 int c=0;

  Collection <UserModelDS> collection = (Collection<UserModelDS>) request.getAttribute("utente");
  UserModelDS material = new UserModelDS(ds);
if(collection!=null&&collection.size()>0){
	Iterator<?> it=collection.iterator();%>
	<div class="table-responsive">
    	<table class="table widget-26">
        <tbody>
   <%
	while(it.hasNext()){
		UserBean bean=(UserBean)it.next();
		FeedbackModelDS fmodel = new FeedbackModelDS(ds);
		feed = fmodel.getFeedbackByUsername(bean.getUsername());
		%>
		<tr>
	        <td>
	            <div class="widget-26-job-emp-img">
	                <img src="PrintImage?username=<%=bean.getUsername() %>" alt="ciao" width="150px"/>
	            </div>
	        </td>
	        <td>
	            <div class="widget-26-job-title">
	                <a style="color:black"  href="<%=visitUserLink%>?friendname=<%=bean.getUsername()%>" ><%=bean.getNome() %> <%=bean.getCognome() %></a>
	               
	            </div>
	        </td>
	        <td>
	            <div class="widget-26-job-info">
	                <p class="type m-0"><%=bean.getUsername() %></p>
	            </div>
	        </td>
	        <td>
	            <div class="widget-26-job-salary"><%=bean.getDenominazione() %></div>
	        </td>
	        <td>
	            <div class="widget-26-job-category bg-soft-base">
	              <i class="indicator bg-info"></i>
	                <span><%=bean.getDipName() %></span>
	            </div>
	        </td>
	        <td>
	            <div class="widget-26-job-starred">
	             								<canvas class="myCanvas" data-rating="<%=feed %>" width="100"
									height="20">
not support the canvas tag.</canvas>
	            </div>
	        </td>
	    </tr>
		
		<% 
		
	}
}
%>
 </tbody>
</table>
</div>
</div>

</div>

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<%@ include file="footer.jsp" %>
</body>

<script src="js/search.js" type="text/javascript"></script>
<script src="js/homepage_user.js"></script>
</html>