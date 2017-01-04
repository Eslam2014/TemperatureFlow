package fcis.asu.temperatureFlow;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell {
	private CellType cellType;
	private double temprature;
	
	public Cell(CellType cellType,double temprature) {
		this.cellType=cellType;
		this.temprature=temprature;
	}
	
	public void setCellType(CellType cellType){
		this.cellType=cellType;
		this.temprature=this.cellType.getValue();
	}
	
	public double updateTemprature(double oldRange,double newRange,double oldMin,double newMin){
		
				this.temprature=(((temprature - oldMin) * newRange) / oldRange) + newMin;
				return temprature;
	}
	
	
	

}
