package Project;

public class VanillaOption extends Derivative {
	//Data members
	double strikePrice, p, q, erDelaT;
	int put_call; 	// 0 == put, 1 == call 
	int US_EU;		// 0 == US, 1 == EU
	
	VanillaOption(int putCall, int USEU, double expiration, double strikePrice){
		put_call = putCall;
		US_EU = USEU;
		T = expiration;
		this.strikePrice = strikePrice;
	}
	
	public void terminalCondition(Node n) {
		
		// put
		if(put_call == 0 ) { 
			n.price = (1/n.erDeltaT) * ((n.p * n.uChild.price) + (n.q * n.dChild.price));
			n.fugit = (n.p * n.uChild.fugit) + (n.q * n.dChild.fugit);
			if(US_EU == 0) {
				double intrinsic = n.stockPrice - strikePrice;
				if(intrinsic > n.price) n.price = intrinsic;	
			}
			if(n.price <= 0 ) n.price = 0.0;
		}
		// call
		else if(put_call == 1) {
			n.price = (1/n.erDeltaT) * ((n.p * n.uChild.price) + (n.q * n.dChild.price));
			n.fugit = (n.p * n.uChild.fugit) + (n.q * n.dChild.fugit);
			if(US_EU == 0) {
				double intrinsic = strikePrice -  n.stockPrice;
				if(intrinsic > n.price) n.price = intrinsic;
			}
			if(n.price <= 0 ) n.price = 0.0;
				
		} else {
			System.out.println("Invalid PUT/CALL choice");
		}
	}

	public void valuationTest(Node n) {
		if(n.isLeaf) {
			// put
			if(put_call == 0 ) { 
				n.price = strikePrice - n.stockPrice;
				if(n.price <= 0 ) n.price = 0.0;
				n.fugit = T;
				n.t = T;
			}
			// call
			else if(put_call == 1) {
				n.price = n.stockPrice - strikePrice;
				if(n.price <= 0 ) n.price = 0.0;
				n.fugit = T;
				n.t = T;
			} else {
				System.out.println("Invalid PUT/CALL choice");
			}	
		} else {
			// put
			if(put_call == 0 ) { 
				n.price = (1/n.erDeltaT) * ((n.p * n.uChild.price) + (n.q * n.dChild.price));
				n.t = n.uChild.t - n.deltaT;
				n.fugit = (n.p * n.uChild.fugit) + (n.q * n.dChild.fugit); 
				
				if(US_EU == 0) {
					double intrinsic = strikePrice - n.stockPrice;
					//System.out.println("n.price = " + n.price + ", intrinsic = " + intrinsic);
					if(intrinsic > n.price) {
						System.out.println("INTRINSICCC");
						//System.out.println("overriding price, n.t = " + n.t);
						n.price = intrinsic;
						n.fugit = n.t;
					}
				}
				if(n.price <= 0 ) n.price = 0.0;
			}
			// call
			else if(put_call == 1) {
				//System.out.println("erDeltaT = " + n.erDeltaT + ", n.p = " + n.p + ", n.q = " + n.q);
				n.price = (1/n.erDeltaT) * ((n.p * n.uChild.price) + (n.q * n.dChild.price));
				n.t = n.uChild.t - n.deltaT;
				n.fugit = (n.p * n.uChild.fugit) + (n.q * n.dChild.fugit);
				
				if(US_EU == 0) {
					double intrinsic = n.stockPrice - strikePrice;
					if(intrinsic > n.price) {
						System.out.println("INTRINSICCCCCCCCCC");
						n.price = intrinsic;
						n.fugit = n.t;
					}
				}
				if(n.price <= 0 ) n.price = 0.0;
			} else {
				System.out.println("Invalid PUT/CALL choice");
			}
		}
	}
}
