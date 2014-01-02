package stat;
/**
 * @author damith
 * */
public class Gini {
	public static double MeanDifference(double[] X){
		double ret=0;
		for(int i=0;i<X.length;i++){
			for(int j=0;j<X.length;j++){
				ret+=Math.abs((X[i]-X[j]));
			}
		}
		return ret/(X.length*(X.length-1));
	}
}
