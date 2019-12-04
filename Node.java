package Project;

public final class Node {
	double stockPrice, price, fugit, t;
	double p ,q;
	double erDeltaT, deltaT;
	
	boolean isLeaf;
	Node uChild, dChild;
	
	Node(double p, double q, double er, double deltaT){
		this.p = p;
		this.q = q;
		this.erDeltaT = er;
		this.deltaT = deltaT;
	}
}
