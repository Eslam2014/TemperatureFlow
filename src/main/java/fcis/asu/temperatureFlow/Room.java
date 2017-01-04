package fcis.asu.temperatureFlow;

public class Room {
	private Cell[][] cells;
	private double minTemperature;
	private double maxTemperature;
	private double midTemperature;
	private int nCol;
	private int nRow;
	private int cellSize;

	public Room(double minTemperature, double maxTemperature, int nCol, int nRow, int cellSize) {
		this.cellSize = cellSize;
		this.nCol = nCol;
		this.nRow = nRow;
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
		this.midTemperature = ((maxTemperature + minTemperature) / 2) + minTemperature;
		CellType.setCellTypeData(minTemperature, maxTemperature);
		initializeCells(nCol, nRow, maxTemperature);

	}

	private void initializeCells(int nCol, int nRow, double midTemperature) {
		cells = new Cell[nCol][nRow];
		for (int i = 0; i < nCol; i++) {
			for (int j = 0; j < nRow; j++) {
				cells[i][j] = new Cell(CellType.NormalCell, midTemperature);
			}
		}
	}

	public void updateCellTypeOfCell(int colIndex, int rowIndex, CellType cellType) {
		this.cells[colIndex][rowIndex].setCellType(cellType);
	}

	public void updateTemperature() {
		Cell[][] newCells = new Cell[(int) nCol][(int) nRow];
		updateLeftColumn(newCells);
		updateRightColumn(newCells);
		updateTopRow(newCells);
		updateBottomRow(newCells);
		updateCenter(newCells);

		for (int i = 0; i < nCol; i++) {
			for (int j = 0; j < nRow; j++) {
				cells[i][j] = newCells[i][j];
			}
		}

	}

	private void updateLeftColumn(Cell[][] newCells) {
		double tmpValue = 0;
		// update top corner
		if (cells[0][0].getCellType().isUpdatable()) {
			tmpValue = getAverageCellInLeftTopCorner();
			newCells[0][0] = new Cell(cells[0][0].getCellType(), tmpValue);
			MainController.redraw(0, 0, tmpValue, cellSize, minTemperature, maxTemperature);
		} else {
			newCells[0][0] = new Cell(cells[0][0].getCellType(), cells[0][0].getTemprature());
		}
		// update bottom corner
		if (cells[0][nRow - 1].getCellType().isUpdatable()) {
			tmpValue = getAverageCellInLeftBottomCorner();
			newCells[0][nRow - 1] = new Cell(cells[0][nRow - 1].getCellType(), tmpValue);
			MainController.redraw(0, nRow - 1, tmpValue, cellSize, minTemperature, maxTemperature);
		} else {
			newCells[0][nRow - 1] = new Cell(cells[0][nRow - 1].getCellType(), cells[0][nRow - 1].getTemprature());
		}

		// update middle
		int nYCell = nRow - 1;
		for (int i = 1; i < nYCell; i++) {

			if (cells[0][i].getCellType().isUpdatable()) {
				tmpValue = getAverageCellInLeftColumn(i);
				newCells[0][i] = new Cell(cells[0][i].getCellType(), tmpValue);
				MainController.redraw(0, i, tmpValue, cellSize, minTemperature, maxTemperature);
			} else {
				newCells[0][i] = new Cell(cells[0][i].getCellType(), cells[0][i].getTemprature());
			}

		}

	}

