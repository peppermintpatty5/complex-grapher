package junk;

import java.util.function.UnaryOperator;

import org.apache.commons.math3.complex.Complex;

public class ReimannZeta implements UnaryOperator<Complex>
{
	private final int N;
	
	public ReimannZeta(int iterations)
	{
		N = iterations;
	}
	
	@Override
	public Complex apply(Complex t)
	{
		Complex sum = Complex.ZERO;
		
		for (int n = 1; n < N; n++)
			sum = sum.add(new Complex(n).pow(t.negate()));
		
		return sum;
	}
}
