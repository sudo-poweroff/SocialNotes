
/*$(document).ready(function(){
    $("#testo").click(function(){
    $("#messaggio1").remove();
         $("#messaggio2").remove();
    $("#listchat").append("<li class='chat-right' id='messaggio1'> <div class='chat-hour'>08:56 <span class='fa fa-check-circle'></span></div> <div class='chat-text'>Hi, Russell <br> I need more information about Developer Plan.</div> <div class='chat-avatar'> <img src='https://www.bootdey.com/img/Content/avatar/avatar3.png' alt='Retail Admin'> <div class='chat-name'>Sam</div> </div> </li> <li class='chat-left' id='messaggio2'> <div class='chat-avatar'> <img src='https://www.bootdey.com/img/Content/avatar/avatar3.png' alt='Retail Admin'> <div class='chat-name'>Russell</div> </div> <div class='chat-text'>Are we meeting today? <br>Project has been already finished and I have results to show you.</div> <div class='chat-hour'>08:57 <span class='fa fa-check-circle'></span></div> </li> ");
      });

    
});*/


/*$(document).ready(function(){

$("#bottone").click(function(){
   var mess =  $("#mex").val();
   
   var chat = $("#chatcode").val();
console.log(chat);
   var user = $("#username").val();
   $.ajax({
   type:"POST",
	url: "InsertMessage",
   data: {mex: mess, chatID: chat, username: user},
   success: function(data){
   console.log("OTTIMO");
 },
   error: function(){
		console.log("errore");
	}
})
})
})  */

var chatID;
var date;
function funzione(){
	event.preventDefault();
	 var mess =  document.getElementById("mex").value;
  
   //	alert(chat);


   var user = document.getElementById("username").value;
   $.ajax({
   type:"POST",
	url: "InsertMessage",
   data: {mex: mess, chatID: chatID, username: user},
    mess: "mex=" + mess,
    chat: "chatID="+ chatID,
    username: "username="+ user,
   success: function(){
//	alert("Sul cesso");

  //  $("#listchat").append("<li class='chat-right' id='messaggio1'> <div class='chat-hour'>08:56 <span class='fa fa-check-circle'></span></div> <div class='chat-text'>Hi, Russell <br> I need more information about Developer Plan.</div> <div class='chat-avatar'> <img src='https://www.bootdey.com/img/Content/avatar/avatar3.png' alt='Retail Admin'> <div class='chat-name'>Sam</div> </div> </li> <li class='chat-left' id='messaggio2'> <div class='chat-avatar'> <img src='https://www.bootdey.com/img/Content/avatar/avatar3.png' alt='Retail Admin'> <div class='chat-name'>Russell</div> </div> <div class='chat-text'>Are we meeting today? <br>Project has been already finished and I have results to show you.</div> <div class='chat-hour'>08:57 <span class='fa fa-check-circle'></span></div> </li> ");
 },
   error: function(){
		alert("Seleziona prima la chat.");
	}
	
});

}


function funzione3(parameter,parameter2){
	   var chat = parameter;
       var titolo = parameter2;
        chatID = chat;
      var user = document.getElementById("username").value;
     $.ajax({
   type:"POST",
	url: "RetrieveMessages",
   data: "chatid=" + chatID,
   success: function(data){
	let messaggi = JSON.parse(data);

      document.getElementById("titolo").innerHTML = titolo; 
    		  $("li").remove(".chat-right");
        $("li").remove(".chat-left");
   for (var mex of messaggi){
	if (mex["username"]==user)
	 $("#listchat").append("<li class='chat-right' id='messaggio1'> <div class='chat-hour'>"+mex["dataInvio"]+" <span class='fa fa-check-circle'></span></div> <div class='chat-text'>"+mex["testo"]+"</div> <div class='chat-avatar'> <img src='PrintImage?username="+mex["username"]+"' alt='Retail Admin'> <div class='chat-name'>"+mex["username"]+"</div> </div> </li>");
       else
 $("#listchat").append("<li class='chat-left' id='messaggio2'> <div class='chat-avatar'> <img src='PrintImage?username="+mex["username"]+"' alt='Retail Admin'> <div class='chat-name'>"+mex["username"]+"</div> </div> <div class='chat-text'>"+mex["testo"]+"</div> <div class='chat-hour'>"+mex["dataInvio"]+" <span class='fa fa-check-circle'></span></div> </li> ");

 date=mex["dataInvio"];
}
},
   error: function(){
	
	}
	
});

	   }
/*
function funzione2(){
	//alert(chat);
	    //$("#messaggio1").remove();
         //$("#messaggio2").remove();
    $("#listchat").append("<li class='chat-right' id='messaggio1'> <div class='chat-hour'>08:56 <span class='fa fa-check-circle'></span></div> <div class='chat-text'>Hi, Russell <br> I need more information about Developer Plan.</div> <div class='chat-avatar'> <img src='https://www.bootdey.com/img/Content/avatar/avatar3.png' alt='Retail Admin'> <div class='chat-name'>Sam</div> </div> </li> <li class='chat-left' id='messaggio2'> <div class='chat-avatar'> <img src='https://www.bootdey.com/img/Content/avatar/avatar3.png' alt='Retail Admin'> <div class='chat-name'>Russell</div> </div> <div class='chat-text'>Are we meeting today? <br>Project has been already finished and I have results to show you.</div> <div class='chat-hour'>08:57 <span class='fa fa-check-circle'></span></div> </li> ");
} 
*/
 function RicevimentoMessaggi(){
      var user = document.getElementById("username").value;
   var dateM = Date.parse(date);
     $.ajax({
   type:"POST",
	url: "RetrieveLatestMessages",
   data: {chatID: chatID, datamessaggio: dateM},
   success: function(data){
	let messaggi = JSON.parse(data);
	
   for (var mex of messaggi){
	if (mex["username"]==user)
	 $("#listchat").append("<li class='chat-right' id='messaggio1'> <div class='chat-hour'>"+mex["dataInvio"]+" <span class='fa fa-check-circle'></span></div> <div class='chat-text'>"+mex["testo"]+"</div> <div class='chat-avatar'> <img src='PrintImage?username="+mex["username"]+"' alt='Retail Admin'> <div class='chat-name'>"+mex["username"]+"</div> </div> </li>");
       else
 $("#listchat").append("<li class='chat-left' id='messaggio2'> <div class='chat-avatar'> <img src='PrintImage?username="+mex["username"]+"' alt='Retail Admin'> <div class='chat-name'>"+mex["username"]+"</div> </div> <div class='chat-text'>"+mex["testo"]+"</div> <div class='chat-hour'>"+mex["dataInvio"]+" <span class='fa fa-check-circle'></span></div> </li> ");

 date=mex["dataInvio"];
}
 

},
   error: function(){
		
	}
	
});

	   }



$(document).ready(function() {
  $.ajaxSetup({ cache: false }); // This part addresses an IE bug.  without it, IE will only load the first number and will never refresh


 setInterval(function() {
	var stringa = "chat.jsp #"+chatID;
	if (chatID!=null){ 
			          		
			//  	    $("li").remove(".chat-right");
       //  $("li").remove(".chat-left");
    $('#listchat').load(RicevimentoMessaggi());

}
  }, 1000); // the "3000" 

});


$("#bottone").click(function(){
	$("#mex").val("");
})


