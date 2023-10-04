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
<title>SocialNotes-Segnalazioni Effettuate</title>
<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/segnalazioni_eff.css" />
<link rel="stylesheet" href="css/errorSearch.css">
</head>
<body>


<% 

	if((session.getAttribute("username")!=null)&&(((int)session.getAttribute("role"))==1)){
%>
<jsp:include page="headerUsersNotesManager.jsp"></jsp:include>
<%}else{ 

  response.sendRedirect(response.encodeUrl("homepage.jsp"));

} %>
<br>
<h2><b>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;SEGNALAZIONI EFFETTUATE</b></h2>



<div class="page-content page-container" id="page-content">
    <div class="padding">
        <div class="row">
        
        

                    
                    
                    <% 
                    	DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
                    	ReportModelDS report=new ReportModelDS(ds);
                    	Collection<ReportBean> reports=report.doRetrieveNotArchived();
                    	if(reports!=null&&reports.size()>0){
                    		Iterator<?> it=reports.iterator();
                    		%>
                    		 <div class="col-sm-8">
                    	      <div class="container-fluid d-flex justify-content-center">
                              <div class="list list-row card" id="sortable" data-sortable-id="0" aria-dropeffect="move">
                    		<% 
                    		while(it.hasNext()){
                    			ReportBean rep=(ReportBean)it.next();
                    
                    %>
                               
            
          
                    
                       <!--  <div class="list-item" data-id="13" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false" style="">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-primary"> <img class="img-fluid" src="PrintImage?username=<%=rep.getUsername() %>" alt="haloo"></span></a></div>
                            <div class="flex"> <a  class="item-author text-color" data-abc="true"><%=rep.getUsername() %></a>
                                <div class="item-except text-muted text-sm h-1x"><%=rep.getMotivo() %></div>
                                <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                           
                            
                        </div>-->
                        
                         <div class="list-item" data-id="10" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false" style="">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-danger"><img src="PrintImage?username=<%=rep.getUsername() %>" alt="haloo"></span></a></div>
                            <div class="flex"> <a  class="item-author text-color" data-abc="true"><%=rep.getUsername() %></a>
                                <div class="item-except text-muted text-sm h-1x"><%=rep.getMotivo() %>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</div>
                                <a href="<%=response.encodeURL("ArchiveReport") %>?code=<%=rep.getCodiceSegnalazione() %>" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                            
                            <div>
                               
                            </div>
                        </div>
                        <% }%>
                        
                        
                        <!-- <div class="list-item" data-id="9" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-info"><img src="https://img.icons8.com/bubbles/24/000000/user.png" alt="."></span></a></div>
                            <div class="flex"> <a class="item-author text-color" data-abc="true">Steven Hmpire</a>
                                <div class="item-except text-muted text-sm h-1x">Build a progressive web app using jQuery</div>
                                <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                           
                        </div> -->
                       <!--  <div class="list-item" data-id="17" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false" style="">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-warning">A</span></a></div>
                            <div class="flex"> <a  class="item-author text-color" data-abc="true">Alan musk</a>
                                <div class="item-except text-muted text-sm h-1x">it be advisable for me to think about business content?</div>
                                <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                           
                            <div>
                                
                            </div>
                        </div>-->
                       <!-- <div class="list-item" data-id="8" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-success"><img src="https://img.icons8.com/doodle/24/000000/user-male.png" alt="."></span></a></div>
                            <div class="flex"> <a class="item-author text-color" data-abc="true">Lawrence Telon</a>
                                <div class="item-except text-muted text-sm h-1x">For what reason would it be advisable for me to think</div>
                                <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                            
                            <div>
                              
                            </div>
                        </div>-->
                     <!--     <div class="list-item" data-id="10" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false" style="">
                         <div><a  data-abc="true"><span class="w-40 avatar gd-danger"><img src="https://img.icons8.com/color/16/000000/administrator-male.png" alt="."></span></a></div>
                               <div class="flex"> <a  class="item-author text-color" data-abc="true">Stuart Clark</a>
                               <div class="item-except text-muted text-sm h-1x">For what reason would, i think about business content?&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</div>
                               <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                            
                            <div>
                               
                            </div>-->
                        </div>
                                            </div>
                </div>
                       
                       					<% 
			}else{
			%>
	    <div class="col-md-12 col-sm-12">
        <div class="card shadow-lg border-0 rounded-lg mt-5 mx-auto" style="width: 70%;">
            <h3 class="card-header display-1 text-muted text-center">
                :-)
            </h3>
			<br>
            <span class="card-subtitle mb-2 text-muted text-center">
                Nessuna segnalazione presente.
            </span>
        </div>
    </div>
    <br><br><br><br><br><br><br><br>
    <br><br><br><br><br><br><br><br>
    <br><br><br><br><br><br><br><br><br>
	  <%} %>
				

            </div>
        </div>
    </div>
</div>


<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<%@ include file="footer.jsp"%>
</body>
</html>