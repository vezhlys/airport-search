package com.github.vezhlys.models;

import com.github.vezhlys.models.enums.Currency;

import lombok.Value;

@Value
public class BasicFare {
	private double amount;
	private Currency currency;
}
