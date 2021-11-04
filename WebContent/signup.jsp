<%@ page language="java" contentType="text/html"
    pageEncoding="ISO-8859-1" import="java.util.*,it.unisa.model.*,java.io.InputStream,javax.sql.DataSource"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>SocialNotes: Registrazione</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

	<link rel="stylesheet" href="css/signup.css">
	
  </head>

  <body class="bg-light">
  <%
 
 
	  synchronized(session) {
	  
		 if (session.getAttribute("username")!=null){ 
		  
	 String link = "homepage_user.jsp";
	 String encodedURL = response.encodeRedirectURL(link);
	 response.sendRedirect(encodedURL);
	 }
		 
  }
  
  %>
	<%
	String errore = (String) request.getAttribute("error");
	if (errore != null) {
	%>

	<div class="alert alert-danger alert-dismissible fade show" role="alert">
		<strong>Attenzione!</strong> La registrazione non è andata a buon fine, si prega di riprovare.
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

	<%
	}
	%>
	 
    <div class="container">
    
    
    
      <div class="py-5 text-center">
        <a href="homepage.jsp"><img class="d-block mx-auto mb-4" src="img/logo.png" alt="SocialNotes" width="140" height="140"></a>
        <h2>Registrazione</h2>
      </div>

      <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">


          <form name="registrazione" id="reg" action="SignupControl" method="POST" onSubmit="return formValidation();" novalidate>


            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="firstName">Nome:</label>
                <input type="text" class="form-control" name="firstName" id="firstName" onblur="allLetter(this)" required>
                <div class="invalid-feedback">
                  Campo obbligatorio. Solo lettere ammesse.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="lastName">Cognome:</label>
                  <input type="text" class="form-control" name="lastName" id="lastName" onblur="allLetter(this)" required>
                <div class="invalid-feedback">
                  Campo obbligatorio. Solo lettere ammesse.
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="username">Nome utente:</label>
              <div class="input-group">

                <div class="input-group-prepend">
                  <span class="input-group-text">@</span>
                </div>
                
                
                <input type="text" class="form-control" name="username" id="username" placeholder="Nome Utente" onblur="usernameValidation(this,3,15);" required>
                
                <!-- Feedback importante deve rimanere vuoto-->
                <div class="invalid-feedback" id="username-feedback" style="width: 100%;"></div>
                
                
              </div>
              <div class="form-group">
                <label for="pwd">Password:</label>
                <input type="password" class="form-control" name="password" id="password" placeholder="Inserisci password" name="pswd" onblur="passwordValidation(this,5,12)" required>
                <div class="valid-feedback">Password valida.</div>
                <div class="invalid-feedback">Per favore inserisci una password di lunghezza compresa tra 5 e 12 caratteri, che contenga almeno una lettera maiuscola, un numero e una lettera minuscola.</div>
              </div>
            </div>

            <div class="mb-3">
              <label for="email">Email:</label>
                <input type="email" class="form-control" name="email" id="email" placeholder="tu@esempio.com" onblur="validateEmail(this)" required >
              <!-- Importante che sia vuoto per AJAX -->
              <div class="invalid-feedback" id="email-feedback"></div>
            </div>



            <div class="row">
              <div class="col-md-6 mb-3">
                  <label for="uni">Universit&agrave;:</label>
                  <select class="custom-select d-block w-100" id="uni" name="uni">
                    <option value="">Scegli...</option>
                    
                    <%
	          			DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
	          			UniversityModelDS umodel = new UniversityModelDS(ds);
	          			Collection <UniversityBean> universities = umodel.doRetrieveAll();
                    
                    
                    %>
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
                  <div class="invalid-feedback">
                    Per favore seleziona una universit&agrave;.
                  </div>
              </div>
       
              <div class="col-md-6 mb-3">
                  <label for="corso">Corso di studi:</label>
                  <select class="custom-select d-block w-100" id="corso" name="corso">
              
                   
                   
                  </select>
                  <div class="invalid-feedback">
                    Per favore seleziona un corso di studi.
                  </div>
              </div>
            </div>
			<div class="mb-3">
              <label for="datanascita">Data di Nascita:</label>
                <input type="date" class="form-control" name="nascita" id="nascita" placeholder="gg/mm/aaaa" onblur="validateDate(this)" required >
              <div class="valid-feedback">Data di nacita valida.</div>
              <div class="invalid-feedback">Inserisci un formato di data corretto (gg/mm/aaaa)</div>
            </div>

            <hr class="mb-4">
            <div class="row">
              <!-- 3 col per mettere il bottone centrale -->
              <div class="col"></div>
              <div class="col">
                <input class="btn btn-principale btn-lg" type="submit" value="Registrati ora">
                <button class="bottoneindietro" onclick="history.back()">Torna indietro</button>
              </div>
              <div class="col"></div>
            </div>


          </form>
        </div>
      </div>
		<p id="result"></p>
      <footer class="my-5 pt-5 text-muted text-center text-small">
        <p class="mb-1">&copy; 2021 SocialNotes</p>
        <ul class="list-inline">
			<li class="list-inline-item"><a href="PrivacyPolicy.html">Privacy & Terms</a></li>
        	<li class="list-inline-item"><a href="mailto:socialnotes@gmail.com">Support</a></li>
        </ul>
      </footer>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
   <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
   
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>


    <script src="js/form-validation.js" type="text/javascript"></script>
    <script src="js/FindDepartment.js" type="text/javascript"></script>

  </body>
</html>