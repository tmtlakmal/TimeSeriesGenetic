package timeseries.genetic;

import org.jaga.definitions.*;
import org.jaga.util.Mutation;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific mutation algorithm
 *
 */

public class TimeSeriesMutation extends Mutation {

	private static final Class applicableClass = TimeSeriesIndividual.class;

	public Class getApplicableClass() {
		return applicableClass;
	}

	public TimeSeriesMutation() {
		super();
	}

	public TimeSeriesMutation(double mutProb) {
		super(mutProb);
	}

	public Individual [] reproduce(Individual[] parents, GAParameterSet params) {
		final int kidsCount = parents.length;
		TimeSeriesIndividual [] kids = new TimeSeriesIndividual[kidsCount];
		final RandomGenerator rnd = params.getRandomGenerator();
		final double mutProb = getMutationProbability();
		final TimeSeriesIndividualSimpleFactory factory =
				(TimeSeriesIndividualSimpleFactory) params.getIndividualsFactory();

		for (int i = 0; i < kidsCount; i++) {

			final int maxAttempts = params.getMaxBadReproductionAttempts();
			int attempts = 0;
			boolean kidIsValid = false;
			do {

				if (!getApplicableClass().isInstance(parents[i]))
					fireIllegalParentException(parents, i);

				TimeSeriesBitString kidBits = (TimeSeriesBitString) ((TimeSeriesIndividual) parents[i]).getContent().clone();
				for (int b = 0; b < TimeSeriesBitString.getBitStringLength(); b++)
					if (rnd.nextDouble() < mutProb)
						kidBits.flip(b);

				TimeSeriesIndividual tst = (TimeSeriesIndividual)
								factory.createSpecificIndividual(kidBits, params);
				kidIsValid = factory.valid(tst);

				if (kidIsValid)
					kids[i] = tst;

				attempts++;
			} while(!kidIsValid && attempts <= maxAttempts);

			if (!kidIsValid) {
				kids[i] = (TimeSeriesIndividual) factory.createSpecificIndividual(
											((TimeSeriesIndividual)parents[i]).getContent(),
											params);
				kids[i].setFitness(parents[i].getFitness());
			}

		}

		return kids;
	}

	private void fireIllegalParentException(Individual[] parents, int i)
														   throws IllegalArgumentException {
		throw new IllegalArgumentException("SimpleBinaryMutation works "
										   + "only on parents of type " + getApplicableClass()
										   +", but parent number " + i + " is of type "
										   + parents[i].getClass().getName());
	}

}