	private void updateRightColumn(Cell[][] newCells) {
		double tmpValue = 0;
		// update top corner
		if (cells[nCol - 1][0].getCellType().isUpdatable()) {
			tmpValue = getAverageCellInRightTopCorner();
			newCells[nCol - 1][0] = new Cell(cells[nCol - 1][0].getCellType(), tmpValue);
			MainController.redraw(nCol - 1, 0, tmpValue, cellSize, minTemperature, maxTemperature);
		} else {
			newCells[nCol - 1][0] = new Cell(cells[nCol - 1][0].getCellType(), cells[nCol - 1][0].getTemprature());
		}

		// update bottom corner
		if (cells[nCol - 1][nRow - 1].getCellType().isUpdatable()) {
			tmpValue = getAverageCellInRightBottomCorner();
			newCells[nCol - 1][nRow - 1] = new Cell(cells[nCol - 1][nRow - 1].getCellType(), tmpValue);
			MainController.redraw(nCol - 1, nRow - 1, tmpValue, cellSize, minTemperature, maxTemperature);
		} else {
			newCells[nCol - 1][nRow - 1] = new Cell(cells[nCol - 1][nRow - 1].getCellType(),
					cells[nCol - 1][nRow - 1].getTemprature());
		}
		// update middle

		int nYCell = nRow - 1;
		int nXCell = nCol - 1;
		for (int i = 1; i < nYCell; i++) {
			if (cells[nXCell][i].getCellType().isUpdatable()) {
				tmpValue = getAverageCellInRightColumn(i);
				newCells[nXCell][i] = new Cell(cells[nXCell][i].getCellType(), tmpValue);
				MainController.redraw(nXCell, i, tmpValue, cellSize, minTemperature, maxTemperature);

			} else {
				newCells[nXCell][i] = new Cell(cells[nXCell][i].getCellType(), cells[nXCell][i].getTemprature());

			}

		}

	}

	private void updateTopRow(Cell[][] newCells) {
		int nCell = (int) nRow - 1;
		double tmpValue = 0;
		for (int i = 1; i < nCell; i++) {
			if (cells[i][0].getCellType().isUpdatable()) {
				tmpValue = getAverageCellInTopRow(i);
				newCells[i][0] = new Cell(cells[i][0].getCellType(), tmpValue);
				MainController.redraw(i, 0, tmpValue, cellSize, minTemperature, maxTemperature);

			} else {
				newCells[i][0] = new Cell(cells[i][0].getCellType(), cells[i][0].getTemprature());
			}

		}

	}

	private void updateBottomRow(Cell[][] newCells) {
		int nXCell = nCol - 1;
		int nYCell = nRow - 1;
		double tmpValue = 0;
		for (int i = 1; i < nXCell; i++) {
			if (cells[i][nYCell].getCellType().isUpdatable()) {
				tmpValue = getAverageCellInBottomRow(i);
				newCells[i][nYCell] = new Cell(cells[i][nYCell].getCellType(), tmpValue);
				MainController.redraw(i, nYCell, tmpValue, cellSize, minTemperature, maxTemperature);
			} else {
				newCells[i][nYCell] = new Cell(cells[i][nYCell].getCellType(), cells[i][nYCell].getTemprature());

			}

		}

	}

	private void updateCenter(Cell[][] newCells) {
		double tmpValue = 0;
		for (int i = 1; i < nCol - 1; i++) {
			for (int j = 1; j < nRow - 1; j++) {
				if (cells[i][j].getCellType().isUpdatable()) {
					tmpValue = getAverageCellInCenter(i, j);
					newCells[i][j] = new Cell(cells[i][j].getCellType(), tmpValue);
					MainController.redraw(i, j, tmpValue, cellSize, minTemperature, maxTemperature);
				} else {
					newCells[i][j] = new Cell(cells[i][j].getCellType(), cells[i][j].getTemprature());
				}

			}
		}

	}

	private double getAverageCellInLeftColumn(int y) {
		boolean rightTop = false;
		boolean rightBottom = false;
		int counter = 1;
		double sum = cells[0][y].getTemprature();
		// bottom
		if (!cells[0][y + 1].getCellType().equals(CellType.Block)) {
			rightBottom = true;
			counter++;
			sum += cells[0][y + 1].getTemprature();
		}
		// top
		if (!cells[0][y - 1].getCellType().equals(CellType.Block)) {
			rightTop = true;
			counter++;
			sum += cells[0][y - 1].getTemprature();
		}
		// left
		if (!cells[1][y].getCellType().equals(CellType.Block)) {
			rightBottom = true;
			rightTop = true;
			counter++;
			sum += cells[1][y].getTemprature();
		}
		// topLeft
		if (!cells[1][y - 1].getCellType().equals(CellType.Block) && rightTop) {
			counter++;
			sum += cells[1][y - 1].getTemprature();
		}
		// bottomRight
		if (!cells[1][y + 1].getCellType().equals(CellType.Block) && rightBottom) {
			counter++;
			sum += cells[1][y + 1].getTemprature();
		}

		return (double) sum / counter;

	}

