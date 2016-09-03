package com.github.vezhlys.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {
	@RequestMapping(method = GET)
	public String renderView() {
		return "dashboard";
	}
}
