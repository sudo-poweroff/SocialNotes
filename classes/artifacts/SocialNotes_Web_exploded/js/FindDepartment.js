

$(document).ready(function(){
	$("#uni").blur(function(){
		var den = $("#uni").val();
		
		$.ajax({
	type : "GET", 
	url : "FindDepartmentAJAX",
	data: "denominazione=" + den,
	success : function(data){
		let departments = JSON.parse(data);
		var select = document.getElementById("corso");
		var i=0;
		var options = select.getElementsByTagName('option');
		
		for (var j=options.length; j--;){
			select.removeChild(options[j]);
		}
		
		
		
		for (var dp of departments) {
         var opt = document.createElement('option');
           document.getElementById('corso').options[i]=new Option('');
           document.getElementById('corso').options[i].innerHTML = dp["nome"];
           document.getElementById('corso').options[i].value = dp["nome"];
          
           
           i++;
}
		
		
	},
	error: function(){
		console.log("errore");
	}
	
})

		
	})
})



