package com.github.vezhlys.models;

import java.util.List;

import lombok.Value;

@Value
public class EmbeddedLocations {
	private final List<Airport> locations;
}
