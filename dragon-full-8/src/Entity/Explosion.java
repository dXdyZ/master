package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Explosion {
	private int x; // Позиция по оси X
	private int y; // Позиция по оси Y
	private int xmap; // Позиция на карте по оси X
	private int ymap; // Позиция на карте по оси Y
	private int width; // Ширина взрыва
	private int height; // Высота взрыва
	private Animation animation; // Анимация взрыва
	private BufferedImage[] sprites; // Спрайты для анимации взрыва
	private boolean remove; // Флаг, указывающий на необходимость удаления взрыва

	public Explosion(int x, int y) {
		this.x = x; // Установка начальной позиции по оси X
		this.y = y; // Установка начальной позиции по оси Y
		width = 30; // Установка ширины взрыва
		height = 30; // Установка высоты взрыва
		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/explosion.gif"
					)
			);
			sprites = new BufferedImage[6]; // Создание массива для спрайтов взрыва
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
						i * width,
						0,
						width,
						height
				); // Загрузка спрайтов взрыва из спрайтшита
			}
		} catch(Exception e) {
			e.printStackTrace(); // Вывод информации об ошибке, если что-то пошло не так
		}
		animation = new Animation(); // Создание анимации взрыва
		animation.setFrames(sprites); // Установка спрайтов для анимации
		animation.setDelay(70); // Установка задержки между кадрами анимации
	}

	public void update() {
		animation.update(); // Обновление анимации взрыва
		if(animation.hasPlayedOnce()) {
			remove = true; // Установка флага на удаление взрыва, если анимация проиграна один раз
		}
	}

	public boolean shouldRemove() { return remove; } // Метод, возвращающий флаг удаления взрыва

	public void setMapPosition(int x, int y) {
		xmap = x; // Установка позиции на карте по оси X
		ymap = y; // Установка позиции на карте по оси Y
	}

	public void draw(Graphics2D g) {
		g.drawImage(
				animation.getImage(),
				x + xmap - width / 2,
				y + ymap - height / 2,
				null
		); // Отрисовка текущего кадра анимации взрыва
	}
}

















