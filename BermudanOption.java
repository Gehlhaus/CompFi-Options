public class BermudanOption extends Derivative {
	static double window_begin;
	static double window_end;
	int put_call; 	// 0 == put, 1 == call
	
	BermudanOption(int putCall, double expiration, double strikePrice, double window_begin, double window_end){
		put_call = putCall;
		T = expiration;
		this.strike = strikePrice;
		BermudanOption.window_begin = window_begin;
		BermudanOption.window_end = window_end;
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

			if(validWindow(n)) {
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
			
			if(validWindow(n)) {
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
	
	static boolean validWindow(Node n){
		if (n.t >= window_begin && n.t <= (window_end + .000001)){
			return true;
		}
		return false;
	}
}
