import java.util.Random;


public class RandomVariable{
	double lambda, rv =0.0;
	public RandomVariable(double lambda){
		this.lambda =lambda;
	}
	public synchronized double nextValue(){
		double u = Math.random();
		rv = (-lambda)*(Math.log(1-u));
		return rv;
	}
}
