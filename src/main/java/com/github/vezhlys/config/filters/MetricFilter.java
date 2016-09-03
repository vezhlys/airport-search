package com.github.vezhlys.config.filters;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.github.vezhlys.services.MetricsService;

public class MetricFilter implements Filter {
	private final MetricsService metricsService;

	public MetricFilter(final MetricsService metricsService) {
		this.metricsService = metricsService;
	}

	@Override
	public void init(final FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws java.io.IOException, ServletException {
		final Instant start = Instant.now();
		chain.doFilter(request, response);
		final int status = ((HttpServletResponse) response).getStatus();
		final Instant end = Instant.now();
		final String metricPrefix = "counter.airports_search.metrics.";
		metricsService.increase(metricPrefix + "all");
		metricsService.increase(metricPrefix + status);
		metricsService.update(
				"gauge.airpots_search.internal.metrics.response.time."
						+ UUID.randomUUID(),
				Duration.between(start, end).toMillis());
	}

	@Override
	public void destroy() {

	}
}