	private double getAverageCellInRightColumn(int y) {
		boolean letftTop = false;
		boolean leftBottom = false;
		int counter = 1;
		double sum = cells[nCol - 1][y].getTemprature();
		// bottom
		if (!cells[nCol - 1][y + 1].getCellType().equals(CellType.Block)) {
			leftBottom = true;
			counter++;
			sum += cells[nCol - 1][y + 1].getTemprature();
		}
		// top
		if (!cells[nCol - 1][y - 1].getCellType().equals(CellType.Block)) {
			letftTop = true;
			counter++;
			sum += cells[nCol - 1][y - 1].getTemprature();
		}
		// right
		if (!cells[nCol - 2][y].getCellType().equals(CellType.Block)) {
			leftBottom = true;
			letftTop = true;
			counter++;
			sum += cells[nCol - 2][y].getTemprature();
		}
		// topRight
		if (!cells[nCol - 2][y - 1].getCellType().equals(CellType.Block) && letftTop) {
			counter++;
			sum += cells[nCol - 2][y - 1].getTemprature();
		}
		// bottomRight
		if (!cells[nCol - 2][y + 1].getCellType().equals(CellType.Block) && leftBottom) {
			counter++;
			sum += cells[nCol - 2][y + 1].getTemprature();
		}

		return (double) sum / counter;

	}

	private double getAverageCellInTopRow(int x) {
		boolean rightBottom = false;
		boolean leftBottom = false;
		int counter = 1;
		double sum = cells[x][0].getTemprature();
		// right
		if (!cells[x + 1][0].getCellType().equals(CellType.Block)) {
			rightBottom = true;
			counter++;
			sum += cells[x + 1][0].getTemprature();
		}
		// left
		if (!cells[x - 1][0].getCellType().equals(CellType.Block)) {
			leftBottom = true;
			counter++;
			sum += cells[x - 1][0].getTemprature();
		}
		// bottom
		if (!cells[x][1].getCellType().equals(CellType.Block)) {
			rightBottom = true;
			leftBottom = true;
			counter++;
			sum += cells[x][1].getTemprature();
		}
		// bottom-left
		if (!cells[x - 1][1].getCellType().equals(CellType.Block) && leftBottom) {
			counter++;
			sum += cells[x - 1][1].getTemprature();
		}
		// bottom-right
		if (!cells[x + 1][1].getCellType().equals(CellType.Block) && rightBottom) {
			counter++;
			sum += cells[x + 1][1].getTemprature();
		}
		return (double) sum / counter;

	}

	private double getAverageCellInBottomRow(int x) {
		boolean letftTop = false;
		boolean rightTop = false;
		int counter = 1;
		double sum = cells[x][nRow - 1].getTemprature();
		// right
		if (!cells[x + 1][nRow - 1].getCellType().equals(CellType.Block)) {
			rightTop = true;
			counter++;
			sum += cells[x + 1][nRow - 1].getTemprature();
		}
		// left
		if (!cells[x - 1][nRow - 1].getCellType().equals(CellType.Block)) {
			letftTop = true;
			counter++;
			sum += cells[x - 1][nRow - 1].getTemprature();
		}
		// top
		if (!cells[x][nRow - 2].getCellType().equals(CellType.Block)) {
			rightTop = true;
			letftTop = true;
			counter++;
			sum += cells[x][nRow - 2].getTemprature();
		}
		// top-left
		if (!cells[x - 1][nRow - 2].getCellType().equals(CellType.Block) && letftTop) {
			counter++;
			sum += cells[x - 1][nRow - 2].getTemprature();
		}
		// top-right
		if (!cells[x + 1][nRow - 2].getCellType().equals(CellType.Block) && rightTop) {
			counter++;
			sum += cells[x + 1][nRow - 2].getTemprature();
		}

		return (double) sum / counter;

	}

