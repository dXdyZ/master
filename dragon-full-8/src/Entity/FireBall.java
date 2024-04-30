package Entity;

import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FireBall extends MapObject {
	private boolean hit; // Флаг, указывающий, попала ли огненная шаров во что-то
	private boolean remove; // Флаг, указывающий, что необходимо удалить огненный шар
	private BufferedImage[] sprites; // Спрайты для обычного состояния огненного шара
	private BufferedImage[] hitSprites; // Спрайты для состояния огненного шара при попадании

	public FireBall(TileMap tm, boolean right) {
		super(tm); // Вызов конструктора родительского класса
		facingRight = right; // Установка направления движения огненного шара
		moveSpeed = 3.8; // Установка скорости движения огненного шара
		if(right) dx = moveSpeed; // Установка скорости по оси X в зависимости от направления
		else dx = -moveSpeed;
		width = 30; // Установка ширины огненного шара
		height = 30; // Установка высоты огненного шара
		cwidth = 14; // Установка ширины столкновения огненного шара
		cheight = 14; // Установка высоты столкновения огненного шара
		// Загрузка спрайтов огненного шара
		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Player/fireball.gif"
					)
			);
			sprites = new BufferedImage[4]; // Массив для спрайтов обычного состояния
			// Загрузка спрайтов для обычного состояния
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
						i * width,
						0,
						width,
						height
				);
			}
			hitSprites = new BufferedImage[3]; // Массив для спрайтов при попадании
			// Загрузка спрайтов для состояния при попадании
			for(int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(
						i * width,
						height,
						width,
						height
				);
			}
			animation = new Animation(); // Создание анимации огненного шара
			animation.setFrames(sprites); // Установка спрайтов для анимации
			animation.setDelay(70); // Установка задержки между кадрами анимации
		} catch(Exception e) {
			e.printStackTrace(); // Вывод информации об ошибке, если что-то пошло не так
		}
	}

	public void setHit() {
		if(hit) return; // Если огненный шар уже попал, выходим из метода
		hit = true; // Устанавливаем флаг попадания
		animation.setFrames(hitSprites); // Устанавливаем спрайты для анимации при попадании
		animation.setDelay(70); // Установка задержки между кадрами анимации
		dx = 0; // Устанавливаем скорость по оси X равной 0
	}

	public boolean shouldRemove() { return remove; } // Метод, возвращающий флаг удаления огненного шара

	public void update() {
		checkTileMapCollision(); // Проверка столкновений с тайлами
		setPosition(xtemp, ytemp); // Установка новой позиции огненного шара
		if(dx == 0 && !hit) {
			setHit(); // Если огненный шар стоит на месте и еще не попал, вызываем метод попадания
		}
		animation.update(); // Обновление анимации огненного шара
		if(hit && animation.hasPlayedOnce()) {
			remove = true; // Установка флага на удаление огненного шара, если анимация при попадании проиграна один раз
		}
	}

	public void draw(Graphics2D g) {
		setMapPosition(); // Установка позиции на карте
		super.draw(g); // Вызов метода отрисовки из родительского класса
	}
}



















