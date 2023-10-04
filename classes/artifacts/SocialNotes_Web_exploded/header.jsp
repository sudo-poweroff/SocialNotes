
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>


<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
 

<style type="text/css">
@import url(css/navbar.css);
</style>

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark colore-principale justify-content-end">
        <a class="navbar-brand" href="homepage.jsp" ><img src="img/LogoDefinitivo1.png" height="25" width="150"></a>
        <button class="navbar-toggler my-1" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end float-right" id="navbarText">
          <form class="form-inline " id="form1" style="padding-bottom:5px;" action="SearchServlet" >
            <input class="form-control mr-sm-2"  type="search" id="ricerca" name="ricerca" placeholder="Cerca Materiale" aria-label="Search">
            <button class="btn btn-dark btn-md my-2 my-sm-0 "  type="submit"><i class="fas fa-search"></i>Cerca</button>
          </form>

          <!-- Magico-->
          <div class ="ml-auto mr-3">

            <ul class="navbar-nav mr-auto">
              <li class="nav-item active">
                <a class="nav-link" href="homepage.jsp">Home <span class="sr-only">(current)</span></a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="news.jsp">News</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="prezzi.jsp">Pricing</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="login.jsp">Accedi</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="signup.jsp">Registrati</a>
              </li>
            </ul>

          </div>

        </div>

      </nav>

</body>

   <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</html>