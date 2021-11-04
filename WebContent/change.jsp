<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.Date,it.unisa.model.*,java.util.Collection,java.util.Iterator"%>
    
<%@ page import ="java.io.InputStream" %>
<%@page import="com.mysql.cj.jdbc.Blob"%>  
<%@page import="java.util.LinkedList"%>  
<!DOCTYPE html>
<html>
<head prefix="og:http://ogp.me/ns#">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Aggiorna impostazioni</title>
  <link rel="icon" href="img/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/change.css">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />


  <meta name="description" content="Manage your Uber Driving Partner account.">


  <!-- Errorception disabled as trial has expired
      <script>
        (function(_,e,rr,s){_errs=[s];var c=_.onerror;_.onerror=function(){var a=arguments;_errs.push(a);
        c&&c.apply(this,a)};var b=function(){var c=e.createElement(rr),b=e.getElementsByTagName(rr)[0];
        c.src="//beacon.errorception.com/"+s+".js";c.async=!0;b.parentNode.insertBefore(c,b)};
        _.addEventListener?_.addEventListener("load",b,!1):_.attachEvent("onload",b)})
        (window,document,"script","555fd83490f66327760016ad");
      </script>
  -->
  <!-- Raven for Sentry -->
  <script src="//cdn.ravenjs.com/1.1.11/jquery,native/raven.min.js"></script>
  <script>
    Raven.config('https://2f18d47e042011e5affda0369f46ff14@sentry.uberinternal.com/125', {
            // we highly recommend restricting exceptions to a domain in order to filter out clutter
            whitelistUrls: ['partners.uber.com']
        }).install();
  </script>


  <link rel="stylesheet" href="//fonts.googleapis.com/css?family=Open+Sans:300,400,500,700" type="text/css">

  <script>
    window.MP_REGISTER_DATA = {
      'user_id': 'd1e8fb76-bac0-42ff-bd82-d5b7b19b6ab5'
    };
  </script>

	<script type="text/javascript">
	function passwordUguali(){
		//console.log("ciao");
		var pwd1 = document.getElementById("pwd");
		var pwd2 = document.getElementById("newpwd");
		//console.log("password:"+pwd1.value+"conferma:"+pwd2.value);
	    pwd2.classList.remove("is-invalid");
	    pwd2.classList.remove("is-valid");
	    if(pwd1.value===pwd2.value){
	    	//console.log("sono uguali");
	    	pwd2.classList.add("is-valid");
	    	return true;
	    }
	    else{
	    	pwd2.classList.remove("is-valid");
	    	//console.log("sono diversi");
	    	pwd2.classList.add("is-invalid");
	    	return false;
	    }
	}
	
	function cardNumberValidation(number){
		//console.log("ciao");
	    number.classList.remove("is-invalid");
	    number.classList.remove("is-valid");
	    var strLen = /^[0-9]{16}$/;
	    if(number.value.match(strLen)){
	        number.classList.add("is-valid");
	        return true;
	      }
	      else{
	        number.classList.add("is-invalid");
	       	number.focus();
	        return false;
	      }
	}
	</script>

  <script>
    window.utag_data = {};
  </script>

  <link rel="stylesheet" type="text/css" href="https://d1a3f4spazzrp4.cloudfront.net/web-p2/stylesheets/home/icons.e225ddcb0b486de3046f4d4c231185ca.css">
  <link href="//d1a3f4spazzrp4.cloudfront.net/uber-icons/3.3.0/uber-icons.css" rel="stylesheet">
  <style>
  	.btn-principale{
  		background-color: #9697e7 !important;
  	}
  </style>
</head>

