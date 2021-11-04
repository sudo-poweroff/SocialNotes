<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/prezzi.css" />
<link rel="icon" href="img/favicon.ico">

<title>I nostri abbonamenti</title>
</head>
<body>

<%
String adminURL = "admin.jsp";
String choosePaymentUrl = "choosePayment.jsp";
   if(session.getAttribute("username")==null){
	  %>
	  
	  <jsp:include page="header.jsp"></jsp:include>
	  
	  <%
   }else{
		  if (((int)session.getAttribute("role"))==1){
			  adminURL = response.encodeURL(adminURL);
			  response.sendRedirect(adminURL);
			  return;
		  }
		  
		  choosePaymentUrl = response.encodeURL(choosePaymentUrl);
	   %>
	   <jsp:include page="header_user.jsp"></jsp:include>
	   <% }

%>


<br><br>
 <div class="wrapper">
        <div class="pricing-table group">
            <h1 class="heading">SCEGLI QUANTI COINS CARICARE</h1>
            <div class="block personal fl card shadow" style="border-radius: 15px;right:17px;">
                <h3 class="title">pochi ma buoni</h3>
                <div class="content">
                    <p class="price">
                        
                        <span>50</span>
                        <sub>COINS</sub>
                    </p>
                    <p class="hint">Sei alle prime armi con SocialNotes</p>
                </div>
                <ul class="features">
                    <li><span class="fontawesome-cog"></span>Puoi scaricare fino a 5 documenti</li>
                    
                    <li><span class="fontawesome-cloud"></span>A SOLI 5 &euro;</li>
                </ul>
                <div class="pt-footer">
                    <a href="<%=choosePaymentUrl %>?coin=50">ACQUISTA COINS</a>
                </div>
            </div>
            <div class="block professional fl card shadow" style="border-radius: 15px;right:17px;">
                <h3 class="title">Il giusto sprint</h3>
                <div class="content">
                    <p class="price">
                        
                        <span>100</span>
                        <sub>COINS</sub>
                    </p>
                    <p class="hint">Hai bisogno urgentemente di appunti... <br>CORRI A STUDIARE!</p>
                </div>
                <ul class="features">
                    <li><span class="fontawesome-cog"></span>Puoi scaricare fino a 10 documenti</li>
                    <li><span class="fontawesome-star"></span>Riceverai ben 20 COINS EXTRA</li>
                
                    <li><span class="fontawesome-cloud"></span>A SOLI 10 &euro; </li>
                </ul>
                <div class="pt-footer">
                        <a href="<%=choosePaymentUrl %>?coin=120">ACQUISTA COINS</a>
                </div>
            </div>
            <div class="block business fl card shadow" style="border-radius: 15px;right:17px;">
                <h3 class="title">Vai alla grande!</h3>
                <div class="content">
                    <p class="price">
                        
                        <span>500</span>
                        <sub> COINS</sub>
                    </p>
                    <p class="hint">Sei ormai cliente affezionato</p>
                </div>
                <ul class="features">
                    <li><span class="fontawesome-cog"></span>Puoi scaricare fino a 50 Documenti</li>
                    <li><span class="fontawesome-star"></span>Ricevi 100 COINS EXTRA </li>
                    <li><span class="fontawesome-cloud"></span>A SOLI 20 &euro;</li>
                </ul>
                <div class="pt-footer">
                      <a href="<%=choosePaymentUrl %>?coin=600">ACQUISTA COINS</a>
                </div>
            </div>
        </div>
    </div>
    <br><br>
    <%@ include file="footer.jsp" %>
</body>
</html>