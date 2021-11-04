<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SocialNotes</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
<link rel="icon" href="img/favicon.ico">


<style>
.immagine {
	text-align: center;
}

.carduni {
	box-shadow: 5px 5px 10px 2px #333333;
	-webkit-box-shadow: 5px 5px 10px 2px #333333;
	-moz-box-shadow: 5px 5px 10px 2px #333333;
	margin-bottom: 2%;
}

.divtrasparente {
	background-color: #000;
	color: #fff;
	opacity: .5;
}

svg.radial-progress {
	height: auto;
	max-width: 200px;
	padding: 1em;
	transform: rotate(-90deg);
	width: 100%;
}

svg.radial-progress circle {
	fill: rgba(0, 0, 0, 0);
	stroke: #000;
	stroke-dashoffset: 219.91148575129;
	stroke-width: 10;
}

svg.radial-progress circle.incomplete {
	opacity: 0.25;
}

svg.radial-progress circle.complete {
	stroke-dasharray: 219.91148575129;
}

svg.radial-progress text {
	fill: #000;
	text-anchor: middle;
}

svg.radial-progress circle {
	stroke: #9697e6;
}

body{margin-top:20px;}

.align-center {
    text-align: center;
}
.hash-list {
    display: block;
    padding: 0;
    margin: 0 auto;
}

@media (min-width: 768px){
    .hash-list.cols-3 > li:nth-last-child(-n+3) {
        border-bottom: none;
    }
}
@media (min-width: 768px){
    .hash-list.cols-3 > li {
        width: 33.3333%;
    }
}
.hash-list > li {
    display: block;
    float: left;
    border-right: 1px solid rgba(0, 0, 0, 0.2);
    border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}
.pad-30, .pad-30-all > * {
    padding: 30px;
}
img {
    border: 0;
}
.mgb-20, .mgb-20-all > * {
    margin-bottom: 20px;
}
.wpx-100, .wpx-100-after:after {
    width: 100px;
}
.img-round, .img-rel-round {
    border-radius: 50%;
}
.padb-30, .padb-30-all > * {
    padding-bottom: 30px;
}

.mgb-40, .mgb-40-all > * {
    margin-bottom: 40px;
}
.align-center {
    text-align: center;
}
[class*="line-b"] {
    position: relative;
    padding-bottom: 20px;
    border-color: #E6AF2A;
}
.fg-text-d, .fg-hov-text-d:hover, .fg-active-text-d.active {
    color: #222;
}
.font-cond-b {
    font-weight: 700 !important;
}


/**********************
/***** Services *******
/*********************/
@import url('https://maxcdn.bootstrapcdn.com/font-awesome/5.7.0/css/font-awesome.min.css');
section{
    padding: 60px 0;
}
section .section-title{
	text-align:center;
	color:#white;
	margin-bottom:50px;
	text-transform:uppercase;
}
#what-we-do{
	background:#ffffff;
}
#what-we-do .card{
	padding: 1rem!important;
	border: none;
	margin-bottom:1rem;
	-webkit-transition: .5s all ease;
	-moz-transition: .5s all ease;
	transition: .5s all ease;
}
#what-we-do .card:hover{
	-webkit-box-shadow: 5px 7px 9px -4px rgb(158, 158, 158);
	-moz-box-shadow: 5px 7px 9px -4px rgb(158, 158, 158);
	box-shadow: 5px 7px 9px -4px rgb(158, 158, 158);
}
#what-we-do .card .card-block{
	padding-left: 50px;
    position: relative;
}
#what-we-do .card .card-block a{
	color: #9697e7 !important;
	font-weight:700;
	text-decoration:none;
}
#what-we-do .card .card-block a i{
	display:none;
	
}
#what-we-do .card:hover .card-block a i{
	display:inline-block;
	font-weight:700;
	
}
#what-we-do .card .card-block:before{
	font-family: FontAwesome;
    position: absolute;
    font-size: 39px;
    color: #9697e7;
    left: 0;
	-webkit-transition: -webkit-transform .2s ease-in-out;
    transition:transform .2s ease-in-out;
}
#what-we-do .card .block-1:before{
    content: "1";
}
#what-we-do .card .block-2:before{
    content: "2";
}
#what-we-do .card .block-3:before{
    content: "3";
}
#what-we-do .card .block-4:before{
    content: "4";
}
#what-we-do .card .block-5:before{
    content: "5";
}
#what-we-do .card .block-6:before{
    content: "6";
}
#what-we-do .card:hover .card-block:before{
	-webkit-transform: rotate(360deg);
	transform: rotate(360deg);	
	-webkit-transition: .5s all ease;
	-moz-transition: .5s all ease;
	transition: .5s all ease;
}

