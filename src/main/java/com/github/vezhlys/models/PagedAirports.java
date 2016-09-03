package com.github.vezhlys.models;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PagedAirports {
	private final Page page;
	private final List<Airport> airports;

	public PagedAirports(final EmbeddedLocations locations, final Page page) {
		this.page = page;
		this.airports = Collections
				.unmodifiableList(Optional.ofNullable(locations.getLocations())
						.orElse(Collections.emptyList()));
	}
}
