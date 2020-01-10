package junk;

import java.util.function.UnaryOperator;

import org.apache.commons.math3.complex.Complex;

public class Polynomial implements UnaryOperator<Complex>
{
	private final double[] a;
	
	public Polynomial(double ... coefficients)
	{
		a = coefficients;
	}
	
	@Override
	public Complex apply(Complex t)
	{
		Complex sum = Complex.ZERO;
		
		for (int n = 0; n < a.length; n++)
			sum = sum.add(new Complex(a[n]).multiply(t.pow(n)));
			
		return sum;
	}
}
