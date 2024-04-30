package TileMap;

import java.awt.image.BufferedImage;

/**
 * @author Ильин Тимур
 */
public class Tile {
	private BufferedImage image; // Изображение тайла
	private int type; // Тип тайла

	// Виды тайлов
	public static final int NORMAL = 0; // Обычный тайл
	public static final int BLOCKED = 1; // Заблокированный тайл

	// Конструктор
	public Tile(BufferedImage image, int type) {
		this.image = image; // Устанавливаем изображение тайла
		this.type = type; // Устанавливаем тип тайла
	}

	// Метод получения изображения тайла
	public BufferedImage getImage() {
		return image;
	}

	// Метод получения типа тайла
	public int getType() {
		return type;
	}
}
