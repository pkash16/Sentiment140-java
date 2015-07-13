	var total_sum = 0;
	var avg = 0;
$(document).ready(function(){
	refresh();
	$('#button').click(function(){
		refresh();
	});

});


refresh = function(){
	total_sum=0;
	avg=0;
	$.getJSON('output.json', function(response_json){

			$.each(response_json.data, function(i, item){
				total_sum += item.polarity;
			});

			avg = total_sum/response_json.data.length;
			$('#avg').text("Average Polarity: " + avg);
			var textual_feedback;
			if(avg < 2){
				textual_feedback='negative';
			}if(avg == 2){
				textual_feedback='neutral';
			}if(avg > 2){
				textual_feedback='positive';
			}
			$('#textual_feedback').text(textual_feedback);


	});
	return;
}