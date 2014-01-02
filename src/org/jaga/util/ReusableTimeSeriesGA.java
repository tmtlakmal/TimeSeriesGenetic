package org.jaga.util;

import org.jaga.definitions.*;

import timeseries.genetic.TimeSeriesGA;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific reusable genetic algorithm
 *
 */

public class ReusableTimeSeriesGA extends TimeSeriesGA {

	private GAParameterSet parameters = null;

	public ReusableTimeSeriesGA() {
		super();
	}

	public ReusableTimeSeriesGA(GAParameterSet parameters) {
		super();
		this.parameters = parameters;
	}

	public GAResult exec() {
		if (null == this.parameters)
			throw new IllegalStateException("ReusableSimpleGA.exec() was called without "
											+ "previously initialising the parameter set");
		return exec(this.parameters);
	}
}