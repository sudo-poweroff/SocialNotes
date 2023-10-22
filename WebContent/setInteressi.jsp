<%@ page import="javax.sql.DataSource" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="materiale.CourseModelDS" %>
<%@ page import="materiale.CourseBean" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">
    <title>Interessi</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="css/login.css">
</head>

<body class="text-center">
<%
    if (session.getAttribute("username")==null)
        response.sendRedirect("login.jsp");
    else if(session.getAttribute("accessNumber")!=null && (int)session.getAttribute("accessNumber")!=0) {
        session.removeAttribute("accessNumber");
        response.sendRedirect("homepage_user.jsp");
    }
    else if (session.getAttribute("accessNumber")==null)
        response.sendRedirect("homepage_user.jsp");


    DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
    String errore = (String) request.getAttribute("error");
    if (errore!=null){
%>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Attenzione!</strong> <small><%=errore%></small>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
<%
    }
%>
<h1 class="h3 mb-3 font-weight-normal">Inserisci i tuoi interessi</h1>
<div class="button-container">
    <%
        CourseModelDS courseModel=new CourseModelDS(ds);
        ArrayList<CourseBean> courses=courseModel.doRetrieveAll();
        if(courses!=null&&courses.size()>0){
            Iterator<?> it= courses.iterator();
            while(it.hasNext()){
                CourseBean bean= (CourseBean) it.next();
    %>
                <button class="custom-button" id="<%=bean.getCodiceCorso()%>">
                    <div class="button-content">
                        <span><%=bean.getNome()%></span>
                    </div>
                </button>
    <%
            }
        }
    %>

</div>

<div class="bottom-buttons">
    <button class="square-button white-button" id="skipButton">Skip</button>
    <button class="square-button black-button" id="avantiButton">Avanti</button>
</div>

<script>
    const buttons = document.querySelectorAll(".custom-button");
    const interessiSelezionati = [];

    buttons.forEach(button => {
        button.addEventListener("click", function () {
            button.classList.toggle("selected");
            if (button.classList.contains("selected")) {
                // Cambia il colore dello sfondo e del testo quando selezionato
                button.style.backgroundColor = "#E6AF2A";
                button.style.color = "#fff";
                interessiSelezionati.push(button.id);
            } else {
                // Torna allo stile iniziale quando deselezionato
                button.style.backgroundColor = "#fff";
                button.style.color = "#000";
                const idCorso = button.id;
                const index = interessiSelezionati.indexOf(idCorso);
                if (index !== -1) {
                    interessiSelezionati.splice(index, 1);
                }
            }
            console.log("Corsi selezionati: ",interessiSelezionati)
        });
    });
    const skipButton = document.getElementById("skipButton");
    const avantiButton = document.getElementById("avantiButton");

    avantiButton.addEventListener("click", function () {
        fetch('SetInteressi', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(interessiSelezionati),
        })
        .then(response => response.json()) // Parse the JSON response
        .then((data) => {
            if (data.messaggio!="") {
                alert(data.messaggio);
            }
            else {
                window.location.href = "homepage_user.jsp";
            }
        });
    });

    skipButton.addEventListener("click", function () {
        window.location.href = "homepage.jsp";
    });


</script>

<style>
    .custom-button {
        background-color: #fff;
        color: #000;
        border: none;
        border-radius: 8px;
        padding: 10px 20px;
        margin: 8px;
        cursor: pointer;
        transition: background-color 0.2s, color 0.2s, transform 0.2s, box-shadow 0.2s;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        max-width: 33.33%;
    }

    .square-button {
        width: calc(25% - 16px); /* 25% della larghezza della finestra, sottraendo il margine */
        height: 80px; /* Altezza fissa di 20px */
        padding: 0;
    }

    .white-button {
        background-color: #fff;
        border: 1px solid #000;
        color: #000;
        margin-right: 16px;
    }

    .black-button {
        background-color: #000;
        border: 1px solid #000;
        color: #fff;
        margin-left: 16px;
    }

    .bottom-buttons {
        display: flex;
        justify-content: center; /* Allinea i pulsanti al centro orizzontalmente */
        position: fixed;
        bottom: 24px; /* Sposta i pulsanti verso l'alto di 24px dal fondo */
        width: 100%;
    }

    .button-container {
        max-width: calc(80% - 16px); /* Massima larghezza del contenitore (scheda divisa per 3, sottraendo il margine) */
        margin: 0 auto; /* Centra il contenitore orizzontalmente */
    }

    body {
        margin-top: 24px;
    }
</style>

<!-- Font Awesome per le icone (assicurati di aggiungere la libreria appropriata) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>
