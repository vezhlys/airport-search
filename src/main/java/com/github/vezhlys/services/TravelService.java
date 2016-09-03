package com.github.vezhlys.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.vezhlys.models.Airport;
import com.github.vezhlys.models.BasicFare;
import com.github.vezhlys.models.PagedAirports;
import com.github.vezhlys.models.enums.ServiceTemplate;
import com.github.vezhlys.models.filters.AirportFilter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TravelService {
	private final RestTemplate restTemplate;
	@Value("${klm.fares.endpoints.serviceUrl}")
	private String serviceEndpoint;

	@Autowired
	public TravelService(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Async
	public CompletableFuture<BasicFare> travelFare(final String origin,
			final String destination) {
		log.debug("Getting fare");
		return CompletableFuture.completedFuture(restTemplate.getForObject(
				ServiceTemplate.FARES.getUri(serviceEndpoint)
						+ "/{origin}/{destination}",
				BasicFare.class, origin, destination));
	}

	@Async
	public CompletableFuture<Airport> airport(final String code) {
		log.debug("Getting airport");
		return CompletableFuture.completedFuture(restTemplate.getForObject(
				ServiceTemplate.AIRPORTS.getUri(serviceEndpoint) + "/{code}",
				Airport.class, code));
	}

	@Async
	public CompletableFuture<PagedAirports> airports(
			final AirportFilter filter) {
		log.debug("Getting airports");
		return CompletableFuture.completedFuture(restTemplate.getForObject(
				ServiceTemplate.AIRPORTS.getUri(serviceEndpoint)
						+ "/?size={size}&page={page}&term={term}",
				PagedAirports.class, filter.getRequestParams()));
	}
}
