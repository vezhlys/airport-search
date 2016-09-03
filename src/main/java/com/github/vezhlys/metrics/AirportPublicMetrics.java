package com.github.vezhlys.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.reader.MetricReader;

public class AirportPublicMetrics implements PublicMetrics {
	private static final String RESPONSES_AIRPORTS_SEARCH_METRICS_PREFIX = "responses.airports_search.metrics.";
	private static final String RESPONSES_AIRPORTS_SEARCH_METRICS_MIN = RESPONSES_AIRPORTS_SEARCH_METRICS_PREFIX
			+ "min";
	private static final String RESPONSES_AIRPORTS_SEARCH_METRICS_MAX = RESPONSES_AIRPORTS_SEARCH_METRICS_PREFIX
			+ "max";
	private static final String RESPONSES_AIRPORTS_SEARCH_METRICS_AVG = RESPONSES_AIRPORTS_SEARCH_METRICS_PREFIX
			+ "avg";

	private final MetricReader metricReader;

	public AirportPublicMetrics(final MetricReader metricReader) {
		this.metricReader = metricReader;
	}

	@Override
	public Collection<Metric<?>> metrics() {
		final List<Metric<?>> result = new ArrayList<>();
		result.addAll(addResponseCodeCounts());
		result.add(new Metric<>(RESPONSES_AIRPORTS_SEARCH_METRICS_MIN,
				minResponse()));
		result.add(new Metric<>(RESPONSES_AIRPORTS_SEARCH_METRICS_MAX,
				maxResponse()));
		result.add(new Metric<>(RESPONSES_AIRPORTS_SEARCH_METRICS_AVG,
				avgResponse()));
		return result;
	}

	private DoubleStream getResponsesStream() {
		return StreamSupport
				.stream(this.metricReader.findAll().spliterator(), false)
				.filter(metric -> {
					return metric.getName().contains(
							"gauge.airpots_search.internal.metrics.response.time.");
				}).mapToDouble(metric -> (Double) metric.getValue());
	}

	private Double minResponse() {
		return getResponsesStream().min().orElse(new Double(0));
	}

	private Double maxResponse() {
		return getResponsesStream().max().orElse(new Double(0));
	}

	private Double avgResponse() {
		return getResponsesStream().average().orElse(new Double(0));
	}

	private List<Metric<?>> addResponseCodeCounts() {
		return StreamSupport
				.stream(this.metricReader.findAll().spliterator(), false)
				.filter(metric -> {
					return metric.getName()
							.contains("counter.airports_search.metrics.");
				}).collect(Collectors.toCollection(ArrayList::new));
	}
}
