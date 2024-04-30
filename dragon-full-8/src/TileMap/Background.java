package TileMap;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

/**
 * @author Ильин Тимур
 */
public class Background {
	private BufferedImage image; // Изображение фона
	private double x; // Координата x фона
	private double y; // Координата y фона
	private double dx; // Вектор движения по оси x
	private double dy; // Вектор движения по оси y
	private double moveScale; // Масштабирование скорости движения

	// Конструктор
	public Background(String s, double ms) {
		try {
			// Загружаем изображение фона из файла
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms; // Устанавливаем масштаб скорости движения
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Метод установки позиции фона
	public void setPosition(double x, double y) {
		// Зацикливаем фон, чтобы он перемещался бесконечно
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}

	// Метод установки вектора движения фона
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	// Метод обновления состояния фона
	public void update() {
		x += dx;
		y += dy;
	}

	// Метод отрисовки фона
	public void draw(Graphics2D g) {
		// Отрисовка фона на текущей позиции
		g.drawImage(image, (int) x, (int) y, null);

		// Если фон выходит за левый край экрана, рисуем его справа от экрана
		if (x < 0) {
			g.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, null);
		}
		// Если фон выходит за правый край экрана, рисуем его слева от экрана
		if (x > 0) {
			g.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, null);
		}
	}
}







