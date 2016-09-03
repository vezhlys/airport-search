package com.github.vezhlys.models;

import lombok.Value;

@Value
public class Airport {
	private String code;
	private String name;
	private String description;
	private Coordinates coordinates;
}
