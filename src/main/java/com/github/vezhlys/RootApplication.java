package com.github.vezhlys;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.vezhlys.config.filters.MetricFilter;
import com.github.vezhlys.metrics.AirportPublicMetrics;
import com.github.vezhlys.services.MetricsService;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableAsync
public class RootApplication {

	public static void main(final String[] args) throws Exception {
		SpringApplication.run(RootApplication.class, args);
	}

	@Bean
	public AirportPublicMetrics publicMetrics(final MetricReader reader) {
		return new AirportPublicMetrics(reader);
	}

	@Bean
	public MetricsEndpoint metricsEndpoint(final AirportPublicMetrics metrics) {
		return new MetricsEndpoint(metrics);
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration(
			final MetricsService metricsService) {
		final FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new MetricFilter(metricsService));
		registration.addUrlPatterns("/fares/*", "/airports/*");
		registration.setName("metricsFilter");
		return registration;
	}

	@Bean
	public Filter logFilter() {
		final CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(5120);
		return filter;
	}

	@Bean
	public WebMvcConfigurerAdapter forwardFromIndex() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addViewControllers(
					final ViewControllerRegistry registry) {
				registry.addViewController("/").setViewName("forward:/fares");
			}
		};
	}
}
