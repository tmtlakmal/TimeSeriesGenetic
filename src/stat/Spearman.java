package stat;
/**
 * @author damith
 * */
public class Spearman {

	public static double Rho(double[] X, double[] Y){
		double[] d=new double[X.length];
		double sumsq=0;
		for(int i=0;i<X.length;i++){
			d[i]=X[i]-Y[i];
			sumsq+=(d[i]*d[i]);
		}
		return (1-( 6*sumsq/(X.length*((X.length*X.length)-1))));
	}
}