	private double getAverageCellInCenter(int x, int y) {
		boolean topLeft = false;
		boolean topRight = false;
		boolean bottomLeft = false;
		boolean bottomRight = false;
		int counter = 1;
		double sum = cells[x][y].getTemprature();
		// top
		if (!cells[x][y - 1].getCellType().equals(CellType.Block)) {
			topLeft = true;
			topRight = true;
			counter++;
			sum += cells[x][y - 1].getTemprature();
		}
		// bottom
		if (!cells[x][y + 1].getCellType().equals(CellType.Block)) {
			bottomLeft = true;
			bottomRight = true;
			counter++;
			sum += cells[x][y + 1].getTemprature();
		}
		// left
		if (!cells[x - 1][y].getCellType().equals(CellType.Block)) {
			topLeft = true;
			bottomLeft = true;
			counter++;
			sum += cells[x - 1][y].getTemprature();
		}
		// right
		if (!cells[x + 1][y].getCellType().equals(CellType.Block)) {
			topRight = true;
			bottomRight = true;
			counter++;
			sum += cells[x + 1][y].getTemprature();
		}
		// top-left
		if (!cells[x - 1][y - 1].getCellType().equals(CellType.Block) && topLeft) {
			counter++;
			sum += cells[x - 1][y - 1].getTemprature();
		}
		// top-right
		if (!cells[x + 1][y - 1].getCellType().equals(CellType.Block) && topRight) {
			counter++;
			sum += cells[x + 1][y - 1].getTemprature();
		}
		// bottom-left
		if (!cells[x - 1][y + 1].getCellType().equals(CellType.Block) && bottomLeft) {
			counter++;
			sum += cells[x - 1][y + 1].getTemprature();
		}
		// bottom-right
		if (!cells[x + 1][y + 1].getCellType().equals(CellType.Block) && bottomRight) {
			counter++;
			sum += cells[x + 1][y + 1].getTemprature();
		}
		return (double) sum / counter;

	}

	private double getAverageCellInLeftTopCorner() {
		boolean bottomRight = false;
		int counter = 1;
		double sum = cells[0][0].getTemprature();
		// bottom
		if (!cells[0][1].getCellType().equals(CellType.Block)) {
			bottomRight = true;
			counter++;
			sum += cells[0][1].getTemprature();
		}
		// right
		if (!cells[1][0].getCellType().equals(CellType.Block)) {
			bottomRight = true;
			counter++;
			sum += cells[1][0].getTemprature();
		}
		// bottom-right
		if (!cells[1][1].getCellType().equals(CellType.Block) && bottomRight) {
			counter++;
			sum += cells[1][1].getTemprature();
		}
		return (double) sum / counter;

	}

	private double getAverageCellInRightTopCorner() {
		boolean bottomLeft = false;
		int counter = 1;
		double sum = cells[nCol - 1][0].getTemprature();
		// bottom
		if (!cells[nCol - 1][1].getCellType().equals(CellType.Block)) {
			bottomLeft = true;
			counter++;
			sum += cells[nCol - 1][1].getTemprature();
		}
		// left
		if (!cells[nCol - 2][0].getCellType().equals(CellType.Block)) {
			bottomLeft = true;
			counter++;
			sum += cells[nCol - 2][0].getTemprature();
		}
		// bottom-left
		if (!cells[nCol - 2][1].getCellType().equals(CellType.Block) && bottomLeft) {
			counter++;
			sum += cells[nCol - 2][1].getTemprature();
		}
		return (double) sum / counter;

	}

