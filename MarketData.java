public final class MarketData {
	public double Price;	//Market price of security
	public double S;		//Stock price
	public double r;		//Interest rate
	public double sigma;	//Volatility
	public double t0;		//Current time
	
	MarketData(double Price, double S, double r, double sigma, double t0){
		this.Price = Price;
		this.S = S;
		this.r = r;
		this.sigma = sigma;
		this.t0 = t0;
	}
}
