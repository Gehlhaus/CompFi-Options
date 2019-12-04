package Project;

public final class Output {
	public double FV;		//Fair-Value
	public double fugit;	//fugit value
	public double impvol;	//implied volatility
	public int num_iter;	//Number of iterations to compute implied volatility
	
	Output(){
		FV = fugit = impvol = num_iter = -0;
	}
}
