package fcis.asu.utilities;

import javafx.scene.paint.Color;

public class Utilites {

	public static Color[] ColorsList = new Color[] { Color.BLUE, Color.AQUA,Color.LAWNGREEN, Color.ORANGE, Color.RED };

	public static Color TransferFunction(double Val, double Smin, double Smax) {
		double r;
		double g;
		double b;
		double DeltaS = (Smax - Smin) / 4;
		
		double Ds = (double)(Val - Smin) / DeltaS;
		double i = Math.floor(Ds);
		double alpha = Ds - i;

		// { Color.Red, Color.Orange, Color.Yellow, Color.Green, Color.Blue }

		r = g = b = -1;

		int tmp = (int) i;
		if (tmp != 4) {
			r = ColorsList[tmp].getRed() + alpha * (ColorsList[tmp + 1].getRed() - ColorsList[tmp].getRed());
			g = ColorsList[tmp].getGreen() + (alpha * (ColorsList[tmp + 1].getGreen() - ColorsList[tmp].getGreen()));
			b = ColorsList[tmp].getBlue() + alpha * (ColorsList[tmp + 1].getBlue() - ColorsList[tmp].getBlue());
			//System.out.println(i+":"+Val+":"+r+":"+g+":"+b);

			return new Color(r, g, b, 1);
		} else {
			return ColorsList[tmp];
		}

		
	}

}
