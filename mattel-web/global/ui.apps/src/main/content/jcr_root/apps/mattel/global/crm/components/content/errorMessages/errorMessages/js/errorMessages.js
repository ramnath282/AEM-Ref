$(document).ready(function() {
$.ajax({
        type: 'GET',    
        url:'/bin/getErrorMessages',
        success: function(data){
			console.log('data');
        }
    });
});