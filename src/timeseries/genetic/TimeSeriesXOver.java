package timeseries.genetic;

import org.jaga.definitions.*;
import org.jaga.util.XOver;


/**
 * 
 * @author lakmal
 *
 *	The Time series specific Xover algorithm
 *
 */

public class TimeSeriesXOver extends XOver {

	private static final Class applicableClass = TimeSeriesIndividual.class;

	public TimeSeriesXOver() {
		super();
	}

	public TimeSeriesXOver(double xOverProb) {
		super(xOverProb);
	}

	public Class getApplicableClass() {
		return applicableClass;
	}

	public Individual[] reproduce(Individual[] parents, GAParameterSet params) {
		if (parents.length != getRequiredNumberOfParents())
			throw new IllegalArgumentException("Need " + getRequiredNumberOfParents()
											   + " parents for reproduction (not "
											   + parents.length + ")");

		// Check correct type for parents, get length:
		int bitLen = checkParentsTypeAndLength(parents);

		// Chance (1 - xover probability) that parents wont be changed:
		final RandomGenerator rnd = params.getRandomGenerator();

		if (rnd.nextDouble() >= getXOverProbability())
			return makeCopyOfParents(parents, params);
		
		
		// Get parents bitsrings:
		TimeSeriesBitString p1 = ((TimeSeriesIndividual) parents[0]).getContent();
		TimeSeriesBitString p2 = ((TimeSeriesIndividual) parents[1]).getContent();

		// x-over:
		final int maxAttempts = params.getMaxBadReproductionAttempts();

		int attempts = 0;
		boolean kidsAreValid = false;
		do {
			kidsAreValid = false;
			int xPoint = rnd.nextInt(1, bitLen);

			// offspring bit strings:
			TimeSeriesBitString c1 = new TimeSeriesBitString();
			TimeSeriesBitString c2 = new TimeSeriesBitString();

			// copy before xover-point:
			for (int i = 0; i < xPoint; i++) {
				c1.setBitValue(i, p1.getBitValue(i));
				c2.setBitValue(i, p2.getBitValue(i));
			}

			// copy after xover-point:
			for (int i = xPoint; i < bitLen; i++) {
				c1.setBitValue(i, p2.getBitValue(i));
				c2.setBitValue(i, p1.getBitValue(i));
			}

			// create children and check if children are valid:
			TimeSeriesIndividual[] kids = this.createKidsFromEncoding(params, c1, c2);
			kidsAreValid = kidsSatisfyConstraints(kids, params);

			// return valid kids or have another attempts:
			if (kidsAreValid)
				return kids;
			else
				attempts++;

		} while(!kidsAreValid && attempts < maxAttempts);

		// all attempts failed:
		return makeCopyOfParents(parents, params);

	}

	protected Individual [] makeCopyOfParents(Individual [] parents, GAParameterSet params) {
		Individual [] kids = new Individual[parents.length];
		for (int i = 0; i < kids.length; i++) {
			kids[i] = params.getIndividualsFactory().createSpecificIndividual(
						   ((TimeSeriesIndividual)parents[i]).getContent(),
						   params);
			//kids[i] = (BinaryEncodedIndividual) ((BinaryEncodedIndividual) parents[i]).clone();
			kids[i].setFitness(parents[i].getFitness());
		}
		return kids;
	}

	private int checkParentsTypeAndLength(Individual[] parents) throws IllegalArgumentException {
		// Now make sure that parents are of right type and equal length:
			  int bitLen = -1;
			  for (int i = 0; i < parents.length; i++) {
				  // Check type:
				  if (!getApplicableClass().isInstance(parents[i]))
					  throw new IllegalArgumentException("TimeSeriesXOver works only for "
													   + getApplicableClass().getName()
													   + ", but parent is "
													   + parents[i].getClass().getName());
				  if (0 == i) // Remember bit len of first parent:
					  bitLen = ((TimeSeriesIndividual) parents[i]).getContent().getBitStringLength();
				  else        // And compare it to the bit len of all other parents:
					  if (bitLen != ((TimeSeriesIndividual) parents[i]).getContent().getBitStringLength())
						  throw new IllegalArgumentException("TimeSeriesXOver works only on "
												  + getApplicableClass() + " of equal representation length ("
												  + bitLen  + "!="
												  +((TimeSeriesIndividual) parents[i]).getContent().getBitStringLength());
			  }
		return bitLen;
	}

	private boolean kidsSatisfyConstraints(TimeSeriesIndividual[] kids, GAParameterSet params) {
		TimeSeriesIndividualSimpleFactory fact = (TimeSeriesIndividualSimpleFactory) params.getIndividualsFactory();
		for (int i = 0; i < kids.length; i++)
			if (!fact.valid(kids[i]))
				return false;
		return true;
	}

	private TimeSeriesIndividual [] createKidsFromEncoding(GAParameterSet params, TimeSeriesBitString c1, TimeSeriesBitString c2) {
		TimeSeriesIndividual [] kids = new TimeSeriesIndividual[getRequiredNumberOfParents()];
		kids[0] = (TimeSeriesIndividual)
					 params.getIndividualsFactory().createSpecificIndividual(c1, params);
		kids[1] = (TimeSeriesIndividual)
					 params.getIndividualsFactory().createSpecificIndividual(c2, params);
		return kids;
	}

}