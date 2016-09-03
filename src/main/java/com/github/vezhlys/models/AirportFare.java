package com.github.vezhlys.models;

import lombok.Value;

@Value
public class AirportFare {
	BasicFare fare;
	Airport origin;
	Airport destination;
}
