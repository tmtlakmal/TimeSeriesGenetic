package timeseries.genetic;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific representation
 *
 */


public class TimeSeriesBitString {

	private static int length;
	private static int order;
	private static int decimalPart;
	private static int integerPart;
	private boolean [] content;

	/*private TimeSeriesBitString() {
		throw new UnsupportedOperationException("Use BitString(int)");
	}*/
	/*
	 * order - the order of polynomial (3rd order x^3)
	 * decimalPart - the length of decimal Part
	 * integerPart - the length of the integer Part
	 */
	
	public static void setBitStringData(int pOrder, int iPart, int dPart){
		decimalPart = dPart;
		integerPart = iPart;
		order = pOrder;
		length = (decimalPart+integerPart+1)*(order+1);
	}
	
	public static int getBitStringLength(){
		return length;
	}
	
	public static int getBitStringOrder(){
		return order;
	}
	
	public TimeSeriesBitString() {		
		this.content = new boolean[length];
	}
	
	public double getDecimalFraction(){
		if(decimalPart>0){
			return 1/Math.pow(2.0, decimalPart);
		}
		else{
			return 0;
		}
	}
	
	public void setBitTrue(int bitNo){
		content[bitNo] = true;
	}
	
	public void setBitFalse(int bitNo){
		content[bitNo] = false;
	}
	
	public boolean getBitValue(int bitNo){
		return content[bitNo];
	}
	
	public void setBitValue(int bitNo,boolean value){
		this.content[bitNo] = value;
	}
	/*
	 * term No - should be order
	 */
	
	public double getCoefficient(int termNo){
		if(termNo>order||termNo<0){
			return 0;
		}
		else{ 
			int signPosition = termNo*(this.decimalPart+this.integerPart+1);
			int integerPosition = signPosition+1;
			int decimalPosition = integerPosition+this.integerPart;
			int integerVal = 0,decimalVal=0;
			for (int i = integerPosition; i < decimalPosition; i++) {
			    integerVal = (integerVal << 1) + (content[i] ? 1 : 0);
			}
			for(int i=decimalPosition;i<decimalPosition+decimalPart;i++){
				decimalVal = (decimalVal << 1) + (content[i] ? 1 : 0);
			}
			//sign 0 denotes positive numbers
			if(!content[signPosition]){
				return integerVal+decimalVal*this.getDecimalFraction();
			}
			else{
				return -(integerVal+decimalVal*this.getDecimalFraction());
			}
		}
	}
/*
	public TimeSeriesBitString(final TimeSeriesBitString toCopy) {
		this.length = toCopy.length;
		int bufSize = length / 32;
		if (0 != length % 32)
			++bufSize;
		this.content = new int[bufSize];
		System.arraycopy(toCopy.content, 0, this.content, 0, this.content.length);
	}
*/
	public Object clone() {
		TimeSeriesBitString copy = new TimeSeriesBitString();
		System.arraycopy(this.content, 0, copy.content, 0, copy.content.length);
		return copy;
	}
/*
	public int getLength() {
		return length;
	}

	public TimeSeriesBitString substring(int from, int to) {
		int len = checkSubLength(from, to);
		TimeSeriesBitString substring = new TimeSeriesBitString(len);
		for (int i = from; i < to; i++)
			substring.setUnchecked(i - from, this.getUnchecked(i));
		return substring;
	}

	public boolean get(int index) {
		checkIndex(index);
		return getUnchecked(index);
	}

	protected boolean getUnchecked(int index) {
		int segment = index / 32;
		int offset = index % 32;
		int mask = 0x1 << (32 - offset - 1);
		return 0 != (content[segment] & mask);
	}

	public void set(int index, boolean value) {
		checkIndex(index);
		setUnchecked(index, value);
	}

	public void setUnchecked(int index, boolean value) {
		int segment = index / 32;
		int offset = index % 32;
		int mask = 0x1 << (32 - offset - 1);
		if (value)
			content[segment] |= mask;
		else
			content[segment] &= ~mask;
	}

	public void set(int from, int to, boolean value) {
		checkSubLength(from, to);
		for (int i = from; i < to; setUnchecked(i++, value));
	}

	public void set(int from, int to, TimeSeriesBitString values) {
		if (values.getLength() == 0)
			throw new IllegalArgumentException("Length of values must be > 0");
		int len = checkSubLength(from, to);
		int iV = 0;
		for (int i = from; i < to; i++, iV++) {
			if (iV >= values.getLength())
				iV = 0;
			this.setUnchecked(i, values.getUnchecked(iV));
		}
	}*/

	public void flip(int bitNo) {
		if(content[bitNo]){
			content[bitNo]=false;
		}
		else{
			content[bitNo]=true;
		}
	}
/*
	public String toString() {
		StringBuffer s = new StringBuffer(length);
		for (int i = 0; i < length; s.append(get(i++) ? 1 : 0));
		return s.toString();
	}

	protected int checkSubLength(int from, int to) throws IndexOutOfBoundsException {
		checkIndex(from);
		checkIndex(to - 1);
		int sublen = to - from;
		if (0 > sublen)
			throw new IllegalArgumentException("must have 'from' <= 'to', but has "
											   + from + " > " + to);
		return sublen;
	}

	protected void checkIndex(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= this.length)
			throw new IndexOutOfBoundsException("index is " + index
												+ ", but must be in [0, "
												+ (this.length - 1) + "]");
	}*/
}
