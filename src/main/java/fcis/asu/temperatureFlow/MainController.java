package fcis.asu.temperatureFlow;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Gauge.SkinType;
import fcis.asu.utilities.Utilites;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.util.Duration;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

public class MainController {

	@FXML
	private JFXRadioButton normal;
	@FXML
	private JFXSlider side;
	@FXML
	private JFXTextField min;
	@FXML
	private JFXRadioButton heatSource;
	@FXML
	private JFXTextField max;
	@FXML
	private ToggleGroup blockType;
	@FXML
	private JFXButton updateCells;
	@FXML
	private JFXRadioButton coldSource;
	@FXML
	private JFXRadioButton block;
	@FXML
	private JFXRadioButton window;
	@FXML
	private JFXButton srart;
	@FXML
	private JFXButton btnStop;
	@FXML
	private Canvas drawCanvas;

	@FXML
	private JFXButton updateColor;

	private static GraphicsContext gc;
	private double canvWeight;
	private double canvHeight;
	private int nCol;
	private int nRow;
	private double cellSize;
	private CellType cellType;
	private Timeline fiveSecondsWonder;
	private double minTemperature;
	private double maxTemperature;
	private double midColor;

	private static Room room;

	@FXML
	public void initialize() {
		minTemperature = Double.parseDouble(min.getText());
		maxTemperature = Double.parseDouble(max.getText());
		midColor = (maxTemperature - minTemperature) / 2;
		Color color = Utilites.TransferFunction(midColor, minTemperature, maxTemperature);
		reset(drawCanvas, color);
		temprature.getChildren().add(gauge5);

		gc = drawCanvas.getGraphicsContext2D();
		this.canvWeight = drawCanvas.getWidth();
		this.canvHeight = drawCanvas.getHeight();
		this.cellSize = Math.ceil(side.getValue());
		nCol = (int) Math.ceil(canvHeight / cellSize);
		nRow = (int) Math.ceil(canvWeight / cellSize);
		room = new Room(minTemperature, maxTemperature, nCol, nRow, (int) cellSize);

		// setCellTypeData(minTemperature,maxTemperature,midColor);
		// initializeCells(nCol, nRow, midColor);
		block.setSelected(true);
		setCellType(CellType.Block);
		blockType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
				// Cast object to radio button
				RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle();

				String tmpCellType = chk.getId();
				switch (tmpCellType) {
				case "block":
					setCellType(CellType.Block);
					break;
				case "heatSource":
					setCellType(CellType.HeatSource);
					break;
				case "coldSource":
					setCellType(CellType.ColdSource);
					break;
				case "normal":
					setCellType(CellType.NormalCell);
					break;
				case "window":
					setCellType(CellType.Window);
					break;
				default:
					break;
				}
			}
		});
	}


	@FXML
	void mouseClikedDraw(MouseEvent e) {
		try {
			drawCell(e.getX(), e.getY(), cellSize);

		} catch (Exception e2) {
		}

	}

	@FXML
	void mouseDraggedDraw(MouseEvent e) {
		try {
			drawCell(e.getX(), e.getY(), cellSize);

		} catch (Exception e2) {
		}
	}

	@FXML
	void stopVisul(ActionEvent event) {
		btnStop.setVisible(false);
		srart.setVisible(true);
		fiveSecondsWonder.stop();
		/*
		 * fiveSecondsWonder.setOnFinished(new EventHandler<ActionEvent>(){
		 * public void handle(ActionEvent event) {}
		 * 
		 * });
		 */

	}

	/**
	 * Resets the canvas to its original look by filling in a rectangle covering
	 * its entire width and height. Color.BLUE is used in this demo.
	 *
	 * @param canvas
	 *            The canvas to reset
	 * @param color
	 *            The color to fill
	 */
	private void reset(Canvas canvas, Color color) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(color);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.closePath();
	}

	private void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	private CellType getCellType() {
		return this.cellType;
	}

	private void drawCell(double x, double y, double cellSize) {

		int colIndex = (int) (-Math.floor(-(double) x / cellSize) - 1);
		int rowIndex = (int) (-Math.floor(-(double) y / cellSize) - 1);

		room.updateCellTypeOfCell(colIndex, rowIndex, getCellType());

		// cells[colIndex][rowIndex].setCellType(getCellType());

		double tmpX = colIndex * (int) cellSize;
		double tmpY = rowIndex * (int) cellSize;
		/*
		 * gc.beginPath(); gc.moveTo(tmpX, tmpY); gc.closePath();
		 */
		gc.setFill(getCellType().getColor());
		gc.fillRect(tmpX, tmpY, (int) cellSize, (int) cellSize);

		if (getCellType().equals(CellType.Window)) {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.strokeRoundRect(tmpX + 1, tmpY + 1, cellSize - 2, cellSize - 2, 8, 8);
			gc.stroke();
		}

	}

	@FXML
	void visual(ActionEvent event) {

		srart.setVisible(false);
		btnStop.setVisible(true);
		fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				room.updateTemperature();
			}
		}));
		fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fiveSecondsWonder.play();
		// updateHeats();

	}

	protected static void redraw(int x, int y, double temperature, int cellSize, double minTemperature,
			double maxTemperature) {
		double tmpX = x * (int) cellSize;
		double tmpY = y * (int) cellSize;
		/*
		 * gc.beginPath(); gc.moveTo(tmpX, tmpY); gc.closePath();
		 */
		gc.setFill(Utilites.TransferFunction(temperature, minTemperature, maxTemperature));
		gc.fillRect(tmpX, tmpY, (int) cellSize, (int) cellSize);

		if (room.getCellTypeOfCell(x, y).equals(CellType.Window)) {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.strokeRoundRect(tmpX + 1, tmpY + 1, cellSize - 2, cellSize - 2, 8, 8);
			gc.stroke();
		}

	}

	@FXML
	private Pane temprature;

	private Gauge gauge5 = GaugeBuilder.create().skinType(SkinType.DASHBOARD).animated(true).title("Dashboard")
			.unit("\u00B0C").maxValue(100).barColor(Color.CRIMSON).valueColor(Color.DEEPSKYBLUE)
			.titleColor(Color.DEEPSKYBLUE).unitColor(Color.RED).thresholdVisible(true).threshold(50)
			.shadowsEnabled(true).gradientBarEnabled(true)
			.gradientBarStops(new Stop(0.00, Color.BLUE), new Stop(0.25, Color.CYAN), new Stop(0.50, Color.LIME),
					new Stop(0.75, Color.YELLOW), new Stop(1.00, Color.RED))
			.build();

	@FXML
	void mouseMove(MouseEvent e) {

		int indexX = (int) (-Math.floor(-(double) e.getX() / cellSize) - 1);
		int indexY = (int) (-Math.floor(-(double) e.getY() / cellSize) - 1);
		try {
			gauge5.setValue(room.getCellTemprature(indexX, indexY));

		} catch (Exception e2) {

			System.out.println("out of range");
		}

	}

	@FXML
	void updateView(ActionEvent event) {
		double tmpCellHight = Math.ceil(side.getValue());
		int tmpNCellX = (int) Math.ceil(canvHeight / tmpCellHight);
		int tmpNCellY = (int) Math.ceil(canvWeight / tmpCellHight);
		// Cell[][] newCells = new Cell[tmpNCellX][tmpNCellY];

		// System.out.println(side.getValue());
		// reInitializeCells(tmpNCellX, tmpNCellY, newCells);

		room.reconstructionCells(tmpNCellX, tmpNCellY, (int) tmpCellHight);
	}

	public static void draw(int i, int j, int cellSize, double minTemperature, double maxTemperature,
			double midTemperature) {
		double tmpX = i * cellSize;
		double tmpY = j * cellSize;
		/*
		 * gc.beginPath(); gc.moveTo(tmpX, tmpY); gc.closePath();
		 */
		gc.setFill(Utilites.TransferFunction(midTemperature, minTemperature, maxTemperature));
		gc.fillRect(tmpX, tmpY, cellSize, cellSize);
	}

	public static void drawCell(int i, int j, Cell cell, int cellSize, double minTemperature, double maxTemperature) {
		double tmpX = i * (int) cellSize;
		double tmpY = j * (int) cellSize;
		/*
		 * gc.beginPath(); gc.moveTo(tmpX, tmpY); gc.closePath();
		 */
		if (cell.getCellType().equals(CellType.Block)) {
			gc.setFill(Color.WHITESMOKE);
			gc.fillRect(tmpX, tmpY, (int) cellSize, (int) cellSize);
		} else {
			gc.setFill(Utilites.TransferFunction(cell.getTemprature(), minTemperature, maxTemperature));
			gc.fillRect(tmpX, tmpY, (int) cellSize, (int) cellSize);
		}
		if (cell.getCellType().equals(CellType.Window)) {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.strokeRoundRect(tmpX + 1, tmpY + 1, cellSize - 2, cellSize - 2, 8, 8);
			gc.stroke();
		}
	}

	public static void updateCellSize(int x, int y, int cellHight, double minTemperature, double maxTemperature) {

		double tmpX = x * (int) cellHight;
		double tmpY = y * (int) cellHight;
		/*
		 * gc.beginPath(); gc.moveTo(tmpX, tmpY); gc.closePath();
		 */
		gc.setFill(Utilites.TransferFunction(room.getCellTemprature(x, y), minTemperature, maxTemperature));
		gc.fillRect(tmpX, tmpY, cellHight, cellHight);

		if (room.getCellTypeOfCell(x, y).equals(CellType.Window)) {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.strokeRoundRect(tmpX + 1, tmpY + 1, cellHight - 2, cellHight - 2, 8, 8);
			gc.stroke();
		}

	}

	@FXML
	void updateColors(ActionEvent event) {
		double tmpMinColor = Double.parseDouble(min.getText());
		double tmpMaxColor = Double.parseDouble(max.getText());
		double tmpMidColor = (tmpMaxColor - tmpMinColor) / 2;
		gauge5.setMinValue(tmpMinColor);
		gauge5.setMaxValue(tmpMaxColor);
		gauge5.setThreshold(tmpMidColor + tmpMinColor);
		/*
		 * gauge5.setGradientBarStops(new Stop(0.00, Color.BLUE), new Stop(0.25,
		 * Color.CYAN), new Stop(0.50, Color.LIME), new Stop(0.75,
		 * Color.YELLOW), new Stop(1.00, Color.RED));
		 */
		room.resetTemperature(tmpMinColor, tmpMaxColor);
		this.midColor = tmpMidColor + minTemperature;
		this.maxTemperature = tmpMaxColor;
		this.minTemperature = tmpMinColor;

	}

}
