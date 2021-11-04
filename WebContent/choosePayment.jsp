<%@page import="it.unisa.model.PaymentMethodBean"%>
<%@page import="it.unisa.model.PaymentMethodModelDS"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" href="img/favicon.ico">
<title>Scegli la carta di credito</title>
<link rel="stylesheet" href="https://allyoucan.cloud/cdn/icofont/1.0.1/icofont.css" integrity="sha384-jbCTJB16Q17718YM9U22iJkhuGbS0Gd2LjaWb4YJEZToOPmnKDjySVa323U+W7Fv" crossorigin="anonymous">
<link rel="stylesheet" type="text/css"    href="choosePayment.css">
</head>
<body>
<%
	if (session.getAttribute("username")==null)
		  response.sendRedirect("login.jsp");
	String username=(String)session.getAttribute("username");
	DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
%>

<%@include file="header_user.jsp" %>



<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            
        </div>
        <div class="col-md-8">
            
                
                 
                        <h4 class="font-weight-bold mt-0 mb-4">SCEGLI LA CARTA CON CUI EFFETTUARE IL PAGAMENTO</h4>
                        <!-- inizio codice java -->
                        <%
	                     	Collection<PaymentMethodBean> cards=new LinkedList<PaymentMethodBean>();
	                 	 	PaymentMethodModelDS payment=new PaymentMethodModelDS(ds);
	                 	 	cards=payment.doRetrieveByUsername(username);
	                 	 	int i=0;
	                 	 	if(cards!=null&&cards.size()>0){
	    						Iterator<?> it=cards.iterator();
	    						while(it.hasNext()){
	    							i++;
	    							PaymentMethodBean card=(PaymentMethodBean)it.next();
	    							String ultimeCifre=card.getNumeroCarta().substring(11);
	                      
                        %>
      
                                <div class="bg-white card payments-item mb-4 shadow-sm col-md-5">
                                    <div class="gold-members p-4">
                                        <div class="media">
                                            <div class="media-body">
                                                <a href="<%=response.encodeURL("payment.jsp") %>?numeroCarta=<%=card.getNumeroCarta()%>&coin=<%=Integer.parseInt(request.getParameter("coin"))%>">
                                                    <i class="fab fa-cc-visa" style="font-size:100px"></i>
                                                    <h6 class="mb-1">XXXX-XXXX-XXXX-<%=ultimeCifre %></h6>
                                                    <h6>SCADENZA <%=card.getDataScadenza().getMonth()+1%>/<%=card.getDataScadenza().getYear()+1900 %></h6>
                                                </a>
                                                
                                                   
                                                    
                                            </div>
                                        </div>

                                    </div>
                                </div>
               
	                        <%
		    					}
	                 	 	}
	                 	 	else{
	                 	 	%>
	                 	 		<h4>Non hai nessun metodo di pagamento!</h4>
                              <a href="<%=response.encodeURL("change.jsp")%>">Aggiugi metodo di pagamento</a>
	                 	 		
	                 	 	<%	
	                 	 	}
	                 	 	%>
                            <!-- fine codice java -->
                    
                    
                      
                  
              
                <div class="col-md-2">
            
        </div>
        <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
            </div>
        </div>
    </div>


<%@include file="footer.jsp" %>
</body>
</html>