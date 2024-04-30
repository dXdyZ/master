package TileMap;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import javax.imageio.ImageIO;

import Main.GamePanel;

/**
 * @author Ильин Тимур
 */
public class TileMap {
	// Позиция
	private double x;
	private double y;
	// Границы
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	private double tween; // Для плавного перемещения камеры
	// Карта
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	// Тайлсет
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	// Отрисовка
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;

	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		// Определение количества строк и столбцов для отрисовки
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07; // Коэффициент плавности перемещения
	}

	// Загрузка тайлов изображений из файла
	public void loadTiles(String s) {
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross]; // Создание массива тайлов

			BufferedImage subimage;
			// Загрузка изображений для каждого типа тайла
			for (int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Загрузка карты из файла
	public void loadMap(String s) {
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			// Чтение размеров карты
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			// Установка границ карты
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			String delims = "\\s+"; // Разделитель между значениями в строке
			// Загрузка данных карты
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Получение размера тайла
	public int getTileSize() {
		return tileSize;
	}

	// Получение текущей позиции X
	public double getx() {
		return x;
	}

	// Получение текущей позиции Y
	public double gety() {
		return y;
	}

	// Получение ширины карты
	public int getWidth() {
		return width;
	}

	// Получение высоты карты
	public int getHeight() {
		return height;
	}

	// Получение типа тайла по указанным координатам
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}

	// Установка коэффициента плавного перемещения
	public void setTween(double d) {
		tween = d;
	}

	// Установка позиции карты
	public void setPosition(double x, double y) {
		// Плавное перемещение камеры
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		fixBounds(); // Корректировка границ отображения карты
		colOffset = (int) -this.x / tileSize;
		rowOffset = (int) -this.y / tileSize;
	}

	// Корректировка границ отображения карты
	private void fixBounds() {
		if (x < xmin) x = xmin;
		if (y < ymin) y = ymin;
		if (x > xmax) x = xmax;
		if (y > ymax) y = ymax;
	}

	// Отрисовка карты
	public void draw(Graphics2D g) {
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if (row >= numRows) break;
			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if (col >= numCols) break;
				if (map[row][col] == 0) continue;
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
			}
		}
	}
}



















