package com.github.vezhlys.config;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.github.vezhlys.models.jackson.AirportSearchJackson2Module;

@Configuration
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().anonymous().and().httpBasic()
				.disable();
	}

	@Bean
	@ConfigurationProperties("klm.fares.client")
	OAuth2ProtectedResourceDetails getResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean(name = "restTemplate")
	public RestTemplate restTemplate() {
		final RestTemplate template = new OAuth2RestTemplate(
				getResourceDetails(), new DefaultOAuth2ClientContext(
						new DefaultAccessTokenRequest()));
		template.setMessageConverters(
				Arrays.asList(new MappingJackson2HttpMessageConverter(
						objectMapperBuilder().build())));
		return template;
	}

	private Jackson2ObjectMapperBuilder objectMapperBuilder() {
		final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.failOnUnknownProperties(false);
		builder.serializationInclusion(NON_NULL);
		builder.modules(new AirportSearchJackson2Module());
		builder.featuresToDisable(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return builder;
	}
}
