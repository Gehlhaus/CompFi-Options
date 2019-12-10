import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		final int PUT = 0, CALL = 1, US = 0, EU = 1;
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Market Price");
		double Price = scan.nextDouble();
		//Price = 9;
		System.out.println("Enter Stock Price");
		double S = scan.nextDouble();
		//S = 100;
		System.out.println("Enter Interest");
		double r = scan.nextDouble();
		//r = .1;
		System.out.println("Enter SIGMA");
		double sigma = scan.nextDouble(); 
		//sigma = 0.4;
		System.out.println("Enter t0");
		double t0 = scan.nextDouble();
		System.out.println("Enter T");
		double T = scan.nextDouble();
		System.out.println("Enter Strike");
		double strike = scan.nextDouble();
		System.out.println("Enter N");
		int n = scan.nextInt();
		System.out.println("Enter Max_Iter");
		int max_iter = scan.nextInt();
		System.out.println("Enter tol");
		double tol = scan.nextDouble();
		
		scan.close();
		
		
		MarketData mkt = new MarketData(Price, S, r, sigma, t0);
		Derivative deriv = new VanillaOption(CALL, EU, T, strike); 	//Derivative deriv = new BermudanOption();
		Output out = Library.binom(deriv, mkt, n, true);
		System.out.println("FV = " + out.FV + ", Fugit = " + out.fugit);
		Output impvolOut = new Output();
		/*int impvolStatus = Library.impvol(deriv, mkt, n, max_iter, tol, impvolOut);
		System.out.print("Volatility status = "); 
			if(impvolStatus == 1){ System.out.println("Failed"); } 
			else { System.out.println("Success, " + impvolOut.num_iter + " iterations"); }
		System.out.println("ImpVol = " + impvolOut.impvol);*/
	}

}
