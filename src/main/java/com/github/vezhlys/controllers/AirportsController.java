package com.github.vezhlys.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.vezhlys.models.PagedAirports;
import com.github.vezhlys.models.filters.AirportFilter;
import com.github.vezhlys.services.TravelService;

@Controller
@RequestMapping(value = "/airports")
public class AirportsController {
	private final TravelService travelService;

	@Autowired
	public AirportsController(final TravelService travelService) {
		this.travelService = travelService;
	}

	@RequestMapping(method = GET)
	public String renderView() {
		return "airports";
	}

	@RequestMapping(method = GET, path = "/search")
	@ResponseBody
	public Callable<PagedAirports> getAirports(
			@RequestParam(required = false, defaultValue = "") final String term,
			@RequestParam(required = false) final int page,
			@RequestParam(required = false) final int pageSize) {
		return () -> {
			return travelService.airports(AirportFilter.builder().term(term)
					.page(page).size(pageSize).build()).get();
		};
	}
}
