window.setTimeout(function() {
    $("#generalAlert").fadeTo(400, 0).slideUp(400, function(){
        $(this).remove(); 
    });
}, 1300);


$('#confirmDeleteModal').on('show.bs.modal', function (event){
	
	var button = $(event.relatedTarget)
	var titleId = button.data('id')
	var titleDescription = button.data('description')
	
	var modal = $(this)
	var form = modal.find('form')
	var url = form.data('url-base') + '/' + titleId
	form.attr('action', url)
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o título <strong>' + titleDescription + '</strong>?')
	
	/*
	$('#btnConfirmDelete').click((event) => {
		event.preventDefault()
		
		const response = 
			$.ajax({
				url: url,
				method: "DELETE"
			})
		
		response.done((data) => {
			//window.location.reload();
		})
		
		response.fail((event) => {
			console.error(event)
		})
	})
	*/
});

$(function () {
  
  $('[rel="tooltip"]').tooltip()
  $('.js-currency').maskMoney({thousands:'.', decimal:',', allowZero:true, numeralMaxLength:true})
  
  $('.js-update-status').click((event) => {
	  event.preventDefault()
	  
	  const buttonReceive = $(event.currentTarget)
	  const receiveUrl = buttonReceive.attr('href')
	  
	  const response = 
		  $.ajax({
				url: receiveUrl,
				method: "PUT",
				async: true
			})
	  response.done((data) => {
		  var titleId = buttonReceive.data('id')
		  $('[data-role=' + titleId + ']').html('<span class="badge badge-success">' + data + '</span>')
		  buttonReceive.hide()
	  })
	  response.fail((event) => {
		  console.error(event)
	  })
			
  })
})