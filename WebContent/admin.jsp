<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  
    <link rel="stylesheet" type="text/css" href="css/admin.css">
    <meta charset="utf-8">
    <link rel="icon" href="img/favicon.ico">
    <title>Admin</title>
  </head>
  <body>
  <%

  String homeUser = "homepage_user.jsp";

    if ((session.getAttribute("username")==null)){
    	response.sendRedirect("login.jsp");
    	return;
    }else if (((int)session.getAttribute("role"))!=1){
    	String encodeHomeUserURL = response.encodeRedirectURL(homeUser);
    	response.sendRedirect(encodeHomeUserURL);
    	return;
    }
  %>

    
    
    
    <%@include file="header_admin.jsp" %> 
<div class="container">
  <div class="row">

<div class="col-md-2"></div>
<div class="col-md-8">

 
    <h2>Bentornato Admin</h2>
    <h2>Andamento settimanale</h2>
    
    
   <div class="chart chart--dev">
    <span class="chart__title">UTENZA</span>
    <ul class="chart--horiz">
      <li class="chart__bar" style="width: 78%;">
        <span class="chart__label">
          LUNEDI'
        </span>
      </li>
      <li class="chart__bar" style="width: 86%;">
        <span class="chart__label">
          MARTEDI'
        </span>
      </li>
      <li class="chart__bar" style="width: 54%;">
        <span class="chart__label">
          MERCOLEDI'
        </span>
      </li>
      <li class="chart__bar" style="width: 70%;">
        <span class="chart__label">
          GIOVEDI'
        </span>
      </li>
      <li class="chart__bar" style="width: 45%;">
        <span class="chart__label">
          VENERDI'
        </span>
      </li>
      <li class="chart__bar" style="width: 30%;">
        <span class="chart__label">
          SABATO
        </span>
      </li>
      <li class="chart__bar" style="width: 25%; height: 26px;">
        <span class="chart__label">
          DOMENICA
        </span>
      </li>
      </ul>
  </div>

 
  </div>

  </div>




   







<div class="col-md-2"></div>


<div class="row row-cols-1 row-cols-md-2">
  <div class="col mb-4">
    <div class="card">
      <img src="img/news.png" class="card-img-top" alt="..." height="300">
      <div class="card-body">
            <a href="<%=notizieURL %>"><h5 class="card-title">Carica News</h5></a>
      <p class="card-text">Carica le news , per far rimanere sempre aggiornato SocialNotes</p>
       </div>
    </div>
  </div>
  <div class="col mb-4">
    <div class="card">
      <img src="img/materiale.jpg" class="card-img-top" alt="..." height="300">
      <div class="card-body">
       <a href="<%=materialeURL %>" >  <h5 class="card-title">Verifica Materiale</h5></a>
      <p class="card-text">Controlla il materiale caricato dagli utenti e imposta un costo in coins</p>
      </div>
    </div>
  </div>


</div>

</div>
<br>
<br>
  
<%@include file="footer.jsp" %>
  </body>
  <script>
    const html = document.documentElement;
const body = document.body;
const menuLinks = document.querySelectorAll(".admin-menu a");
const collapseBtn = document.querySelector(".admin-menu .collapse-btn");
const toggleMobileMenu = document.querySelector(".toggle-mob-menu");
const switchInput = document.querySelector(".switch input");
const switchLabel = document.querySelector(".switch label");
const switchLabelText = switchLabel.querySelector("span:last-child");
const collapsedClass = "collapsed";
const lightModeClass = "light-mode";

/*TOGGLE HEADER STATE*/
collapseBtn.addEventListener("click", function () {
  body.classList.toggle(collapsedClass);
  this.getAttribute("aria-expanded") == "true"
    ? this.setAttribute("aria-expanded", "false")
    : this.setAttribute("aria-expanded", "true");
  this.getAttribute("aria-label") == "collapse menu"
    ? this.setAttribute("aria-label", "expand menu")
    : this.setAttribute("aria-label", "collapse menu");
});

/*TOGGLE MOBILE MENU*/
toggleMobileMenu.addEventListener("click", function () {
  body.classList.toggle("mob-menu-opened");
  this.getAttribute("aria-expanded") == "true"
    ? this.setAttribute("aria-expanded", "false")
    : this.setAttribute("aria-expanded", "true");
  this.getAttribute("aria-label") == "open menu"
    ? this.setAttribute("aria-label", "close menu")
    : this.setAttribute("aria-label", "open menu");
});

/*SHOW TOOLTIP ON MENU LINK HOVER*/
for (const link of menuLinks) {
  link.addEventListener("mouseenter", function () {
    if (
      body.classList.contains(collapsedClass) &&
      window.matchMedia("(min-width: 768px)").matches
    ) {
      const tooltip = this.querySelector("span").textContent;
      this.setAttribute("title", tooltip);
    } else {
      this.removeAttribute("title");
    }
  });
}

/*TOGGLE LIGHT/DARK MODE*/
if (localStorage.getItem("dark-mode") === "false") {
  html.classList.add(lightModeClass);
  switchInput.checked = false;
  switchLabelText.textContent = "Chiaro";
}

switchInput.addEventListener("input", function () {
  html.classList.toggle(lightModeClass);
  if (html.classList.contains(lightModeClass)) {
    switchLabelText.textContent = "Chiaro";
    localStorage.setItem("dark-mode", "false");
  } else {
    switchLabelText.textContent = "Scuro";
    localStorage.setItem("dark-mode", "true");
  }
});



  </script>
</html>
