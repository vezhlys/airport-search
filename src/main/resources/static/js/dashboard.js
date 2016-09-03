$(function () {
	$.get('/metrics', function(data) {
		var counters = [],
			responses = [],
			counterPrefix = 'counter.airports_search.metrics.',
			responsesPrefix = 'responses.airports_search.metrics.';
		for (var property in data) {
		    if (data.hasOwnProperty(property)) {
		        if (property.indexOf(counterPrefix) > -1) {
		        	counters.push({name: property.replace(counterPrefix, ''), value: data[property]});
		        }
		        if (property.indexOf(responsesPrefix) > -1) {
		        	responses.push({name: property.replace(responsesPrefix, ''), value: data[property]});
		        }
		    }
		}
		$.get('/mustache/dashboard.mst', function(template) {
			var rendered = Mustache.render(template, {counters: counters, responses: responses});
			$('div.span12').remove();
			$('div.page-header').after(rendered);
		});
		
	});
});
