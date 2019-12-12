public final class Library {
	//Computes the Fair-Value(FV) and Fugit of a derivative
	static Output binom(final Derivative deriv, final MarketData mkt, int n, boolean print) {
		//System.out.println("Calculating FV with SIGMA: " + mkt.sigma);
		double deltaT = ((deriv.T-mkt.t0)/n);
		double u = Math.pow(Math.E, (mkt.sigma * Math.sqrt(deltaT)));
		double d = 1.0/u;
		double p = (Math.pow(Math.E, mkt.r * deltaT) - d)/(u - d);
		double q = 1 - p;
		double erDeltaT = Math.pow(Math.E, (mkt.r * deltaT));
		
		deriv.p = p;
		deriv.q = q;
		deriv.erDeltaT = erDeltaT;
		
		Output out = new Output();
		Node[][] nodes = new Node[n+1][];
		
		//Initialize Node 2d array
		for(int i=0; i<nodes.length; i++){
			nodes[i] = new Node[i+1];
		}
		
		//Create Nodes, Assign stock prices, Assign isLeaf, Set Children 
		for(int i=nodes.length-1; i>=0; i--) {
			for(int j=0; j<nodes[i].length; j++) {
				nodes[i][j] = new Node();
				int value = i-(2*j);
				if(value == 0) nodes[i][j].stockPrice = mkt.S;
				else if(value > 0) nodes[i][j].stockPrice = mkt.S * Math.pow(u, value);
				else if(value < 0) nodes[i][j].stockPrice = mkt.S * Math.pow(d, Math.abs(value));
				if(i == nodes.length - 1) {
					nodes[i][j].isLeaf = true;
					nodes[i][j].t = deriv.T;
					//System.out.println(nodes[i][j].t);
				}
				else {
					nodes[i][j].uChild = nodes[i+1][j];
					nodes[i][j].dChild = nodes[i+1][j+1];
					nodes[i][j].t = deltaT * i;
					//System.out.println(nodes[i][j].deltaT * i);
				}
			}
		}
			
		for(int i=nodes.length - 1; i>=0; i--) {
			for(int j=0; j<nodes[i].length; j++) {
				if(i == nodes.length - 1){
					deriv.terminalCondition(nodes[i][j]);
				}
				else { 
					deriv.valuationTest(nodes[i][j]); 
				}
			}
		}		
		out.FV = nodes[0][0].price;
		out.fugit = nodes[0][0].fugit;
		//System.out.println("FV for sigma: " + mkt.sigma + " = " + out.FV);
				
		if(print){
			print(nodes);
		}
		
		return out;
	}
	
	//Executes a loop of iterations of the Binom function to calculate the implied volatility of a derivative 
	static int impvol(final Derivative deriv, final MarketData mkt, int n, int max_iter, double tol, Output out) {
		//low 1% high 200%
		int counter = 0;
		double temp, FV, vLow = .01, vHigh = 2.0;
		while(counter < max_iter) {
			//System.out.println("Iteration " + counter + ": vLow = " + vLow + ", vHigh = " + vHigh);
			//Check vLow
			mkt.sigma = vLow;
			FV = binom(deriv, mkt, n, false).FV;
			//System.out.println("FV for sigma: " + vLow + " = " + FV);
			double lowDiff = FV - mkt.Price;
			//System.out.println("Low Diff = " + lowDiff);
			if(Math.abs(lowDiff) <= tol){
				out.impvol = vLow;
				out.num_iter = counter;
				return 0;
			}
			//Check vHigh
			mkt.sigma = vHigh;
			FV = binom(deriv, mkt, n, false).FV;
			double highDiff = FV - tol;
			//System.out.println("High Diff = " + highDiff);
			if(Math.abs(highDiff) <= tol){
				out.impvol = vLow;
				out.num_iter = counter;
				return 0;
			}
			
			temp = (vLow + vHigh)/2.0;
			mkt.sigma = temp;
			FV = binom(deriv, mkt, n, false).FV;
			double tempDiff = FV - mkt.Price;
			if(tempDiff < 0){
				vLow = temp;
			}
			else {
				vHigh = temp;
			}
			counter++;	
		}
		out.num_iter = 0;
		out.impvol = 0;
		return 1; 	//Fail
		
	}
	
	public static void print (Node[][] nodes){
		for(int i=0; i<nodes.length; i++){
			for(int j=0; j<nodes[i].length; j++){
				System.out.print(i + "," + j + " - " + "Stock: ");
				System.out.format("%.4f", nodes[i][j].stockPrice);

				System.out.print("\t\tPrice: : ");
				System.out.format("%.4f", nodes[i][j].price);
	
				System.out.print("\t\tFugit: ");
				System.out.format("%.4f", nodes[i][j].fugit);
				System.out.println();
			}
		}
	}
}