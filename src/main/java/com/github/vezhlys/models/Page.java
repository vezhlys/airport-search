package com.github.vezhlys.models;

import lombok.Value;

@Value
public class Page {
	private int size;
	private int totalElements;
	private int totalPages;
	private int number;
}
