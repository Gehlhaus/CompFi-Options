package Project;

public class Main {

	public static void main(String[] args) {
		final int PUT = 0, CALL = 1, US = 0, EU = 1;
		double Price = 100;
		double S = 100;
		double r = .15;
		double sigma = .3;
		double t0 = 0;
		double T = .3;
		double strike = 95;
		int n = 3;
		
		
		MarketData mkt = new MarketData(Price, S, r, sigma, t0);
		Derivative deriv = new VanillaOption(PUT, US, T, strike); 	//Derivative deriv = new BermudanOption();
		Output out = Library.binom(deriv, mkt, n);
		Output impvolOut = new Output();
		int impvolSatus = Library.impvol(deriv, mkt, 5, 100, 1.0e-4, impvolOut);
	}

}
