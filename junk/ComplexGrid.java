package junk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.apache.commons.math3.complex.Complex;

public class ComplexGrid
{
	private final ArrayList<ArrayLine> lines = new ArrayList<>();

	/**
	 * Creates a ComplexGrid
	 * 
	 * TODO better JavaDoc
	 * 
	 * @param ll	the lower-bounds of the real and imaginary axes
	 * @param ur	the upper-bounds of the real and imaginary axes
	 */
	public ComplexGrid(Complex ll, Complex ur)
	{
		double	aMin = ll.getReal(), aMax = ur.getReal(),
				bMin = ll.getImaginary(), bMax = ur.getImaginary();

		// Creates vertical grid lines consisting of several points
		for (double a = aMin; a <= aMax; a += 0.2)
			lines.add(new ArrayLine(
					new Complex(a, bMin),
					new Complex(a, bMax), 600));

		// Creates horizontal grid lines consisting of several points
		for (double b = bMin; b <= bMax; b += 0.2)
			lines.add(new ArrayLine(
					new Complex(aMin, b),
					new Complex(aMax, b), 800));
	}

	private ComplexGrid()
	{
		// intentionally blank default constructor
	}

	public List<ArrayLine> getLines()
	{
		return Collections.unmodifiableList(lines);
	}

	public ComplexGrid applyFunction(UnaryOperator<Complex> func)
	{
		ComplexGrid newGrid = new ComplexGrid();

		newGrid.lines.addAll(this.lines.stream()
				.map(l -> l.applyFunction(func)).collect(Collectors.toList()));

		return newGrid;
	}

	public static List<ComplexGrid> getInterpolation(
			ComplexGrid start, UnaryOperator<Complex> func, int numFrames)
	{
		ComplexGrid finish = start.applyFunction(func);
		LinkedList<ComplexGrid> frames = new LinkedList<>();
		
		for (int n = 0; n < numFrames; n++)
		{
			ComplexGrid frameN = new ComplexGrid();
			
			for (int i = 0; i < start.lines.size(); i++)
			{
				var lineS = start.lines.get(i);
				var lineF = finish.lines.get(i);
				var lineN = new ArrayLine(lineS.size());
				
				for (int j = 0; j < lineS.size(); j++)
				{
					var pointS = lineS.get(j);
					var pointF = lineF.get(j);
					
					// https://www.desmos.com/calculator/hd98vniruf
					
					Complex weightedAvg =
									pointF.multiply((double) n / numFrames)
							.add(
									pointS.multiply(1 - ((double) n / numFrames))
							);
					
					lineN.add(weightedAvg);
				}
				
				frameN.lines.add(lineN);
			}
			
			frames.add(frameN);
		}

		frames.addFirst(start);
		frames.addLast(finish);
		
		return frames;
	}
}