<body>

 <%
 if (session.getAttribute("username")==null){
	 String linkLogin = "login.jsp";
	 String encodeURL = response.encodeRedirectURL(linkLogin);
	 response.sendRedirect(encodeURL);
	 
 }
	 String nome = (String)session.getAttribute("nome");
	 String cognome = (String)session.getAttribute("cognome");
	 String username = (String)session.getAttribute("username");
	 Blob image = (Blob)session.getAttribute("img");
	 if(image!=null){
		InputStream img=image.getBinaryStream();
	 }
	 String email = (String)session.getAttribute("email");
	 String password = (String)session.getAttribute("password");
	 Date dataNascita = (Date)session.getAttribute("dataNascita");
	 String matricola = (String)session.getAttribute("matricola");
	// int coin = (int)session.getAttribute("coin");
	 String denominazione = (String)session.getAttribute("denominazione");
	 String dipName = (String)session.getAttribute("dipName");
	 
	 	 
	 DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
	
	
 %>
 <%@include file="header_user.jsp" %>

 	<div class="flexbox height--full main-wrapper"> 
	

	
	<form action=<%="ChangeProfile;jsessionid="+session.getId()%> method="POST" enctype="multipart/form-data" id="main-profile">

	


    <div class="contents flexbox hard">


		<% 
		String success =(String)request.getAttribute("success");
		if (success!=null){
		%>
		<div class="alert alert-success alert-dismissible fade show" role="alert">
  			<strong>Fatto!</strong> Le modifiche sono state salvate con successo:<%=success%>
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    		<span aria-hidden="true">&times;</span>
 			 </button>
		</div>
		<%
		}
		%>
		<%
		String error = (String)request.getAttribute("error");
		if(error!=null){
		%>
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
  			<strong>Ops!</strong> Si &egrave verificato un errore:<%=error%>
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    		<span aria-hidden="true">&times;</span>
 			 </button>
		</div>
		<%
		} 
		%>
		

		
      <div class="soft-double portable--hard">


        <h1 class="page-heading">Profilo</h1>


        <div class="inner bg-color--white">





          <!-- Main profile row -->


          <div class="row-group editable-main-row">
          
          
          
          
          
            <div class="grid data-row">
              <div class="grid__item one-quarter title">
                <img class="profile-picture" src="PrintImage?username=<%=username %>" alt="ciao">
              </div>
              <!--
 -->
              <div class="grid__item one-half data" data-field="None">
                <h1><%=nome %> <%=cognome %></h1>
                <p class='data' data-field='email'><%=email %></p>
                <p class='data' data-field='nickname'><%=username %></p>


              </div>
              <!--
 -->
              <div class="grid__item one-quarter">
                <button href="#" class="btn edit-hidden-form btn-principale">
                  Modifica
                </button>
              </div>
            </div>
            <div class="grid hidden-form hidden">
              
                <input type="hidden" name="_csrf_token" id="-csrf-token" value="1440526831-01-FIiAtX3_sdMNiFb34GXRG49qaUBWsxonFotdT79C_3s=">

                <input type="file" name="picture" class="profile-picture"></input>

                <div class="grid__item one-quarter">

                  <div class="change-profile-picture">
                    <img src="PrintImage?username=<%=username %>" class="profile-picture" alt="ciao">
                    <div class="black-filter"></div>
                    <div class="helper-profile-picture">
                      <p>
                        <i class="icon icon_picture"></i>
                        <br /> Aggiungi foto
                      </p>
                    </div>
                  </div>

                </div>
                <!--
   -->
                <div class="grid__item one-third">
                 <h1><%=nome %> <%=cognome %></h1>
                  <ul class="form-fields">
                    <li>
                      <label>Email</label>
                      <input type="text" class="text-input" name="mail" onblur="validateEmail(this)">
                      <div class="invalid-feedback" id="email-feedback" style="font-size:12px;"></div>
                    </li>
                  </ul>
                  <div class="submit">
                    <div class="messages"></div>
                    <button class="btn btn-principale cancel-hidden-form text-dark">Chiudi</button>
                  </div>
                </div>
              
            </div>
          </div>

          <!-- Address row -->


          <div class="row-group editable-single-row">
            <div class="grid data-row">
              <div class="grid__item one-quarter title">
                Universit&agrave;
              </div>
              <!--
 -->
              <div class="grid__item one-half data" data-field="None">
                <%=denominazione %> - <%=dipName %>
              </div>
              <!--
 -->
              <div class="grid__item one-quarter">
                <button href="#" class="btn edit-hidden-form btn-principale">
                  Modifica
                </button>
              </div>
            </div>

            <div class="grid hidden-form hidden">
              <div class="grid__item one-quarter">
                Università
              </div>
              <!--
 -->
              <div class="grid__item one-third">
                
                  <input type="hidden" name="_csrf_token" id="-csrf-token" value="1440526831-01-FIiAtX3_sdMNiFb34GXRG49qaUBWsxonFotdT79C_3s=">
                  		<%
                  			UniversityModelDS umodel = new UniversityModelDS(ds);
                  			Collection <UniversityBean> universities = umodel.doRetrieveAll();
                  			
                  			DepartmentModelDS dmodel = new DepartmentModelDS(ds);
                  			Collection <DepartmentBean> dipartimenti = dmodel.doRetrieveAll();
                      	%>	
                  		<label>Universit&agrave;</label>
    				    <select class="form-control" name="nomeuni" id="uni">
                        <option value="">Scegli...</option>
                  		<%
                  			
							if(universities!=null&&universities.size()>0){
								Iterator<?> it=universities.iterator();
								while(it.hasNext()){
									UniversityBean ubean=(UniversityBean)it.next();
                  		%>
                         <option value="<%=ubean.getDenominazione()%>"><%=ubean.getDenominazione()%></option>
							<%
								}
							}
							%>
                       </select>	
                       
                       <span class="divider"></span>
                       
                       <label>Corso di studi</label>
                       <select class="form-control" name="dipartimento" id="corso">
                        
                         
                       </select>
                  
                  <div class="submit">
                    <div class="messages"></div>
                    <button class="btn btn-principale cancel-hidden-form text-dark">Chiudi</button>
                    
                  </div>
              
              </div>
            </div>
          </div>


          <!-- Password row -->


          <div class="row-group editable-single-row">
            <div class="grid data-row">
              <div class="grid__item one-quarter title">
                Password
              </div>
              <!--
 -->
              <div class="grid__item one-half data" data-field="None">
                &#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;
              </div>
              <!--
 -->
              <div class="grid__item one-quarter">
                <button href="#" class="btn edit-hidden-form btn-principale">
                  Modifica
                </button>
              </div>
            </div>
            <div class="grid hidden-form hidden">
              <div class="grid__item one-quarter">Password</div>
              <div class="grid__item one-third">
               
                  <input type="hidden" name="_csrf_token" id="_csrf_token1" value="1440526831-01-FIiAtX3_sdMNiFb34GXRG49qaUBWsxonFotdT79C_3s=">

                  <div class="input-group">
                    <input type="password" class="text-input" name="current_password" placeholder="Password corrente">
                    <div class="input-group">
                    	<input type="password" class="text-input" name="password" id="pwd" placeholder="Nuova Password" onblur="passwordValidation(this,5,12)">
                    	<div class="valid-feedback">Password valida.</div>
                		<div class="invalid-feedback">Per favore inserisci una password di lunghezza compresa tra 5 e 12 caratteri, che contenga almeno una lettera maiuscola, un numero e una lettera minuscola.</div>
                		<br>
                    </div>
                    <div class="input-group">
                    	<input type="password" class="text-input" name="confirm_password" id="newpwd" onblur="passwordUguali()" placeholder="Conferma nuova Password">
                    	<div class="valid-feedback">Le password corrispondono.</div>
                		<div class="invalid-feedback">Le password non corrispondono.</div>
                	</div>
                  </div>

                  <div class="submit">
                    <div class="messages"></div>
                    <button class="btn btn-principale cancel-hidden-form text-dark">Chiudi</button>
                  </div>
               
              </div>
              <div class="grid__item one-half"></div>
            </div>
          </div>

          <!-- Language row -->





            <div class="row-group editable-single-row">
              <div class="grid data-row">
                <div class="grid__item one-quarter title">
                    Aggiungi metodo di pagamento
                </div>
                <div class="grid__item one-half data" data-field="None">

                  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
                  <h5><b>Le tue carte</b></h5>
                  <%
                 	Collection<PaymentMethodBean> cards=new LinkedList<PaymentMethodBean>();
             	 	PaymentMethodModelDS payment=new PaymentMethodModelDS(ds);
             	 	cards=payment.doRetrieveByUsername(username);
             	 	if(cards!=null&&cards.size()>0){
             	 		%>
             	 		<ul>
             	 		
             	 		<%
						Iterator<?> it=cards.iterator();
						while(it.hasNext()){
							PaymentMethodBean bean=(PaymentMethodBean)it.next();
							String ultimeCifre=bean.getNumeroCarta().substring(11);
                  %>	
               			 <li class='data' data-field='name'><%=bean.getNomeIntestatario() %> <%=bean.getCognomeIntestatario() %>&nbsp;&nbsp;&nbsp;&nbsp;    Numero Carta: &#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;<%=ultimeCifre %></li>
				<%
						}
             	 	}
				%>
						</ul>


                  </div>
                  <div class="grid__item one-quarter">
                    <button href="#" class="btn edit-hidden-form btn-principale">
                      Aggiungi una carta
                    </button>
                  </div>
                </div>

                <div class="grid hidden-form hidden">
                  <div class="grid__item one-quarter">
                    Aggiungi metodo di pagamento
                  </div>
               <div class="grid__item one-third">
                 
                   <div class="form-group">
                     <label class="col-sm-3 control-label">Titolare Carta</label>
                     <div class="col-sm-9">
                       <input type="text" class="form-control" name="nomecarta" placeholder="Nome titolare"></input>
                       <p class="help-block">Nome che appare sulla carta</p>
                     </div>
                     <div class="col-sm-9">
                       <input type="text" class="form-control" name="cognomecarta" placeholder="Cognome titolare"></input>
                       <p class="help-block">Cognome che appare sulla carta</p>
                     </div>
                   </div>
                   <div class="form-group">
                     <label class="col-sm-3 control-label">Numero carta </label>
                     <div class="col-sm-9">
                       <input type="text" class="form-control" placeholder="&#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226;" name="numcarta" onblur="cardNumberValidation(this);"></input>
                       <div class="valid-feedback">Numero di carta valido.</div>
                <div class="invalid-feedback">Per favore inserisci un numero di carta corretto.</div>
                       <p class="help-block">Le 16 cifre che trovi sulla carta.</p>
                     </div>
                   </div>
                   <div class="form-group">
                     <label class="col-sm-3 control-label">Data scadenza</label>
                     <div class="col-sm-9 form-inline">
                       <select class="form-control" name="month">
                         <option value="01">01</option>
                         <option value="02">02</option>
                         <option value="03">03</option>
                         <option value="04">04</option>
                         <option value="05">05</option>
                         <option value="06">06</option>
                         <option value="07">07</option>
                         <option value="08">08</option>
                       </select>
                       <span class="divider">/</span>
                       <select class="form-control" name="year">
                         <option value="2021">2021</option>
                         <option value="2022">2022</option>
                         <option value="2023">2023</option>
                         <option value="2024">2024</option>
                         <option value="2025">2025</option>
                         <option value="2026">2026</option>
                         <option value="2027">2027</option>
                         <option value="2028">2028</option>
                       </select>
                       <p class="help-block">La data in cui scade la carta. La trovi sul fronte della carta</p>
                     </div>
                     <label class="col-sm-3 control-label">CVC</label>
                     <div class="col-sm-9">
                       <input type="text" class="form-control" style="width: 120px;" placeholder="CVC" maxlength="3">
                       <p class="help-block">Le 3 cifre che trovi sul retro della carta.</p>
                     </div>
                   </div>
                   <hr>
                   <div class="submit">
                     <div class="messages"></div>
                     <button class="btn btn-principale cancel-hidden-form text-dark">Chiudi</button>
                   </div>
                
                 
                 </div>
                </div>
                </div>
                 <div class="row-group editable-single-row">
            <div class="grid data-row">
              <div class="grid__item one-quarter title">
                Elimina metodo di pagamento
              </div>
              <!--
 -->
                <div class="grid__item one-half data" data-field="None">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              </div>
              <!--
 -->
              <div class="grid__item one-quarter">
                <button href="#" class="btn edit-hidden-form btn-principale">
                  Modifica
                </button>
              </div>
            </div>
            <div class="grid hidden-form hidden">
              <div class="grid__item one-quarter">Elimina metodo di pagamento</div>
              <div class="grid__item one-third">
               
				  <div class="form-group">
                     <label class="col-sm-3 control-label">Numero carta da eliminare</label>
                     <div class="col-sm-9">
                       <input type="text" class="form-control" placeholder="&#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226;" name="numcartaDelete" onblur="cardNumberValidation(this)"></input>
                    <div class="valid-feedback">Numero di carta valido.</div>
                <div class="invalid-feedback">Per favore inserisci un numero di carta corretto.</div>
                       <p class="help-block">Le 16 cifre che trovi sulla carta.</p>
                     </div>

                  <div class="submit">
                    <div class="messages"></div>
                    <button class="btn btn-principale cancel-hidden-form text-dark">Chiudi</button>
                  </div>
               
              </div>
              <div class="grid__item one-half"></div>
            </div>
          </div>
                <div class="grid__item one-quarter"></div>
                	<div class="grid__item one-half">
              		<div class="submit">
                     <div class="messages"></div>
                     <button class="btn btn--primary btn-principale text-dark" type="submit">Salva le modifiche</button>
                     <button class="btn btn-danger" type="reset">Annulla</button>
                   </div>
                   <br>
                   </div>
                	
                </div>
                </div>






            </div>
            
            
            </form>
            
            
            </div>




