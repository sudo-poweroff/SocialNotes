<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SocialNotes-Segnalazioni Effettuate</title>
<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/segnalazioni_eff.css" />
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<h2><b>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;SEGNALAZIONI EFFETTUATE</b></h2>



<div class="page-content page-container" id="page-content">
    <div class="padding">
        <div class="row">
            <div class="col-sm-8">
            
                <div class="container-fluid d-flex justify-content-center">
                    <div class="list list-row card" id="sortable" data-sortable-id="0" aria-dropeffect="move">
                        <div class="list-item" data-id="13" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false" style="">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-primary">P</span></a></div>
                            <div class="flex"> <a  class="item-author text-color" data-abc="true">fonzobit</a>
                                <div class="item-except text-muted text-sm h-1x">Segnalato per essere troppo forte e per essere comunista</div>
                                <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                            
                            
                        </div>
                        <div class="list-item" data-id="9" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-info"><img src="https://img.icons8.com/bubbles/24/000000/user.png" alt="."></span></a></div>
                            <div class="flex"> <a class="item-author text-color" data-abc="true">Steven Hmpire</a>
                                <div class="item-except text-muted text-sm h-1x">Build a progressive web app using jQuery</div>
                                <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                           
                        </div>
                        <div class="list-item" data-id="17" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false" style="">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-warning">A</span></a></div>
                            <div class="flex"> <a  class="item-author text-color" data-abc="true">Alan musk</a>
                                <div class="item-except text-muted text-sm h-1x">it be advisable for me to think about business content?</div>
                                <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                           
                            <div>
                                
                            </div>
                        </div>
                        <div class="list-item" data-id="8" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-success"><img src="https://img.icons8.com/doodle/24/000000/user-male.png" alt="."></span></a></div>
                            <div class="flex"> <a class="item-author text-color" data-abc="true">Lawrence Telon</a>
                                <div class="item-except text-muted text-sm h-1x">For what reason would it be advisable for me to think</div>
                                <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                            
                            <div>
                              
                            </div>
                        </div>
                        <div class="list-item" data-id="10" data-item-sortable-id="0" draggable="true" role="option" aria-grabbed="false" style="">
                            <div><a  data-abc="true"><span class="w-40 avatar gd-danger"><img src="https://img.icons8.com/color/16/000000/administrator-male.png" alt="."></span></a></div>
                            <div class="flex"> <a  class="item-author text-color" data-abc="true">Stuart Clark</a>
                                <div class="item-except text-muted text-sm h-1x">For what reason would, i think about business content?&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</div>
                                <a href="javascript:void(0);" id="archivia" class="btn btn-success">Archivia</a>
                            </div>
                            
                            <div>
                               
                            </div>
                        </div>
                       
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<br>
<%@ include file="footer.jsp"%>
</body>
</html>