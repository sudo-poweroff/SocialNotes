<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" href="img/favicon.ico">
<title>Chi Siamo</title>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
 <link rel="stylesheet" type="text/css" href="css/aboutUs.css">
</head>
<body>
<% if(session.getAttribute("username")!=null){
	if ((int)session.getAttribute("role")==1){
		
%>
<jsp:include page="header_admin.jsp"></jsp:include>
<%}else{ %>
<jsp:include page="header_user.jsp"></jsp:include>
<% 
}
}else{
	%>
	<jsp:include page="header.jsp"></jsp:include>
	<% 
}
	
	%>
 

<div id="main-content" class="blog-page">
        <div class="container">
            <div class="row clearfix">
                <div class="col-lg-8 col-md-12 left-box">
                    <div class="card single_post">
                        <div class="body">
                            <div class="img-post">
                                <img class="d-block img-fluid" src="img/aboutUs.png" alt="First slide" width="800px" height="280px">
                            </div>
                            <h3><a href="blog-details.html">SocialNotes</a></h3>
                            <p>SocialNotes nasce nell'estate del 2021, come realizzazione del progetto per l'esame di TECNOLOGIE SOFTWARE PER IL WEB. L’obiettivo è quello di fornire agli studenti universitari una piattaforma sulla quale reperire o caricare materiale di studio per metterlo a disposizione degli altri studenti.Il sito nasce non solo per lo scambio passivo di appunti ma per creareuna piattaforma che garantisca l’interazione tra gli utenti del sito. Tale interazione avviene tramite l’utilizzo di chat individuali o di gruppo, che permettono agli utenti di interagire tra loro.La piattaforma punta ad avere una buona qualità degli appunti, infatti, gli utenti registrati possono ricevere un feedback da coloro che usufruiscono dei loro appunti, questo consente agli utenti che caricano appunti di guadagnare coins,necessariper visualizzare appunti di altri utenti.Ifeedback,invece,permettono alla piattaforma di consigliare appunti migliori ad utenti futuri.Èpossibile usufruire del sito web da qualsiasi dispositivo (smartphone,tablet,pc)essendo strutturato in modo responsive.</p>
                        </div>                        
                    </div>
                    <div class="card">
                            <div class="header">
                                <h2>Team di sviluppo</h2>
                            </div>
                            <div class="body">
                                <ul class="comment-reply list-unstyled">
                                    <li class="row clearfix">
                                        <div class="icon-box col-md-2 col-4"><img class="img-fluid img-thumbnail" src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Awesome Image"></div>
                                        <div class="text-box col-md-10 col-8 p-l-0 p-r0">
                                            <h5 class="m-b-0">Alfonso Califano </h5>
                                            <p>Iscritto all'universita' degli studi di Salerno, facolta' di Informatica. <br> Numero di matricola:  0512109653 </p>                                            
                                        </div>
                                    </li>
                                    <li class="row clearfix">
                                        <div class="icon-box col-md-2 col-4"><img class="img-fluid img-thumbnail" src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Awesome Image"></div>
                                        <div class="text-box col-md-10 col-8 p-l-0 p-r0">
                                            <h5 class="m-b-0">Simone Della Porta </h5>
                                            <p>Iscritto all'universita' degli studi di Salerno, facolta' di Informatica. <br> Numero di matricola: 0512109134 </p>                                          
                                        </div>
                                    </li>
                                     <li class="row clearfix">
                                        <div class="icon-box col-md-2 col-4"><img class="img-fluid img-thumbnail" src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Awesome Image"></div>
                                        <div class="text-box col-md-10 col-8 p-l-0 p-r0">
                                            <h5 class="m-b-0">Francesco di Lauro </h5>
                                            <p>Iscritto all'universita' degli studi di Salerno, facolta' di Informatica. <br> Numero di matricola: 0512106873 </p>
                                            
                                        </div>
                                    </li>
                                     <li class="row clearfix">
                                        <div class="icon-box col-md-2 col-4"><img class="img-fluid img-thumbnail" src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Awesome Image"></div>
                                        <div class="text-box col-md-10 col-8 p-l-0 p-r0">
                                            <h5 class="m-b-0">Armando Caso </h5>
                                            <p>Iscritto all'universita' degli studi di Salerno, facolta' di Informatica. <br> Numero di matricola: 0512106453 </p>
                                            
                                        </div>
                                    </li>
                                </ul>                                        
                            </div>
                        </div>
                        <div class="card">
                            <div class="header">
                                <h2> Vuoi entrare nel team di SocialNotes? <br> <small>Invia una mail nel form in basso in cui spieghi quale ruolo vorresti ricoprire nel Team</small></h2>
                            </div>
                            <div class="body">
                                <div class="comment-form">
                                    <form class="row clearfix">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <input type="text" class="form-control" placeholder="Il tuo nome">
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <input type="text" class="form-control" placeholder="Indirizzo email">
                                            </div>
                                        </div>
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <textarea rows="4" class="form-control no-resize" placeholder="Parlaci di te..."></textarea>
                                            </div>
                                            <button type="submit" class="btn btn-block btn-primary" id="invia">INVIA</button>
                                        </div>                                
                                    </form>
                                </div>
                            </div>
                        </div>
                </div>
                <div class="col-lg-4 col-md-12 right-box">
                    
                    <div class="card">
                        <div class="header">
                            <h2>Cosa e' SocialNotes?</h2>
                        </div>
                        <div class="body widget">
                            <ul class="list-unstyled categories-clouds m-b-0">
                                <li><a href="prezzi.jsp">Un E-Commerce</a></li>
                                <li><a href="news.jsp">Un portale di News</a></li>
                                <li><a href="homepage.jsp">Una biblioteca di materiale scolastico</a></li>
                                <li><a href="chat.jsp">Un Social</a></li>       
                            </ul>
                        </div>
                    </div>
                    <div class="card">
                        <div class="header">
                            <h2>Storia di SocialNotes</h2>                        
                        </div>
                        <div class="body widget popular-post">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="single_post">
                                        
                                        <span>8 Aprile 2021</span>
                                        <div class="img-post">
                                            <img src="img/proposal.png" alt="Proposal">
                                            <p class="m-b-0">SocialNotes viene proposto al prof. Michele Risi.</p>                                        
                                        </div>                                            
                                    </div>
                                    <div class="single_post">
                                        
                                        <span>10 Settembre 2021</span>
                                        <div class="img-post">
                                            <img src="img/progetto.png" alt="Progetto"> 
                                            <p class="m-b-0">SocialNotes viene ultimato e presentato alla discussione del progetto.</p>                                           
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card">
                        <div class="header">
                            <h2>Visita il nostro profilo Instagram</h2>
                        </div>
                        <div class="body widget">
                            <ul class="list-unstyled instagram-plugin m-b-0">
                                <li><a href="https://www.instagram.com/p/BxNSNFaHk_2/?utm_medium=copy_link"><img src="img/study.png" alt="" width="100px" height="100px"></a></li>
                                <li><a href="https://www.instagram.com/p/BxNSKC9Hc-W/?utm_medium=copy_link"><img src="img/like.png" alt="" width="100px" height="100px"></a></li>
                                <li><a href="https://www.instagram.com/p/BxNSAhIHcJl/?utm_medium=copy_link"><img src="img/hero.png" alt="" width="100px" height="100px"></a></li>
                            </ul>
                        </div>
                    </div>
                   
                </div>
            </div>

        </div>
    </div>
 
 <%@include file="footer.jsp" %>
</body>
</html>