	private double getAverageCellInLeftBottomCorner() {
		boolean topRight = false;
		int counter = 1;
		double sum = cells[0][nRow - 1].getTemprature();
		// top
		if (!cells[0][nRow - 2].getCellType().equals(CellType.Block)) {
			topRight = true;
			counter++;
			sum += cells[0][nRow - 2].getTemprature();
		}
		// right
		if (!cells[1][nRow - 1].getCellType().equals(CellType.Block)) {
			topRight = true;
			counter++;
			sum += cells[1][nRow - 1].getTemprature();
		}
		// top-right
		if (!cells[1][nRow - 2].getCellType().equals(CellType.Block) && topRight) {
			counter++;
			sum += cells[1][nRow - 2].getTemprature();
		}
		return (double) sum / counter;

	}

	private double getAverageCellInRightBottomCorner() {
		boolean topLeft = false;
		int counter = 1;
		double sum = cells[nCol - 1][nRow - 1].getTemprature();
		// top
		if (!cells[nCol - 1][nRow - 2].getCellType().equals(CellType.Block)) {
			topLeft = true;
			counter++;
			sum += cells[nCol - 1][nRow - 2].getTemprature();
		}
		// left
		if (!cells[nCol - 2][nRow - 1].getCellType().equals(CellType.Block)) {
			topLeft = true;
			counter++;
			sum += cells[nCol - 2][nRow - 1].getTemprature();
		}
		// top-left
		if (!cells[nCol - 2][nRow - 2].getCellType().equals(CellType.Block) && topLeft) {
			counter++;
			sum += cells[nCol - 2][nRow - 2].getTemprature();
		}
		return (double) sum / counter;

	}

	public CellType getCellTypeOfCell(int colIndex, int rowIndex) {
		return this.cells[colIndex][rowIndex].getCellType();
	}

	public double getCellTemprature(int colIndex, int rowIndex) {
		return cells[colIndex][rowIndex].getTemprature();
	}

	public void reconstructionCells(int newCol, int newRow, int tmpNewCellSize) {
		Cell[][] newCells = new Cell[newCol][newRow];
		double nNewCell = this.cellSize / tmpNewCellSize;

		if (Math.floor(nNewCell) > 1) {
			double equvlentCells = Math.floor(nNewCell);
			System.out.println(equvlentCells);
			for (int i = 0; i < nCol; i++) {
				for (int j = 0; j < nRow; j++) {
					try {
						for (int k = (int) (i * equvlentCells); k < (i * equvlentCells) + equvlentCells; k++) {
							for (int l = (int) (j * equvlentCells); l < (j * equvlentCells) + equvlentCells; l++) {
								newCells[k][l] = new Cell(cells[i][j].getCellType(), cells[i][j].getTemprature());
								MainController.drawCell(k, l, newCells[k][l], (int) tmpNewCellSize, minTemperature,
										maxTemperature);
								// updateCellSize(i,j,(int)tmpNewCellSize);
							}
						}
					} catch (Exception e) {
						System.out.println("out of range: i=" + i + " j=" + j);
					}

				}
			}

			/*
			 * equvlentCells=Math.ceil(nNewCell);
			 * System.out.println(equvlentCells);
			 */
			for (int i = (int) (nCol * equvlentCells); i < newCol; i++) {
				for (int j = 0; j < newRow; j++) {
					newCells[i][j] = new Cell(CellType.NormalCell, CellType.NormalCell.getValue());
					MainController.drawCell(i, j, newCells[i][j], (int) tmpNewCellSize, minTemperature, maxTemperature);

				}
			}

			for (int i = 0; i < newCol; i++) {
				for (int j = (int) (nRow * equvlentCells); j < newRow; j++) {
					newCells[i][j] = new Cell(CellType.NormalCell, CellType.NormalCell.getValue());
					MainController.drawCell(i, j, newCells[i][j], (int) tmpNewCellSize, minTemperature, maxTemperature);

				}
			}

			this.cells = newCells;
			this.nCol = newCol;
			this.nRow = newRow;
			this.cellSize = tmpNewCellSize;

		}

		else if (Math.floor(tmpNewCellSize / cellSize) > 1) {

			double equvlentCells = Math.floor(tmpNewCellSize / cellSize);
			System.out.println(equvlentCells);
			for (int i = 0; i < newCol; i++) {
				for (int j = 0; j < newRow; j++) {
					double tmpAvrage = 0.0;
					Cell tmpCell = null;
					outerLoop: for (int k = (int) (i * equvlentCells); k < (i * equvlentCells) + equvlentCells; k++) {
						for (int l = (int) (j * equvlentCells); l < (j * equvlentCells) + equvlentCells; l++) {
							CellType tmpCellType = cells[k][l].getCellType();
							if (tmpCellType.isUpdatable()) {
								tmpAvrage += cells[k][l].getTemprature();
							} else if (tmpCellType.equals(CellType.HeatSource)) {
								tmpCell = new Cell(CellType.HeatSource, maxTemperature);
								break outerLoop;
							} else if (tmpCell == null) {
								tmpCell = new Cell(tmpCellType, tmpCellType.getValue());
							} else if (tmpCell.getCellType().getPriority() < tmpCellType.getPriority()) {
								tmpCell = new Cell(tmpCellType, tmpCellType.getValue());
							}
							// updateCellSize(i,j,(int)tmpNewCellSize);
						}
					}
					if (tmpCell != null) {
						newCells[i][j] = tmpCell;
						MainController.drawCell(i, j, tmpCell, (int) tmpNewCellSize, minTemperature, maxTemperature);
					} else {
						tmpAvrage = tmpAvrage * (equvlentCells * equvlentCells);
						newCells[i][j] = new Cell(cells[i][j].getCellType(), tmpAvrage);
						MainController.drawCell(i, j, newCells[i][j], (int) tmpNewCellSize, minTemperature,
								maxTemperature);

					}
				}
			}

			this.cells = newCells;
			this.nCol = newCol;
			this.nRow = newRow;
			this.cellSize = tmpNewCellSize;
		} else {
			reInitializeCells(newCol, newRow, newCells, (int) tmpNewCellSize);
		}
	}

