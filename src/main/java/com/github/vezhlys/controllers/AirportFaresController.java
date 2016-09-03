package com.github.vezhlys.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.vezhlys.models.Airport;
import com.github.vezhlys.models.AirportFare;
import com.github.vezhlys.models.BasicFare;
import com.github.vezhlys.models.filters.AirportFilter;
import com.github.vezhlys.services.TravelService;

@Controller
@RequestMapping(value = "/fares")
public class AirportFaresController {
	private final TravelService travelService;

	@Autowired
	public AirportFaresController(final TravelService travelService) {
		this.travelService = travelService;
	}

	@RequestMapping(method = GET)
	public String renderView() {
		return "fares";
	}

	@RequestMapping(method = GET, params = {"origin", "destination"})
	@ResponseBody
	public Callable<AirportFare> getFare(@RequestParam final String origin,
			@RequestParam final String destination) {
		return () -> {
			final CompletableFuture<BasicFare> fare = travelService
					.travelFare(origin, destination);
			final CompletableFuture<Airport> originAirport = travelService
					.airport(origin);
			final CompletableFuture<Airport> destinationAirport = travelService
					.airport(destination);
			return new AirportFare(fare.join(), originAirport.join(),
					destinationAirport.join());
		};
	}

	@RequestMapping(method = GET, params = {"term"})
	@ResponseBody
	public Callable<List<Airport>> getAirports(@RequestParam final String term) {
		return () -> {
			return travelService
					.airports(AirportFilter.builder().term(term).build()).get()
					.getAirports();
		};
	}
}
