package Project;

public final class Library {
	//Computes the Fair-Value(FV) and Fugit of a derivative
	static Output binom(final Derivative deriv, final MarketData mkt, int n) {
		Output out = new Output();
		
		Node[][] nodes = new Node[n+1][];
		
		for(int i=0; i<nodes.length; i++){
			nodes[i] = new Node[i+1];
		}
		
		double deltaT = ((deriv.T-mkt.t0)/n);
		double u = Math.pow(Math.E, (mkt.sigma * Math.sqrt(deltaT)));
		double d = 1.0/u;
		double p = (Math.pow(Math.E, mkt.r * deltaT) - d)/(u - d);
		double q = 1 - p;
		double erDeltaT = Math.pow(Math.E, (mkt.r * deltaT));
		
		for(int i=0; i<nodes.length; i++){
			for(int j=0; j<nodes[i].length; j++){
				nodes[i][j] = new Node(p, q, erDeltaT, deltaT);
				int value = i-(2*j);
				if(value == 0) nodes[i][j].stockPrice = mkt.S;
				else if(value > 0) nodes[i][j].stockPrice = mkt.S * Math.pow(u, value);
				else if(value < 0) nodes[i][j].stockPrice = mkt.S * Math.pow(d, Math.abs(value));
			}
		}

		for(int i=0; i<nodes[nodes.length-1].length; i++) {
			nodes[nodes.length-1][i].isLeaf = true;
			nodes[nodes.length-1][i].isLeaf = true;
		}
		for(int i=nodes.length-2; i>=0; i--) {
			for(int j=0; j<nodes[i].length; j++) {
				nodes[i][j].uChild = nodes[i+1][j];
				nodes[i][j].dChild = nodes[i+1][j+1];
			}
		}
			
		for(int i=nodes.length-1; i>0; i--) {
			for(int j=0; j<nodes[i].length; j++) {
				deriv.valuationTest(nodes[i][j]);
			}
		}
		deriv.terminalCondition(nodes[0][0]);
		out.FV = nodes[0][0].price;
		
		print(nodes);
		
		return out;
	}
	
	//Executes a loop of iterations of the Binom function to calculate the implied volatility of a derivative 
	static int impvol(final Derivative deriv, final MarketData mkt, int n, int max_iter, double tol, Output out) {
		//low 1% high 200%
		int counter = 0;
		double temp, vLow = .01, vHigh = 2.0;
		while(counter < max_iter) {
			
			counter++;
			out.num_iter = counter;
			out.impvol = vLow;
			return 0; 	//Success	
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