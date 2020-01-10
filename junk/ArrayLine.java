package junk;

import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.apache.commons.math3.complex.Complex;

/**
 * Super sketchy class that I should reconsider using
 */
public class ArrayLine extends ArrayList<Complex>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a line that ranges between two end points that is a
	 * straight, continuous line.
	 *
	 * The real and imaginary values of the left endpoint MUST be less than
	 * those of the right endpoint.
	 *
	 * @param min
	 * @param max
	 * @param numPoints
	 */
	public ArrayLine(Complex min, Complex max, int numPoints)
	{
		super(numPoints);

		var diff = max.subtract(min);

		if (diff.getReal() < 0 || diff.getImaginary() < 0)
			throw new IllegalArgumentException("Min greater than max");

		double
			aMin	= min.getReal(),
			aMax	= max.getReal(),
			aDelta	= (aMax - aMin) / numPoints,
			bMin	= min.getImaginary(),
			bMax	= max.getImaginary(),
			bDelta	= (bMax - bMin) / numPoints;

		for (double a = aMin, b = bMin; a < aMax || b < bMax; a += aDelta, b+= bDelta)
				add(new Complex(a, b));

		add(new Complex(aMax, bMax));
	}

	public ArrayLine()
	{
		super();
	}

	public ArrayLine(int initialCapacity)
	{
		super(initialCapacity);
	}

	public ArrayLine applyFunction(UnaryOperator<Complex> func)
	{
		ArrayLine transformed = new ArrayLine();

		transformed.addAll(
				this.stream().map(func).collect(Collectors.toList()));

		return transformed;
	}
}
