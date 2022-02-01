<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="javax.sql.DataSource"%>
<%@page import="profilo.*"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SocialNotes-Segnalazioni Archiviate</title>
<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/segnalazione.css" />
</head>
<body>
<%	if((session.getAttribute("username")!=null)&&(((int)session.getAttribute("role"))==1)){

%>
<jsp:include page="headerUsersNotesManager.jsp"></jsp:include>
<%}else{ 

response.sendRedirect(response.encodeUrl("homepage.jsp"));

} %>
<br>
<h2><b>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;SEGNALAZIONI ARCHIVIATE</b></h2>

<div class="page-content page-container" id="page-content">
    <div class="padding">
        <div class="row">
            <div class="col-sm-8">
                <div class="container-fluid d-flex justify-content-center">
                    <div class="list list-row card" id="sortable" data-sortable-id="0" aria-dropeffect="move">
                    <%
	                    DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
	                	ReportModelDS report=new ReportModelDS(ds);
	                	Collection<ReportBean> reports=report.doRetrieveArchived();
	                	if(reports!=null&&reports.size()>0){
	                		Iterator<?> it=reports.iterator();
	                		while(it.hasNext()){
	                			ReportBean rep=(ReportBean)it.next();
                    
                    %>
                    	
                        <div class="list-item" data-id="13" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false" style="">
                             <div><a  data-abc="true"><span class="w-40 avatar gd-danger"><img src="PrintImage?username=<%=rep.getUsername() %>" alt="haloo"></span></a></div>
                            <div class="flex"> <a  class="item-author text-color" data-abc="true"><%=rep.getUsername() %></a>
                                <div class="item-except text-muted text-sm h-1x"><%=rep.getMotivo() %>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</div>
                            </div>
                            
                            
                        </div>
                        
                        <%}} %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<%@ include file="footer.jsp"%>
</body>
</html>