<%@ include file="footer.jsp" %>


  <script type='text/javascript' src="https://d1a3f4spazzrp4.cloudfront.net/web-p2/scripts/vendor.7c88512201ad36e1f9d9ff0e6d2077c8.js"></script>


  <script type="text/javascript">
    var ANALYTICS_CONFIG = {
          services: {
            tealium: {
              account: 'uber',
              profile: 'main',
              geo: "NG",
              env: "prod"
            }
          },
          debug: false
        };
  </script>

	


  <script src="https://d1a3f4spazzrp4.cloudfront.net/web-p2/scripts/analytics.cb25f45deae12fe2582214808908f898.js"></script>


  <script src="https://d1a3f4spazzrp4.cloudfront.net/web-p2/scripts/config.12a9959a1bdf7bcf0055e0f61fe3285a.js"></script>
  <script src="https://d1a3f4spazzrp4.cloudfront.net/web-p2/scripts/core.fc0a8ee755f365192f254904593a84f5.js" defer></script>

  <script type="text/javascript">
    if (window.mixpanel && window.mixpanel.identify) {
        mixpanel.identify('d1e8fb76-bac0-42ff-bd82-d5b7b19b6ab5');
      }
  </script>
 
  <script src="https://d1a3f4spazzrp4.cloudfront.net/web-p2/scripts/profile/profile.08c09f44a289bf166e5d65b952b1ee74.js"></script>
  <script src="js/form-validation.js" type="text/javascript"></script>



   <script src="js/FindDepartment.js" type="text/javascript"></script>
</body>

</html>