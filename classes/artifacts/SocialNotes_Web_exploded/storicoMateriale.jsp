<%@page import="materiale.MaterialBean"%>
<%@page import="materiale.CourseModelDS"%>
<%@page import="materiale.CourseBean"%>
<%@page import="materiale.FeedbackModelDS"%>
<%@page import="materiale.FeedbackBean"%>
<%@page import="materiale.MaterialModelDS"%>
<%@page import="acquisto.PurchaseBean"%>
<%@page import="acquisto.PurchaseModelDS"%>
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
<title>Storico Materiale</title>
<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" href="css/errorSearch.css">
<link rel="stylesheet" type="text/css" href="css/editUser.css">
</head>
<body>

<% 
String homeURL = "homepage.jsp";
String downloadZipLink = "DownloadZip";
if((session.getAttribute("username")!=null)&&((int)session.getAttribute("role")!=0)){
    homeURL = response.encodeUrl(homeURL);
	response.sendRedirect(response.encodeRedirectUrl("homepage.jsp"));
}else{
	downloadZipLink = response.encodeUrl(downloadZipLink);
	%>
<jsp:include page="header_user.jsp"></jsp:include>
<% }%>


<div class="container">
<br><br><br>
<div class="row">

<%
DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");

PurchaseModelDS pModel = new PurchaseModelDS(ds);
MaterialModelDS mModel = new MaterialModelDS(ds);
CourseModelDS cModel = new CourseModelDS(ds);

Collection <PurchaseBean> collection = pModel.doRetrieveByUsername((String)session.getAttribute("username"));


if(collection!=null&&collection.size()>0){
	Iterator<?> it=collection.iterator();
%>
<div class="container">
  <form method="POST" action=<%=downloadZipLink %>>
    <div class="form-group">
     <table class="table manage-candidates-top mb-0">
            <thead>
              <tr>
                <th>Materiale Aquistato</th>
              </tr>
            </thead>
            <tbody>
<%

	while(it.hasNext()){
		PurchaseBean pBean=(PurchaseBean)it.next();
		
		MaterialBean mBean = mModel.doRetrieveByKey(((Integer.toString(pBean.getCodiceMateriale()))));
		
		String keyCourse = String.valueOf(mBean.getCodiceCorso());
		//System.out.println("CODICE CORSO : "+keyCourse);
		CourseBean cBean = cModel.doRetrieveByKey(keyCourse);
		int feedback = mModel.doRetrieveFeedback(mBean.getCodiceMateriale());

%>
  
  

  
   <tr class="candidates-list">
                        <td class="title">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
          
            <div class="well search-result">
                <div class="row">
                        <div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
                            <img class="img-responsive" src="PrintAnteprima?codice=<%=mBean.getCodiceMateriale() %>" width="160" height="160" alt="hello">
                        </div>
                        <div class="col-xs-6 col-sm-9 col-md-9 col-lg-10 title">
                        <% 
						String documentPreviewLink="documentPreview.jsp";
	  
   						if(session.getAttribute("username")!=null){
						  
						   documentPreviewLink=response.encodeURL(documentPreviewLink);
						   }%>
                            <a href="documentPreview.jsp;jsessionid=<%=session.getId() %>?codice=<%=mBean.getCodiceMateriale()%>"> <h4><%=mBean.getDescrizione() %></h4></a>
                            <span>Utente: <%=mBean.getUsername() %></span><br>
                            <span>Aquistato il : <%=pBean.getDataAcquisto() %></span><br>
                            <span>Data caricamento : <%=mBean.getDataCaricamento() %></span><br>
                            <span>Feedback :<%=feedback %></span><br>
                            <span>Corso :  <%=cBean.getNome() %></span><br>
                        </div>
                        <div>
                                        <input class="form-check-input" type="checkbox" id="inlineCheckbox1" name ="materiale" value="<%=mBean.getCodiceMateriale()%>">
                <label class="form-check-label" for="inlineCheckbox1">
                    </div>
                    </div>
            
          
           
        </div>
         <hr class="my-4">
    </div>
    </td>
    </tr>

      <%}%>
          </tbody>
    </table>
               <div class="col-auto">
      <button type="submit" class="btn btn-primary mb-2" style="background-color:#9697e7;">Scarica il materiale selezionato</button>
      <button type="reset" class="btn btn-primary mb-2" style="background-color:#9697e7;">Reset</button>
    </div>
    </div>
          </form>
          </div>


	<% 
}else{
	%>
	    <div class="col-md-12 col-sm-12">
        <div class="card shadow-lg border-0 rounded-lg mt-5 mx-auto" style="width: 70%;">
            <h3 class="card-header display-1 text-muted text-center">
                :-(
            </h3>

            <span class="card-subtitle mb-2 text-muted text-center">
                Non hai ancora acquistato su SocialNotes
            </span>

            <div class="card-body mx-auto">
                <a type="button" href="<%=homeURL %>"
                class="btn btn-sm btn-info text-white"> Back To Home </a>
            </div>
        </div>
    </div>
	  <%} %>
</div>
</div>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<%@include file="footer.jsp" %>
</body>
</html>