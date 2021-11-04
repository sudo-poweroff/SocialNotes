<%@page import="it.unisa.model.FriendsModelDS"%>
<%@page import="it.unisa.model.UserModelDS"%>
<%@page import="it.unisa.model.ChatModelDS"%>
<%@page import="it.unisa.model.ParticipationModelDS"%>
<%@page import="it.unisa.model.ParticipationBean"%>
<%@page import="it.unisa.model.ChatBean"%>
<%@page import="it.unisa.model.FriendsBean"%>
<%@page import="it.unisa.model.UserBean"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="javax.sql.DataSource"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Chat - SocialNotes</title>
<link rel="icon" href="img/favicon.ico">   

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

<link href="css/chat.css" rel="stylesheet">

<style>
/* use reverse flexbox to take advantage of flex-direction: reverse */
.star-rating {
  display: flex;
  align-items: center;
  width: 160px;
  flex-direction: row-reverse;
  justify-content: space-between;
  position: relative;
}
/* hide the inputs */
.star-rating input {
  display: none;
}
/* set properties of all labels */
.star-rating > label {
  width: 30px;
  height: 30px;
  font-family: Arial;
  font-size: 30px;
  transition: 0.2s ease;
  color: orange;
}
/* give label a hover state */
.star-rating label:hover {
  color: #9697e7;
  transition: 0.2s ease;
}
.star-rating label:active::before {
  transform:scale(1.1);
}

/* set shape of unselected label */
.star-rating label::before {
  content: '\2606';
  position: absolute;
  top: 0px;
  line-height: 26px;
}
/* set full star shape for checked label and those that come after it */
.star-rating input:checked ~ label:before {
  content:'\2605';
}

@-moz-document url-prefix() {
  .star-rating input:checked ~ label:before {
  font-size: 36px;
  line-height: 21px;
  }
}  
</style>

</head>
<body>
<%
String adminURL = "admin.jsp";
String creaChatURL = "creaChat.jsp";

   if(session.getAttribute("username")==null){
	 
		  response.sendRedirect("login.jsp");
	     //return;
	   %>
	  
	
	  
	  <%
   }else{
	   if (((int)session.getAttribute("role"))==1){
			  adminURL = response.encodeURL(adminURL);
			  response.sendRedirect(adminURL);
			  return;
		  }
	   
	   creaChatURL = response.encodeURL(creaChatURL);
	   %>
	   <jsp:include page="header_user.jsp"></jsp:include>
	   <% }

%>
<br><br><br>
<div class="container">

    <!-- Page header start -->
    <div class="page-title">
        <div class="row gutters">
            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
                <h5 class="title">Chat</h5>
            </div>
            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12"> </div>
        </div>
    </div>
    <!-- Page header end -->

    <!-- Content wrapper start -->
    <div class="content-wrapper">

        <!-- Row start -->
        <div class="row gutters">

            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">

                <div class="card m-0">

                    <!-- Row start -->
                    <div class="row no-gutters">
                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-3 col-3">
                            <div class="users-container">
                                <div class="chat-search-box">
                                    <a href="<%=creaChatURL %>" style="color:#9697e7">Crea Nuova Chat <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-square-dotted" viewBox="0 0 16 16">
  <path d="M2.5 0c-.166 0-.33.016-.487.048l.194.98A1.51 1.51 0 0 1 2.5 1h.458V0H2.5zm2.292 0h-.917v1h.917V0zm1.833 0h-.917v1h.917V0zm1.833 0h-.916v1h.916V0zm1.834 0h-.917v1h.917V0zm1.833 0h-.917v1h.917V0zM13.5 0h-.458v1h.458c.1 0 .199.01.293.029l.194-.981A2.51 2.51 0 0 0 13.5 0zm2.079 1.11a2.511 2.511 0 0 0-.69-.689l-.556.831c.164.11.305.251.415.415l.83-.556zM1.11.421a2.511 2.511 0 0 0-.689.69l.831.556c.11-.164.251-.305.415-.415L1.11.422zM16 2.5c0-.166-.016-.33-.048-.487l-.98.194c.018.094.028.192.028.293v.458h1V2.5zM.048 2.013A2.51 2.51 0 0 0 0 2.5v.458h1V2.5c0-.1.01-.199.029-.293l-.981-.194zM0 3.875v.917h1v-.917H0zm16 .917v-.917h-1v.917h1zM0 5.708v.917h1v-.917H0zm16 .917v-.917h-1v.917h1zM0 7.542v.916h1v-.916H0zm15 .916h1v-.916h-1v.916zM0 9.375v.917h1v-.917H0zm16 .917v-.917h-1v.917h1zm-16 .916v.917h1v-.917H0zm16 .917v-.917h-1v.917h1zm-16 .917v.458c0 .166.016.33.048.487l.98-.194A1.51 1.51 0 0 1 1 13.5v-.458H0zm16 .458v-.458h-1v.458c0 .1-.01.199-.029.293l.981.194c.032-.158.048-.32.048-.487zM.421 14.89c.183.272.417.506.69.689l.556-.831a1.51 1.51 0 0 1-.415-.415l-.83.556zm14.469.689c.272-.183.506-.417.689-.69l-.831-.556c-.11.164-.251.305-.415.415l.556.83zm-12.877.373c.158.032.32.048.487.048h.458v-1H2.5c-.1 0-.199-.01-.293-.029l-.194.981zM13.5 16c.166 0 .33-.016.487-.048l-.194-.98A1.51 1.51 0 0 1 13.5 15h-.458v1h.458zm-9.625 0h.917v-1h-.917v1zm1.833 0h.917v-1h-.917v1zm1.834-1v1h.916v-1h-.916zm1.833 1h.917v-1h-.917v1zm1.833 0h.917v-1h-.917v1zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z"/>