</style>


</head>
<body>

<%
String linkAdmin = "admin.jsp";
String link = "homepage_user.jsp";
if (session.getAttribute("username")!=null){
	
	if (((int)session.getAttribute("role"))==1){
		 String encodedURL = response.encodeRedirectURL(linkAdmin);
		 response.sendRedirect(encodedURL);
		 return;
	}
	
	 String encodedURL = response.encodeRedirectURL(link);
	 response.sendRedirect(encodedURL);
	 return;
}
    	
%>


	<%@ include file="header.jsp"%>
	<br>
	<br>
	<div class="container">
		<div class="row">
			<div class="col-12">
				<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
					<div class="carousel-inner">
						<div class="carousel-item active">
							<img class="d-block w-100" src="img/carousel1.jpg" alt="First slide">
							<div class="carousel-caption d-none d-md-block divtrasparente">
								<h5>SEI INDIETRO CON LO STUDIO?</h5>
								<p>Grazie a SocialNotes puoi trovare tutto il materiale necessario per recuperare gli argomenti arretrati e riuscire così a preparare i tuoi esami in tempo.</p>
							</div>
						</div>
						<div class="carousel-item">
							<img class="d-block w-100" src="img/carousel2.jpg" alt="Second slide">
							<div class="carousel-caption d-none d-md-block divtrasparente">
								<h5>TIENITI IN CONTATTO COL MONDO</h5>
								<p>SocialNotes non è solo un sito di condivisione materiale. E' un vero e proprio social che ti permette di restare in contatto con gli altri utenti. SocialNotes non è solo scambio di materiale...E' SCAMBIO DI IDEE </p>
							</div>
						</div>
						<div class="carousel-item">
							<img class="d-block w-100" src="img/carousel3.jpg" alt="Third slide">
							<div class="carousel-caption d-none d-md-block divtrasparente">
								<h5>SARAI SEMPRE AL CORRENTE DI TUTTO</h5>
								<p>Noi del team di SocialNotes ci impegneremo per tenerti sempre aggiornato sulle news di qualsiasi università: dalla pubblicazione degli appelli alle notizie che circondano il mondo universitario.</p>
							</div>
						</div>
					</div>
					<a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>



		</div>

		<br>

		<div class="row">
			<div class="col-md-4">
				<div class="card carduni">
					<div class="card-body">

						<div style="float: left; margin-right: 5px;">
							<img src="img/unisa.png" alt="..." width="70" height="70">
						</div>
						<a href="https://www.unisa.it/"><p align=center>Universit&agrave;  degli studi di Salerno</p></a>
					</div>
				</div>
			</div>


			<div class="col-md-4">
				<div class="card carduni">
					<div class="card-body">

						<div style="float: left; margin-right: 5px;">
							<img src="img/unina.jpg" alt="..." width="70" height="70">
						</div>
						<a href="http://www.unina.it/home;jsessionid=C9445FB3F3128D9495102099AE7E1B00.node_publisher11"><p align=center>Universit&agrave;  degli studi di Napoli Federico II</p></a>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="card carduni">
					<div class="card-body">

						<div style="float: left; margin-right: 5px;">
							<img src="img/politecnico.jpeg" alt="..." width="100" height="70">
						</div>
						<a href="https://www.polimi.it/"><p align=center>Politecnico di Milano</p></a>
					</div>
				</div>
			</div>





		</div>
		
			<div class="row">
			<div class="container">
    <div class="mgb-40 padb-30 auto-invert line-b-4 align-center">
        <h4 class="font-cond-l fg-accent lts-md mgb-10" contenteditable="false">Non ti abbiamo ancora convinto?</h4>
        <h1 class="font-cond-b fg-text-d lts-md fs-300 fs-300-xs no-mg" contenteditable="false">Leggi le recensioni dei nostri utenti</h1>
    </div>
    <ul class="hash-list cols-3 cols-1-xs pad-30-all align-center text-sm">
        <li>
          <img src="https://bootdey.com/img/Content/avatar/avatar1.png" class="wpx-100 img-round mgb-20" title="" alt="" data-edit="false" data-editor="field" data-field="src[Image Path]; title[Image Title]; alt[Image Alternate Text]">
          <p class="fs-110 font-cond-l" contenteditable="false">" Ho superato il mio ultimo esame grazie al materiale trovato su questo sito... sarò ben felice di mettere SocialNotes nei ringraziamenti della mia tesi di laurea ;-) "</p>
          <h5 class="font-cond mgb-5 fg-text-d fs-130" contenteditable="false">Pippo Baudo</h5>
          <small class="font-cond case-u lts-sm fs-80 fg-text-l" contenteditable="false">pipp8</small>
        </li>
        </ul>
        <ul class="hash-list cols-3 cols-1-xs pad-30-all align-center text-sm">
        <li>
          <img src="https://bootdey.com/img/Content/avatar/avatar4.png" class="wpx-100 img-round mgb-20" title="" alt="" data-edit="false" data-editor="field" data-field="src[Image Path]; title[Image Title]; alt[Image Alternate Text]">
          <p class="fs-110 font-cond-l" contenteditable="false">" Nel momento di difficoltà dovuto alla pandemia, avete trovato subito la soluzione migliore per aiutarci con gli esami.   "</p>
          <h5 class="font-cond mgb-5 fg-text-d fs-130" contenteditable="false">Leonardo Bonucci</h5>
          <small class="font-cond case-u lts-sm fs-80 fg-text-l" contenteditable="false">lbonucci19</small>
        </li>
        </ul>
       <ul class="hash-list cols-3 cols-1-xs pad-30-all align-center text-sm">
        <li>
          <img src="https://bootdey.com/img/Content/avatar/avatar5.png" class="wpx-100 img-round mgb-20" title="" alt="" data-edit="false" data-editor="field" data-field="src[Image Path]; title[Image Title]; alt[Image Alternate Text]">
          <p class="fs-110 font-cond-l" contenteditable="false">" Team preparato e sempre disponibile, rispondono in maniera veloce e sono sempre pronti a dare una mano..IL MEGLIO! "</p>
          <h5 class="font-cond mgb-5 fg-text-d fs-130" contenteditable="false">Gigi Buffon</h5>
          <small class="font-cond case-u lts-sm fs-80 fg-text-l" contenteditable="false">Gbffuno</small>
        </li>
      </ul>
      </div>
		<br>
		<br>
		<br>
		<br>
		
	</div>
      <div class="row">


		<!-- Services section -->
    <section id="what-we-do">
			<h2 class="section-title mb-2 h1">Unisciti a noi!</h2>
			<p class="text-center text-muted h5">Osserva tutto ciò che ha da offrire SocialNotes. Il prezzo? Solo la tua iscrizione!</p>
			<div class="row mt-5">
				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4 col-xl-4">
					<div class="card">
						<div class="card-block block-1">
							<h3 class="card-title">REGISTRATI</h3>
							<p class="card-text">Inserisci le tue generalità ed entra a far parte del mondo SocialNotes.</p>
							<a href="signup.jsp" title="Read more" class="read-more" >Portami lì<i class="fa fa-angle-double-right ml-2"></i></a>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4 col-xl-4">
					<div class="card">
						<div class="card-block block-2">
							<h3 class="card-title">NAVIGA</h3>
							<p class="card-text">Cerca e sfoglia il materiale di cui hai bisogno.</p>
							<a href="login.jsp" title="Read more" class="read-more" >Portami lì<i class="fa fa-angle-double-right ml-2"></i></a>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4 col-xl-4">
					<div class="card">
						<div class="card-block block-3">
							<h3 class="card-title">ACQUISTA COINS</h3>
							<p class="card-text">Visualizza i nostri pricing e scegli qual'è quello perfetto per te.</p>
							<a href="prezzi.jsp" title="Read more" class="read-more" >Portami lì<i class="fa fa-angle-double-right ml-2"></i></a>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4 col-xl-4">
					<div class="card">
						<div class="card-block block-4">
							<h3 class="card-title">SFOGLIA LE NEWS</h3>
							<p class="card-text">Resta aggiornato sul mondo universitario.</p>
							<a href="news.jsp" title="Read more" class="read-more" >Portami lì<i class="fa fa-angle-double-right ml-2"></i></a>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4 col-xl-4">
					<div class="card">
						<div class="card-block block-5">
							<h3 class="card-title">CERCA AMICI</h3>
							<p class="card-text">Una volta registrato potrai cercare i tuoi amici aggiungerli alla tua lista.</p>
							<a href="login.jsp" title="Read more" class="read-more" >Portami lì<i class="fa fa-angle-double-right ml-2"></i></a>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4 col-xl-4">
					<div class="card">
						<div class="card-block block-6">
							<h3 class="card-title">METTITI IN CONTATTO COL MONDO</h3>
							<p class="card-text">Grazie alla nostra chat potrai scambiare messaggi con chiunque tu voglia.</p>
							<a href="login.jsp" title="Read more" class="read-more" >Portami lì<i class="fa fa-angle-double-right ml-2"></i></a>
						</div>
					</div>
				</div>
			</div>

	</section>
	<!-- /Services section -->	


			

			



		</div>
