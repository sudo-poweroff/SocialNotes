<%@page import="it.unisa.model.PaymentMethodBean"%>
<%@page import="it.unisa.model.PaymentMethodModelDS"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" href="img/favicon.ico">
<title>Effettua Pagamento</title>
<link rel="stylesheet" type="text/css" href="css/payment.css">
</head>
<body>
<%@include file="header_user.jsp" %>
<%
	if (session.getAttribute("username")==null)
		  response.sendRedirect("login.jsp");
    
		  
	String addCoinUrl = "AddCoin";
	addCoinUrl = response.encodeUrl(addCoinUrl);
	String numeroCarta=(String)request.getParameter("numeroCarta");
	System.out.println(numeroCarta);
	DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
	PaymentMethodModelDS paymentModel=new PaymentMethodModelDS(ds);
	PaymentMethodBean card=paymentModel.doRetrieveByKey(numeroCarta);
%>
     	
		
		<section class="credit-card">
		 <div class="container">
		  
			<div class="card-holder">
			  <div class="card-box bg-news">
		       <div class="row">
				<div class="col-lg-6">
				 <div class="img-box">
				   <img src="PrintImage?username=<%=card.getUsername()%>" class="rounded-circle" />
				 </div>
				</div>
				<div class="col-lg-6">
				
				
				<form>
				  <div class="card-details">
					<h3 class="title">Dettagli Carta di Credito</h3>
					<div class="row">
					  <div class="form-group col-sm-7">
					   <div class="inner-addon right-addon">
						<label for="card-holder">Titolare Carta</label>
                        <i class="far fa-user"></i>
						<input id="card-holder" type="text" class="form-control" placeholder="Titolare Carta" aria-label="Titolare Carta" aria-describedby="basic-addon1" value="<%=card.getNomeIntestatario() %> <%=card.getCognomeIntestatario() %>" disabled>
						
					   </div>	
					  </div>
					  <div class="form-group col-sm-5">
						<label for="">Scadenza</label>
						<div class="input-group expiration-date">
						  <input type="text" class="form-control" placeholder="MM" aria-label="MM" aria-describedby="basic-addon1" disabled value="<%=card.getDataScadenza().getMonth()+1%>">
						  <span class="date-separator">/</span>
						  <input type="text" class="form-control" placeholder="YY" aria-label="YY" aria-describedby="basic-addon1" disabled value="<%=card.getDataScadenza().getYear()+1900%>">
						</div>
					  </div>
					  <div class="form-group col-sm-8">
					   <div class="inner-addon right-addon">
						<label for="card-number">Numero Carta</label>
                        <i class="far fa-credit-card"></i>
						<input id="card-number" type="text" class="form-control" placeholder="Numero Carta" aria-label="Numero Carta" aria-describedby="basic-addon1" disabled value="<%=card.getNumeroCarta()%>">
					   </div>	
					  </div>
					  <div class="form-group col-sm-4">
						<label for="cvc">CVC</label>
						<input id="cvc" type="text" class="form-control" placeholder="000" aria-label="CVC" aria-describedby="basic-addon1" maxlength="3" minlength="3" required>
					  </div>
					  <div class="form-group col-sm-12">
						<a href="<%=addCoinUrl %>?coin=<%=Integer.parseInt(request.getParameter("coin"))%>"><button type="button" class="btn btn-primary btn-block">Conferma Acquisto</button></a>
					  </div>
					</div>
				  </div>
				</form>				
				
				</div><!--/col-lg-6 --> 
		  
		       </div><!--/row -->
			  </div><!--/card-box -->
			</div><!--/card-holder -->		 
			
		 </div><!--/container -->
		</section>

<%@include file="footer.jsp" %>
</body>
</html>