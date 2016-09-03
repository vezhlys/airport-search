package com.github.vezhlys.models.filters;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;

@Builder
public class AirportFilter {
	private final int size;
	private final int page;
	private final String term;

	public Map<String, Object> getRequestParams() {
		final Map<String, Object> params = new HashMap<>();
		params.put("size", size > 0 ? size : 25);
		params.put("page", page > 0 ? page : 1);
		params.put("term", term);
		return params;
	}
}
