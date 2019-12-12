public class VanillaOption extends Derivative {
	//Data members
	int put_call; 	// 0 == put, 1 == call 
	int US_EU;		// 0 == US, 1 == EU
	
	VanillaOption(int putCall, int USEU, double expiration, double strikePrice){
		put_call = putCall;
		US_EU = USEU;
		T = expiration;
		this.strike = strikePrice;
	}
	
	public void terminalCondition(Node n) {

		if(put_call == 0 ) { n.price = strike -  n.stockPrice; }
		else if(put_call == 1) { n.price = n.stockPrice - strike; }
		else { System.out.println("Invalid PUT/CALL choice"); }
		if(n.price <= 0 ) n.price = 0.0;
		n.fugit = T;
	}

	public void valuationTest(Node n) {
		// put
		if(put_call == 0 ) { 
			n.price = (1/erDeltaT) * ((p * n.uChild.price) + (q * n.dChild.price));
			n.fugit = (p * n.uChild.fugit) + (q * n.dChild.fugit); 
			if(n.price <= 0 ) n.price = 0.0;

			if(US_EU == 0) {
				double intrinsic = strike - n.stockPrice;
				if(intrinsic > n.price) {
					n.price = intrinsic;
					n.fugit = n.t;
				}
			}
		}
		// call
		else if(put_call == 1) {
			//System.out.println("erDeltaT = " + n.erDeltaT + ", n.p = " + n.p + ", n.q = " + n.q);
			//System.out.println(n.uChild.price + ", " + n.dChild.price);
			n.price = (1/erDeltaT) * ((p * n.uChild.price) + (q * n.dChild.price));
			n.fugit = (p * n.uChild.fugit) + (q * n.dChild.fugit);
			if(n.price <= 0 ) n.price = 0.0;
			
			if(US_EU == 0) {
				double intrinsic = n.stockPrice - strike;
				if(intrinsic > n.price) {
					n.price = intrinsic;
					n.fugit = n.t;
				}
			}
		} else {
			System.out.println("Invalid PUT/CALL choice");
		}
	}
}
