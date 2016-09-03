package com.github.vezhlys.models.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.vezhlys.models.EmbeddedLocations;
import com.github.vezhlys.models.Page;

abstract class PagedAirportsMixin {

	@JsonCreator
	PagedAirportsMixin(
			@JsonProperty("_embedded") final EmbeddedLocations locations,
			@JsonProperty("page") final Page page) {
	}
}
