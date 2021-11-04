<%@page import="it.unisa.model.MaterialBean"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Collection"%>
<%@page import="it.unisa.model.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.unisa.model.CourseBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" href="img/favicon.ico">
<title>Il tuo carrello</title>
 <link rel="stylesheet" type="text/css" href="css/cart.css">
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<%
	if (session.getAttribute("username")==null)
	  response.sendRedirect("login.jsp");
	DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
	Collection<MaterialBean> cart=(Collection<MaterialBean>)session.getAttribute("cart");
	CourseModelDS cModel=new CourseModelDS(ds);
	MaterialModelDS material = new MaterialModelDS(ds);
	
	String buyMaterialUrl = "buyMaterial.jsp";
	String RemoveFromCart = "RemoveFromCart";
	String documentPreviewUrl = "documentPreview.jsp";
	documentPreviewUrl = response.encodeURL(documentPreviewUrl);
	RemoveFromCart = response.encodeURL(RemoveFromCart);
	buyMaterialUrl = response.encodeURL(buyMaterialUrl);
	%>

 <%@include file="header_user.jsp" %>

<div class="container px-3 my-5 clearfix">
<% 
		String success =(String)request.getAttribute("success");
		if (success!=null){
		%>
		<div class="alert alert-success alert-dismissible fade show" role="alert">
  			<strong>Fatto!</strong> <%=success%>
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    		<span aria-hidden="true">&times;</span>
 			 </button>
		</div>
		<%
		}
		String error = (String)request.getAttribute("error");
		if(error!=null){
		%>
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
  			<strong>Ops!</strong> <%=error%>
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    		<span aria-hidden="true">&times;</span>
 			 </button>
		</div>
		<%
		} 
		%>
		
    <div class="card">
        <div class="card-header">
            <h2>Carrello</h2>
        </div>
        <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered m-0 ">
                <thead>
                  <tr>
                    <th class="text-center py-3 px-4" style="min-width: 400px;">Nome del prodotto &amp; Dettagli</th>
                    <th class="text-right py-3 px-4" style="width: 100px;">Prezzo</th>
                    <th class="text-center align-middle py-3 px-0" style="width: 40px;"><a href="#" class="shop-tooltip float-none text-light" title="" data-original-title="Clear cart"><i class="ino ion-md-trash"></i></a></th>
                  </tr>
                </thead>
                <tbody id="articolo">
        		<%
        			int tot=0;
      
	        		if(cart!=null&&cart.size()>0){
	        			Iterator<?> it=cart.iterator();
	        			while(it.hasNext()){
	        			
	        				MaterialBean mbean=(MaterialBean)it.next();
	        				String keyCourse = String.valueOf(mbean.getCodiceCorso());
	        				CourseBean cBean = cModel.doRetrieveByKey(keyCourse);
	        				int feedback = material.doRetrieveFeedback(mbean.getCodiceMateriale());
        					tot+=mbean.getCosto();
        		%>
        		<tr>
                <td>
                   <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
          
            <div class="well search-result">
                <div class="row">
                        <div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
                            <img class="img-responsive" src="PrintAnteprima?codice=<%=mbean.getCodiceMateriale() %>" width="160" height="160" alt="hello">
                        </div>
                        <div class="col-xs-4 col-sm-9 col-md-9 col-lg-10 title" style="padding-left:85px">
                            <a href="<%=documentPreviewUrl %>?codice=<%=mbean.getCodiceMateriale()%>"> <h4><%=mbean.getDescrizione() %></h4></a>
                            <span>Utente: <%=mbean.getUsername() %></span><br>
                            <span>Data caricamento : <%=mbean.getDataCaricamento() %></span><br>
                            <span>Feedback :<%=feedback %></span><br>
                            <span>Corso :  <%=cBean.getNome() %></span><br>
                        </div>
                    </div>
            
          
           
        </div>
    </div>
    </td>
    <td><span><b><%=mbean.getCosto() %></b></span><br></td>
    <td class="text-center align-middle px-0"> <a href="<%=RemoveFromCart %>?codice=<%=mbean.getCodiceMateriale()%>" class="shop-tooltip close float-none text-danger" title="" data-original-title="Remove">X</a></td>
    </tr>
    <%
	        			}} %>
    </tbody>
              </table>
             
            </div>
        
            <br>
             <div class="float-right">
              <div class="d-flex">
                
                <div class="float-right">
                 <div class="text-large">
                  <label class="text-muted font-weight-normal m-0 ">Prezzo Totale:</label>
                  <strong><%=tot %></strong>
                  </div>
                </div>
              </div>
            </div>
        	<br>
            <div class="float-right">
            <button type="button" class="btn btn-lg btn-primary mt-2" onclick="window.location.href='<%=buyMaterialUrl%>?tot=<%=tot%>'">Continua Acquisto</button> 
            </div>
          </div>
      </div>
      <br><br><br><br><br><br><br><br><br><br><br><br><br>
  </div>
<%@include file="footer.jsp" %>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
   
</html>