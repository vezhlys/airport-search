$(function() {
	var airportsSearchComponent = function() {
		var $airportsTable = $('#airportsTable'), $findAirportsInput = $('#findAirports'), $searchForm = $('#airportsTableSearchForm');
		$findAirportsButton = $('#findAirports').next('button.btn-primary'),
				$paginationElem = $('#pagination');

		_initBinders();
		_getAirports("", 1);

		function _initBinders() {
			$searchForm.off('submit');
			$searchForm.on('submit', function() {
				$findAirportsButton.button('loading');
				_getAirports($findAirportsInput.val());
				return false;
			});
		}

		function _getAirports(search, page) {
			var searchTerm = "term=" + search + "&" + "page="
					+ +(page == null ? 1 : page) + "&pageSize=" + 10, $tableBody = $airportsTable
					.find('tbody');

			$paginationElem.hide();
			$tableBody
					.html('<td class="text-center" colspan="4"><i class="fa fa-spin fa-spinner"></i></td>');
			$.get('/airports/search', searchTerm, function(data) {
				$findAirportsButton.data("current-value", search);
				_renderTable({
					airports : data.airports.sort(function(a, b) {
						return a.code.localeCompare(b.code);
					})
				});
				_showPaginator(data.page);
			}).fail(function() {
				_renderTable({});
			}).always(function() {
				$findAirportsButton.button('reset');
				_initBinders();
			});
		}

		function _renderTable(data) {
			$.get('/mustache/airportItems.mst', function(template) {
				var rendered = Mustache.render(template, data);
				$airportsTable.find('tbody').html(rendered);
			});
		}

		function _showPaginator(page) {
			if ($paginationElem.data("twbs-pagination")) {
				$paginationElem.twbsPagination('destroy');
			}
			$paginationElem.twbsPagination({
				totalPages : page.totalPages,
				visiblePages : 7,
				startPage : page.number,
				onPageClick : function(event, page) {
					_getAirports($findAirportsButton.data('current-value'),
							page);
					return false;
				}
			});
			$paginationElem.show();
		}
	}();
});