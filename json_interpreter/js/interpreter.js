	var total_sum = 0;
	var avg = 0;
$(document).ready(function(){
	refresh();
});


refresh = function(){
	total_sum=0;
	avg=0;

	var freq_pos = 0;
	var freq_neg = 0;
	var freq_neut = 0;

	$.getJSON('output.json', function(response_json){

			$.each(response_json.data, function(i, item){
				total_sum += item.polarity;
				if(item.polarity == 0){
					freq_neg ++;
					freq_neg ++;
				}
				if(item.polarity == 1){
					freq_neg ++;
				}
				if(item.polarity == 2){
					freq_neut++;
				}
				if(item.polarity == 3){
					freq_pos++;
				}
				if(item.polarity == 4){
					freq_pos++;
					freq_pos++;
				}

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


			var pieData = [
				{
					value : freq_pos,
					color : "#4ACAB4",
					label : "Positive"
				},
				{
					value : freq_neut,
					color : "#FFC870",
					label : "Neutral"
				},
				{
					value : freq_neg,
					color : "#FF8153",
					label : "Negative"
				}
			];
			var pieOptions = {
				segmentShowStroke : false,
				animateScale : true
			}


			new Chart(ctx).Pie(pieData, pieOptions);

	});
	return;
}