</div>
		<br>

		




	</div>
	<br>
	<br>

	<%@ include file="footer.jsp"%>



</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
   <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
  

<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha384-tsQFqpEReu7ZLhBV2VZlAu7zcOV+rXbYlF2cqB8txI/8aZajjp4Bqd+V6D5IgvKT" crossorigin="anonymous">
</script>




<script type="text/javascript">
$('svg.radial-progress').each(function( index, value ) { 
  $(this).find($('circle.complete')).removeAttr( 'style' );
});
</script>

<script type="text/javascript">
$(window).scroll(function(){
  $('svg.radial-progress').each(function( index, value ) { 
    // If svg.radial-progress is approximately 25% vertically into the window when scrolling from the top or the bottom
    if ( 
        $(window).scrollTop() > $(this).offset().top - ($(window).height() * 0.75) &&
        $(window).scrollTop() < $(this).offset().top + $(this).height() - ($(window).height() * 0.25)
    ) {
        // Get percentage of progress
        percent = $(value).data('percentage');
        // Get radius of the svg's circle.complete
        radius = $(this).find($('circle.complete')).attr('r');
        // Get circumference (2Ãâ‚¬r)
        circumference = 2 * Math.PI * radius;
        // Get stroke-dashoffset value based on the percentage of the circumference
        strokeDashOffset = circumference - ((percent * circumference) / 100);
        // Transition progress for 1.25 seconds
        $(this).find($('circle.complete')).animate({'stroke-dashoffset': strokeDashOffset}, 1250);
    }
  });
}).trigger('scroll');
</script>
</html>