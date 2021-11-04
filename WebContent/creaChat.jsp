<%@page import="it.unisa.model.FriendsModelDS"%>
<%@page import="it.unisa.model.UserModelDS"%>
<%@page import="it.unisa.model.FriendsBean"%>
<%@page import="it.unisa.model.UserBean"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="javax.sql.DataSource"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" href="img/favicon.ico">
<title>Crea chat - SocialNotes</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/editUser.css">
</head>
<body>
<%
String adminURL = "admin.jsp";
String ChatCreateServlet = "ChatCreateServlet";
   if(session.getAttribute("username")==null){
	 
		  response.sendRedirect("login.jsp");
	     //return;
	   %>
	  
	
	  
	  <%
   }else{
	   if (((int)session.getAttribute("role"))==1){
			  adminURL = response.encodeURL(adminURL);
			  response.sendRedirect(adminURL);
			  return;
		  }
	     ChatCreateServlet = response.encodeURL(ChatCreateServlet);
	   
	   %>
	   <jsp:include page="header_user.jsp"></jsp:include>
	   <% }

%>

  	<%
	String errore = (String) request.getAttribute("error");
	if (errore != null) {
	%>

	<div class="alert alert-danger alert-dismissible fade show" role="alert">
		<strong>Attenzione!</strong> Errore nella creazione della chat.
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

	<%
	}
	%>

<br><br><br>
<div class="container">
<div class="row">
<div class="col-md-2"></div>
<br><br><br>

<%
String username=(String)session.getAttribute("username");
DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
FriendsModelDS friends=new FriendsModelDS(ds);
UserModelDS uModel = new UserModelDS(ds);
Collection<FriendsBean> f=friends.doRetrieveByUsername(username);


%>
<div class="col-md-8">
<form method="POST" action="<%=ChatCreateServlet%>">
  <div class="form-group">
      <label for="exampleFormControlTextarea1">Titolo</label>
    <textarea class="form-control" name="titolo" id="exampleFormControlTextarea1" placeholder="Inserisci il titolo della chat che vuoi creare" rows="3" required></textarea>
  </div>
  <label for="checkAmici">Scegli gli amici con cui vuoi chattare</label>
  <div class="form-group" id="checkAmici">
          <div class="user-dashboard-info-box table-responsive mb-0 bg-white p-4 shadow-sm">
          <table class="table manage-candidates-top mb-0">
            <thead>
              <tr>
                <th>UTENTI</th>
                <th class="text-center">Nome</th>
                <th class="action text-right">Cognome</th>
              </tr>
            </thead>
            <tbody>
  <%
									if(f!=null&&f.size()>0){
										Iterator<?> it=f.iterator();
										while(it.hasNext()){
											FriendsBean bean=(FriendsBean)it.next();
											UserBean userBean = uModel.doRetrieveByUsername(bean.getUsername1());
								%>





              <tr class="candidates-list">
               
                <td class="title">
                <input class="form-check-input" type="checkbox" id="inlineCheckbox1" name ="amici" value="<%=bean.getUsername1()%>">
                <label class="form-check-label" for="inlineCheckbox1">
                  <div class="thumb">
                    <img class="img-fluid" src="PrintImage?username=<%=bean.getUsername1() %>" alt="haloo">
                  </div>
                  <div class="candidate-list-details">
                    <div class="candidate-list-info">
                      <div class="candidate-list-title">
                        <h5 class="mb-0"><a href="visitUser.jsp?friendname=<%=bean.getUsername1()%>"><%=bean.getUsername1() %></a></h5>
                      </div>
                      
                    </div>
                  </div>
                  </label>
                </td>
                <td class="candidate-list-favourite-time text-center">
                <span><%=userBean.getNome()%></span>
               </td>
              <td class="candidate-list-favourite-time text-center">
                <span><%=userBean.getCognome()%></spanp>
               </td>

              
              </tr>
              
   

<%} } %>
         </tbody>
          </table>
        </div>
  </div>
     <div class="col-auto">
      <button type="submit" class="btn btn-primary mb-2" style="background-color:#9697e7;">Crea chat</button>
      <button type="reset" class="btn btn-primary mb-2" style="background-color:#9697e7;">Reset</button>
    </div>
</form>
</div>
<div class="col-md-2"></div>
</div>
</div>

</body>

<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
</html>