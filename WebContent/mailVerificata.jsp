<%--
  Created by IntelliJ IDEA.
  User: rocco
  Date: 29/09/2023
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>SocialNotes: Mail verificata</title>
    <link href="https://fonts.googleapis.com/css?family=Nunito+Sans:400,400i,700,900&display=swap" rel="stylesheet">
    <link rel="icon" href="img/favicon.ico">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <style>
        body {
            text-align: center;
            padding: 40px 0;
            background: #f8f9fa;
        }

        h1 {
            color: #88B04B;
            font-family: "Nunito Sans", "Helvetica Neue", sans-serif;
            font-weight: 900;
            font-size: 40px;
            margin-bottom: 10px;
        }

        p {
            color: #404F5E;
            font-family: "Nunito Sans", "Helvetica Neue", sans-serif;
            font-size: 20px;
            margin: 0;
        }

        i {
            color: #9ABC66;
            font-size: 100px;
            line-height: 200px;
            margin-left: -15px;
        }

        .card {
            background: white;
            padding: 60px;
            border-radius: 4px;
            box-shadow: 0 2px 3px #C8D0D8;
            display: inline-block;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<%
    System.out.println(session.getAttribute("accessNumber"));
    if (session.getAttribute("accessNumber")==null) {
        String toHomepage = response.encodeRedirectURL("homepage.jsp");
        response.sendRedirect(toHomepage);
    }
    else{
        int number = (int) session.getAttribute("accessNumber");
        if (number!=0 && number!=1) {
            String toHomepage = response.encodeRedirectURL("homepage.jsp");
            response.sendRedirect(toHomepage);
        }

        String linkHomepage= "homepage.jsp";
        String homepageURL = response.encodeURL(linkHomepage);
        String linkLogin= "login.jsp";
        String loginURL = response.encodeURL(linkLogin);
%>
        <div class="card">
            <div style="border-radius: 200px; height: 200px; width: 200px; background: #F8FAF5; margin: 0 auto;">
                <i>&#10003;</i>
            </div>
            <h1>Mail verificata!</h1>
            <%
                if (number==0){
            %>
                    <p>
                        Dopo aver effettuato il login potrai inserire le tue materie di interesse.
                    </p>
            <%
                }
            %>
            <br>
            <button class="btn btn-principale btn-lg" onclick="window.location.href='<%=loginURL%>'">Effettua il login</button>
            <button class="btn btn-principale btn-lg" onclick="window.location.href='<%=homepageURL%>'">Vai alla Home</button>
        </div>
<%
    }
%>
</body>
</html>