</svg></a>
                                </div>
                                <ul class="users">
                                
                                <%
                                    
                                DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
                        		ChatModelDS chatModel = new ChatModelDS(ds);
                        		ParticipationModelDS pModel = new ParticipationModelDS(ds);
                        		
                        		Collection<ChatBean> chats = chatModel.doRetrieveChatName((String)session.getAttribute("username"));
                        		
                        		
                        		if(chats!=null){
                        			Iterator<?> it= chats.iterator();
                        		while(it.hasNext()){
                        			ChatBean item = (ChatBean)it.next();
                        		
                                
                                %>
                                    <li class="person" id="testo" onclick="funzione3(<%=item.getChatID() %>,'<%=item.getTitolo() %>')"  id ="<%=item.getChatID() %>" data-chat="person1" id=<%=item.getChatID() %>>
                                        <div class="user">
                                            <img src="img/MicrosoftTeams-image.png"  alt="Retail Admin">
                                            <span class="status busy"></span>
                                        </div>
                                        <p class="name-time" >
                                            <span class="name" onload="funzione3(<%=item.getChatID() %>,'<%=item.getTitolo() %>')"><%=item.getTitolo() %></span>
                                      
                                        </p>
                                    </li>
                                  <%}} %>
                                </ul>
                            </div>
                        </div>
                        <div class="col-xl-8 col-lg-8 col-md-8 col-sm-9 col-9" >
                            <div class="selected-user">
                                <span>To: <span class="name" id="titolo"></span></span>
                            </div>
                            <div class="chat-container" id="divchat">
                               <ul class="chat-box chatContainerScroll" id="listchat">
                               </ul>
                                <div class="form-group mt-3 mb-0">
                                <form id="form1" name="form1" method="GET" >
                                <!--     <input type="text"  id="chatcode" class="name" value="9"> -->
                                    <input type="text" name="username" id="username" hidden value="<%=session.getAttribute("username") %>">
                                    <textarea class="form-control" rows="2" placeholder="Type your message here..." id="mex" name="mex" required></textarea> 
									<br>
                                    <button class="btn btn-light" name="bottone" onclick="funzione()" id="bottone" type="submit" style="background-color : #9697e7; color:white;">Invia il messaggio</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Row end -->
                </div>

            </div>

        </div>
        <!-- Row end -->
	
    </div>
    <!-- Content wrapper end -->
	<br><br><br><br><br><br><br><br><br><br><br><br>
</div>
<br><br><br>
<%@ include file="footer.jsp" %>
</body>
 <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
   
<script src="js/chat.js"></script>
</html>