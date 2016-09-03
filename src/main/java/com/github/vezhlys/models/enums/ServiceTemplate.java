package com.github.vezhlys.models.enums;

public enum ServiceTemplate {
	FARES, AIRPORTS;

	public String getUri(final String endpoint) {
		return endpoint + toString().toLowerCase();
	}
}