	private void reInitializeCells(int newCol, int newRow, Cell[][] newCells, int newCellSize) {
		if (newCol < nCol) {
			for (int i = 0; i < newCol; i++) {
				for (int j = 0; j < newRow; j++) {
					newCells[i][j] = new Cell(cells[i][j].getCellType(), cells[i][j].getTemprature());
					MainController.updateCellSize(i, j, newCellSize, minTemperature, maxTemperature);
				}
			}
			this.cells = newCells;
			this.nCol = newCol;
			this.nRow = newRow;
			this.cellSize = newCellSize;
		}

		else {
			for (int i = 0; i < nCol; i++) {
				for (int j = 0; j < nRow; j++) {
					newCells[i][j] = new Cell(cells[i][j].getCellType(), cells[i][j].getTemprature());
					MainController.updateCellSize(i, j, newCellSize, minTemperature, maxTemperature);
				}
			}
			for (int i = nCol; i < newCol; i++) {
				for (int j = 0; j < newRow; j++) {
					newCells[i][j] = new Cell(CellType.NormalCell, midTemperature);
					MainController.draw(i, j, cellSize, minTemperature, maxTemperature, midTemperature);

				}
			}

			for (int i = 0; i < newCol; i++) {
				for (int j = nRow; j < newRow; j++) {
					newCells[i][j] = new Cell(CellType.NormalCell, midTemperature);
					MainController.draw(i, j, cellSize, minTemperature, maxTemperature, midTemperature);
				}
			}

			this.cells = newCells;
			this.nCol = newCol;
			this.nRow = newRow;
			this.cellSize = newCellSize;

		}

	}

	public void resetTemperature(double newMinTemperature, double newMaxTemperature) {
		for (int i = 0; i < nCol; i++) {
			for (int j = 0; j < nRow; j++) {
				cells[i][j].updateTemprature(maxTemperature - minTemperature, newMaxTemperature - newMinTemperature,
						minTemperature, newMinTemperature);
			}
		}
		this.minTemperature = newMinTemperature;
		this.maxTemperature = newMaxTemperature;
		this.midTemperature = ((newMaxTemperature + newMinTemperature) / 2) + newMinTemperature;
		CellType.setCellTypeData(minTemperature, maxTemperature);

	}

}
