package Project;

public class BermudanOption extends VanillaOption {
	BermudanOption(int putCall, int USEU, double expiration, double strikePrice) {
		super(putCall, USEU, expiration, strikePrice);
	}
	public double window_begin;
	public double window_end;
	
	public void terminalCondition(Node n) {}
	public void valuationTest(Node n) {}
}
