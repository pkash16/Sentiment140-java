	var total_sum = 0;
	var avg = 0;
$(document).ready(function(){
	refresh();
});


refresh = function(){
	total_sum=0;
	avg=0;
	$.getJSON('output.json', function(response_json){

			$.each(response_json.data, function(i, item){
				total_sum += item.polarity;
			});

			avg = total_sum/response_json.data.length;
			$('#avg').text("Average Polarity: " + avg + ", sample size: " + response_json.data.length);
			var textual_feedback;
			if(avg < 2 && avg > 1){
				textual_feedback='slightly negative';
			}
			else if(avg == 1){
				textual_feedback='negative';
			}
			else if(avg < 1){
				textual_feedback='very negative'
			}
			else if(avg == 2){
				textual_feedback='neutral';
			}
			else if(avg > 2 && avg < 3){
				textual_feedback='slightly positive';
			}
			else if(avg == 3){
				textual_feedback='positive';
			}
			else if(avg > 3){
				textual_feedback='very positive';
			}
			else{
				textual_feedback='idk';
			}
			$('#textual_feedback').text(textual_feedback);
			var ctx = $('#testChart').get(0).getContext("2d");


var chartData = {
	labels : ["January","February","March","April","May","June"],
	datasets : [
		{
			fillColor : "rgba(172,194,132,0.4)",
			strokeColor : "#ACC26D",
			pointColor : "#fff",
			pointStrokeColor : "#9DB86D",
			data : [203,156,99,251,305,247]
		}
	]
}


			new Chart(ctx).Line(chartData);

	});
	return;
}