<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="profilo.*"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SocialNotes-Utenti Bannati</title>
<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css" integrity="sha256-2XFplPlrFClt0bIdPgpz8H7ojnk10H69xRqd9+uTShA=" crossorigin="anonymous" />
<link rel="stylesheet" type="text/css" href="css/userBanned.css" />

</head>
<body>
 <%@include file="headerUsersNotesManager.jsp" %>
<br>
<div class="container mt-3 mb-4">
<div class="col-lg-9 mt-4 mt-lg-0">
    <div class="row">
      <div class="col-md-12">
        <div class="user-dashboard-info-box table-responsive mb-0 bg-white p-4 shadow-sm">
          <table class="table manage-candidates-top mb-0">
            <thead>
              <tr>
                <th>Utente</th>
                <th class="text-center">Durata Ban</th>
                <th class="action text-right">Azione</th>
                 <th class="action text-right">Stato</th>
              </tr>
            </thead>
            <tbody>
            <%
            	String visitUserLink=response.encodeURL("visitUserAdmin.jsp");
	            String setBanLink=response.encodeURL("SetBan");
	        	String removeBanLink=response.encodeURL("RemoveBan");
	            DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
	        	UserModelDS model=new UserModelDS(ds);
	        	Collection<UserBean> users=model.doRetrieveUsers();
	            if(users!=null&&users.size()>0){
					Iterator<?> it=users.iterator();
					while(it.hasNext()){
						UserBean bean=(UserBean)it.next();
						String ban;
						Date dataAttuale=new Date(System.currentTimeMillis());
						if(bean.getBan()!=null&&bean.getBan().after(dataAttuale))
							ban="Bloccato";
						else
							ban="Sbloccato";
            %>
              <tr class="candidates-list">
                <td class="title">
                  <div class="thumb">
                    <img class="img-fluid" src="PrintImage?username=<%=bean.getUsername() %>" alt="haloo">
                  </div>
                  <div class="candidate-list-details">
                    <div class="candidate-list-info">
                      <div class="candidate-list-title">
                        <h5 class="mb-0"><a href="<%=visitUserLink %>?friendname=<%=bean.getUsername()%>"><%=bean.getUsername() %> - <%=bean.getNome() %> <%=bean.getCognome() %></a></h5>
                      </div>
                      <div class="candidate-list-option">
                        
                      </div>
                    </div>
                  </div>
                </td>
                  
                <td class="candidate-list-favourite-time text-center">
                <form method="post" action="<%=setBanLink %>?username=<%=bean.getUsername() %>">
                 <a class="candidate-list-favourite order-2 text-danger" ><i class="fas fa-user-clock"></i></a>
                 
                  <input type="date" name="durataBan" placeholder="GG/MM/AAAA" value="" maxlength="10" required>
                    <button style="margin-left:30px" type="submit"><i class="fas fa-lock"></i></button>             
                     </form>
                    </td>
                    <td> 
                    <a href="<%=removeBanLink %>?username=<%=bean.getUsername() %>" class="text-info" data-toggle="tooltip" title="" data-original-title="unban"><i class="fas fa-lock-open"></i></a>
                 
                  
                </td>
                  <td class="candidate-list-favourite-time text-center">
                 
                  <span class="candidate-list-time order-1"><%=ban %></span>
                </td>
               
              </tr>
              
              <%}} %>
            </tbody>
          </table>
          <div class="text-center mt-3 mt-sm-3">
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>