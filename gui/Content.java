package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;

import javax.swing.JComponent;
import javax.swing.Timer;

import org.apache.commons.math3.complex.Complex;

import junk.ComplexGrid;
import junk.Polynomial;
import junk.ReimannZeta;

import static org.apache.commons.math3.complex.Complex.*;

@SuppressWarnings("unused")
public class Content extends JComponent {
	private static final long serialVersionUID = 1L;

	private static final ComplexGrid grid = new ComplexGrid(new Complex(-5, -4), new Complex(5, 4));

	private List<ComplexGrid> lottaGrids;
	private AtomicInteger atomInt = new AtomicInteger();

	public Content() {
		super();

		var rzeta = new ReimannZeta(100);
		var xsqr1 = new Polynomial(0, 3, 1, -2);

		UnaryOperator<Complex> func = Complex::tanh;
		lottaGrids = ComplexGrid.getInterpolation(grid, func, 360);
	}

	public void magicInterpolation() {
		Timer t = new Timer((int) (1000 / 60.0), (e) -> {
			if (atomInt.get() < lottaGrids.size() - 1)
				atomInt.incrementAndGet();

			repaint();
		});

		t.start();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	@Override
	protected void paintComponent(Graphics g) {
		/*
		 * TODO can functions of complex numbers as line transformations be represented
		 * as elementary parametric functions?
		 */

		Graphics2D g2 = (Graphics2D) g;

		g2.fillRect(0, 0, 1000, 1000);

		g2.setColor(Color.YELLOW);
		g2.drawLine(0, 300, 800, 300);
		g2.drawLine(400, 0, 400, 600);

		g2.setColor(new Color(100, 100, 100));

		var currentGrid = lottaGrids.get(atomInt.get());
		for (var line : currentGrid.getLines()) {
			var iter = line.iterator();
			Complex z1 = iter.next();
			Complex z2 = iter.next();

			do {
				g2.drawLine(400 + (int) (100 * z1.getReal()), 300 + (int) (100 * z1.getImaginary()),
						400 + (int) (100 * z2.getReal()), 300 + (int) (100 * z2.getImaginary()));

				z1 = z2;
				z2 = iter.next();
			} while (iter.hasNext());
		}

		// g2.setColor(Color.RED);
		// var p = currentGrid.getLines().get(20).get(500);
		// int x = 400 + (int) (100 * p.getReal());
		// int y = 300 + (int) (100 * p.getImaginary());
		// g2.fillOval(x - 4, y - 4, 8, 8);
	}
}
