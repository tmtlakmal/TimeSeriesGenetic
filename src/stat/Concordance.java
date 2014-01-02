package stat;
/**
 * @author damith
 * */
public class Concordance {
	public static int[][] Concord(int[] X, int[] Y) {
		if (X.length != Y.length) {
			System.out.println("please make sure the two arrays are of equal length");
			return null;
		}
		int[][] temp, grid, Xtemp, Ytemp;
		Xtemp = new int[X.length][X.length];
		Ytemp = new int[Y.length][Y.length];
		grid = new int[X.length][Y.length];

		for (int i = 0; i < X.length; i++) {
			for (int j = 0; j < X.length; j++) {
				Xtemp[i][j] = X[i] - X[j];
			}
		}

		for (int i = 0; i < Y.length; i++) {
			for (int j = 0; j < Y.length; j++) {
				Ytemp[i][j] = Y[i] - Y[j];
			}
		}

		for (int i = 0; i < Y.length; i++) {
			for (int j = 0; j < Y.length; j++) {
				grid[i][j] = Xtemp[i][j] * Ytemp[i][j];
			}
		}

		return grid;
	}

	public static int getConcordantPairs(int[] X, int[] Y) {
		int[][] temp = Concordance.Concord(X, Y);
		int nc = 0;
		for (int i = 0; i < (X.length); i++) {
			for (int j = 0; j < Y.length; j++) {
				if (temp[i][j] > 0)
					nc++;
			}
		}
		return nc;
	}

	public static int getDiscordantPairs(int[] X, int[] Y) {
		int[][] temp = Concordance.Concord(X, Y);
		int nc = 0;
		for (int i = 0; i < (X.length); i++) {
			for (int j = 0; j < Y.length; j++) {
				if (temp[i][j] < 0)
					nc++;
			}
		}
		return nc;
	}

	public static double[][] Concord(double[] X, double[] Y) {
		if (X.length != Y.length) {
			System.out.println("please make sure the two arrays are of equal length");
			return null;
		}
		double[][] temp, grid, Xtemp, Ytemp;
		Xtemp = new double[X.length][X.length];
		Ytemp = new double[Y.length][Y.length];
		grid = new double[X.length][Y.length];

		for (int i = 0; i < X.length; i++) {
			for (int j = 0; j < X.length; j++) {
				Xtemp[i][j] = X[i] - X[j];
			}
		}

		for (int i = 0; i < Y.length; i++) {
			for (int j = 0; j < Y.length; j++) {
				Ytemp[i][j] = Y[i] - Y[j];
			}
		}

		for (int i = 0; i < Y.length; i++) {
			for (int j = 0; j < Y.length; j++) {
				grid[i][j] = Xtemp[i][j] * Ytemp[i][j];
			}
		}

		return grid;

	}

	public static int getConcordantPairs(double[] X, double[] Y) {
		double[][] temp = Concordance.Concord(X, Y);
		int nc = 0;
		for (int i = 0; i < (X.length); i++) {
			for (int j = 0; j < Y.length; j++) {
				if (temp[i][j] > 0)
					nc++;
			}
		}
		return nc;
	}

	public static int getDiscordantPairs(double[] X, double[] Y) {
		double[][] temp = Concordance.Concord(X, Y);
		int nd = 0;
		for (int i = 0; i < (X.length); i++) {
			for (int j = 0; j < Y.length; j++) {
				if (temp[i][j] < 0)
					nd++;
			}
		}
		return nd;
	}
}
