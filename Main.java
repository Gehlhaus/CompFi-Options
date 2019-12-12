import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		final int PUT = 0, CALL = 1, US = 0, EU = 1;
		double Price = 10;
		double S = 100;
		double r = .1;
		double sigma = 0.4;
		double t0 = 0;
		double T = .4;
		double strike = 100;
		int n = 4;
		int max_iter = 250;
		double tol = .0001;
		
		{	
		//Scanner scan = new Scanner(System.in);
		//System.out.println("Enter Market Price");
		//double Price = scan.nextDouble();
		
		//System.out.println("Enter Stock Price");
		//double S = scan.nextDouble();
		
		//System.out.println("Enter Interest");
		//double r = scan.nextDouble();
		
		//System.out.println("Enter SIGMA");
		//double sigma = scan.nextDouble(); 
		
		//System.out.println("Enter t0");
		//double t0 = scan.nextDouble();
		
		//System.out.println("Enter T");
		//double T = scan.nextDouble();
		
		//System.out.println("Enter Strike");
		//double strike = scan.nextDouble();
		
		//System.out.println("Enter N");
		//int n = scan.nextInt();
		
		//System.out.println("Enter Max_Iter");
		//int max_iter = scan.nextInt();
		
		//System.out.println("Enter tol");
		//double tol = scan.nextDouble();
		}
		
		
		MarketData mkt = new MarketData(Price, S, r, sigma, t0);
		VanillaOption deriv = new VanillaOption(PUT, US, T, strike); 	//Derivative deriv = new BermudanOption();
		BermudanOption deriv1 = new BermudanOption(PUT, T, strike, 0, 0.3);
		Output out = Library.binom(deriv, mkt, n, false);
		System.out.println("FV = " + out.FV + ", Fugit = " + out.fugit);
		out = Library.binom(deriv1, mkt, n, false);
		System.out.println("FV = " + out.FV + ", Fugit = " + out.fugit);
		Output impvolOut = new Output();
		int impvolStatus = Library.impvol(deriv1, mkt, n, max_iter, tol, impvolOut);
		System.out.print("Volatility status = "); 
			if(impvolStatus == 1){ System.out.println("Failed"); } 
			else { System.out.println("Success, " + impvolOut.num_iter + " iterations"); }
		System.out.println("ImpVol = " + impvolOut.impvol);
	}

}
