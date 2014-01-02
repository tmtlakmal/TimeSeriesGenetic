package timeseries.genetic;

import org.jaga.definitions.*;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific individual factory
 *
 */

public class TimeSeriesIndividualSimpleFactory implements IndividualsFactory {
	
	private int bitStringOrder;
	private int bitStringDecimalPart;
	private int bitStringIntegerPart;
	private int bitStringLength;
	

	public TimeSeriesIndividualSimpleFactory(
			int bitStringOrder,
			int bitStringIntegerPart,
			int bitStringDecimalPart) {
		this.bitStringOrder = bitStringOrder;
		this.bitStringIntegerPart =bitStringIntegerPart;
		this.bitStringDecimalPart=bitStringDecimalPart;
		TimeSeriesBitString.setBitStringData(
				bitStringOrder, 
				bitStringIntegerPart, 
				bitStringDecimalPart);
		this.bitStringLength = TimeSeriesBitString.getBitStringLength();
	}
	
	
	

	@Override
	public Individual createDefaultIndividual(GAParameterSet params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Individual createRandomIndividual(GAParameterSet params) {
		// create an individual:
		TimeSeriesBitString content = new TimeSeriesBitString();	
		int noOfTrue = params.getRandomGenerator().nextInt(0, bitStringLength);
		//System.out.println(noOfTrue);
		int pos;
		boolean assigned;
		for(int i=0;i<noOfTrue;i++){
			assigned=false;
			while(!assigned){
				pos = params.getRandomGenerator().nextInt(0, bitStringLength);
				if(!content.getBitValue(pos)){
					content.setBitTrue(pos);
					assigned = true;
				}
			}
		}
		TimeSeriesIndividual indiv = new TimeSeriesIndividual(content);
		return indiv;
	}

	@Override
	public Individual createSpecificIndividual(Object init,
			GAParameterSet params) throws NullPointerException,
			ClassCastException {
		if (null == init)
			throw new NullPointerException("Initialisation value for NDecimalsIndividual my not be null");

		if (init instanceof TimeSeriesIndividual)
			return createSpecificIndividual((TimeSeriesIndividual) init);

		if (init instanceof TimeSeriesBitString)
					return createSpecificIndividual((TimeSeriesBitString) init);

		throw new ClassCastException("Initialisation value for NDecimalsIndividual "
									 + "must be of type BitString or Double (but is "
									 + init.getClass() + ")");
	}
	
	public Individual createSpecificIndividual(TimeSeriesIndividual initVal) {
		TimeSeriesIndividual indiv = new TimeSeriesIndividual(initVal.getContent());
		return indiv;
	}

	public Individual createSpecificIndividual(TimeSeriesBitString initVal) {
		TimeSeriesIndividual indiv = new TimeSeriesIndividual(initVal);
		return indiv;
	}
	
	public boolean valid(TimeSeriesIndividual indiv) {
		
		// check if individual's settings match this factiory:
		if (indiv.getContent() instanceof TimeSeriesBitString) {
			return true;
		}
		throw new IllegalArgumentException("The given individual (" + indiv
				 + ") is likely not created by this factory.");
		
	}
	

}