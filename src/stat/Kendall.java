package stat;
/**
 * @author damith
 * */
public class Kendall {
	public static double Tau(double[] X, double[] Y){
		int nc=Concordance.getConcordantPairs(X, Y);
		int nd= Concordance.getDiscordantPairs(X, Y);
		int n=Y.length;
		
		double tau= 2*((nc-nd)*1.0)/(n*(n-1));
		
		return tau;
		
	} 
}
