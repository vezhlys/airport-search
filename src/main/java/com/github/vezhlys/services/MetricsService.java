package com.github.vezhlys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MetricsService {
	private final CounterService counterService;
	private final GaugeService gaugeService;

	@Autowired
	public MetricsService(final CounterService counterService,
			final GaugeService gaugeService) {
		this.counterService = counterService;
		this.gaugeService = gaugeService;
	}

	public void increase(final String metricName) {
		log.debug("increasing metric {}", metricName);
		counterService.increment(metricName);
	}

	public void update(final String metricName, final double value) {
		log.debug("updating metric {} {}", metricName, value);
		gaugeService.submit(metricName, value);
	}
}
