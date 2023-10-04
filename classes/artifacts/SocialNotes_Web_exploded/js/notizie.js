function readURL(input) {
  if (input.files && input.files[0]) {
	
	    var fileInput = 
            document.getElementById('picture');
        var filePath = fileInput.value;
      
        // Allowing file type
        var allowedExtensions = 
/(\.jpeg|\.png|\.jpg|\.PNG)$/i;
          
        if (!allowedExtensions.exec(filePath)) {
            alert('Tipo del file non valido');
            fileInput.value = '';
            return false;
        } 
	
    var reader = new FileReader();

    reader.onload = function(e) {
      $('.image-upload-wrap').hide();

      $('.file-upload-image').attr('src', e.target.result);
      $('.file-upload-content').show();

      $('.image-title').html(input.files[0].name);
    };

    reader.readAsDataURL(input.files[0]);

  } else {
    removeUpload();
  }
  
}

function removeUpload() {
  $('.file-upload-input').replaceWith($('.file-upload-input').clone());
  $('.file-upload-content').hide();
  $('.image-upload-wrap').show();
}
$('.image-upload-wrap').bind('dragover', function () {
        $('.image-upload-wrap').addClass('image-dropping');
    });
    $('.image-upload-wrap').bind('dragleave', function () {
        $('.image-upload-wrap').removeClass('image-dropping');
});
