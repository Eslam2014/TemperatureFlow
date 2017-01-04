package fcis.asu.temperatureFlow;

import fcis.asu.utilities.Utilites;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum CellType {
	Block(Color.WHITESMOKE, 0.0, false,1), HeatSource(Color.RED, 100, false,4), ColdSource(Color.BLUE, 0.0,
			false,3), NormalCell(Color.LAWNGREEN, 50, true,0), Window(Color.LAWNGREEN, 50, false,2);

	@Setter
	private Color color;
	@Setter
	private double value;
	private boolean updatable;
	
	private int priority;

	private CellType(Color color, double value, boolean updatable,int priority) {
		this.color = color;
		this.value = value;
		this.updatable = updatable;
		this.priority=priority;
	}
	
	/**
	 * this method will set the data to cells base on minTemperature and maxTemperature
	 * @param minTemperature
	 * @param maxTemperature
	 */
	public static void setCellTypeData(double minTemperature, double maxTemperature) {
		ColdSource.setValue(minTemperature);
		HeatSource.setValue(maxTemperature);
		double midTemperature=((minTemperature+maxTemperature)/2)+minTemperature;
		NormalCell.setValue(midTemperature);
		Color color = Utilites.TransferFunction(midTemperature , minTemperature, maxTemperature);
		NormalCell.setColor(color);
		Window.setValue(midTemperature + minTemperature);
		Window.setColor(color);
	}

}
