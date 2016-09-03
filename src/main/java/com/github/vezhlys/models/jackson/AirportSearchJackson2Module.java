package com.github.vezhlys.models.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.vezhlys.models.PagedAirports;

public class AirportSearchJackson2Module extends SimpleModule {

	@Override
	public void setupModule(final SetupContext context) {
		context.setMixInAnnotations(PagedAirports.class,
				PagedAirportsMixin.class);
	}
}
