$(function() {
	var fareSearchComponent = function() {
		var $form = $('#fareSearchForm');
		_initValidations();
		
		function _initValidations() {
			$form.validate({
				submitHandler : function(form) {
					if ($('#fareSearchOrigin').val() === $('#fareSearchDestination').val()) {
						$('#sameOriginDestinationModal').modal('show');
						return false;
					}
					_formSubmitHandler();
				},
				messages : {
					origin : {
						required : "Please select the origin"
					},
					destination : {
						required : "Please select the destination"
					}
				},
				errorClass: 'text-danger'
			});
		}

		function _formSubmitHandler() {
			var $submitButton = $form.find('button[type="submit"]');
			$submitButton.button('loading');
			$.get($(location).attr('pathname'), $form.serialize(),
					function(data) {
						_loadFareTemplate('fare', data);
					}).fail(function(jqXHR, textStatus, errorThrown) {
				console.error('textStatus: %s', textStatus);
				_loadFareTemplate('nofare', {});
			}).always(function() {
				$submitButton.button('reset');
			});
			return false;
		}

		function _loadFareTemplate(templateName, data) {
			$.get('/mustache/' + templateName + '.mst', function(template) {
				var rendered = Mustache.render(template, data);
				$('div.panel-success, div.panel-danger').remove();
				$form.parents('div.panel:first').after(rendered);
			});
		}
	}();

	var fareAutoCompleteComponent = function() {
		var options = {
			autoSelect : true,
			delay : 50,
			displayText : function(item) {
				return item.code + ' - ' + item.name;
			}
		}

		$('#fareSearchOrigin').typeahead($.extend(options, {
			afterSelect : function(item) {
				$('#fareSearchOrigin').val(item.code);
			}
		}));

		$('#fareSearchDestination').typeahead($.extend(options, {
			afterSelect : function(item) {
				$('#fareSearchDestination').val(item.code);
			}
		}));

		$('#fareSearchOrigin, #fareSearchDestination').keyup(function() {
			var $input = $(this);
			$.get('/fares/', "term=" + $(this).val(), function(data) {
				$input.data('typeahead').source = data;
			});
		});
	}();
});
