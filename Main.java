package Project;

//erDeltaT = 1.010050167084168, n.p = 0.4922030563603504, n.q = 0.5077969436396497

public class Main {

	public static void main(String[] args) {
		final int PUT = 0, CALL = 1, US = 0, EU = 1;
		double Price = 100;
		double S = 100;
		double r = .1;
		double sigma = .4;
		double t0 = 0;
		double T = .4;
		double strike = 100;
		int n = 4;
		
		
		MarketData mkt = new MarketData(Price, S, r, sigma, t0);
		Derivative deriv = new VanillaOption(CALL, US, T, strike); 	//Derivative deriv = new BermudanOption();
		Output out = Library.binom(deriv, mkt, n);
		Output impvolOut = new Output();
		int impvolSatus = Library.impvol(deriv, mkt, 5, 100, 1.0e-4, impvolOut);
	}

}
