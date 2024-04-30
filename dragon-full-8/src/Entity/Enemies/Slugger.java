package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Slugger extends Enemy {
	private BufferedImage[] sprites; // Массив спрайтов
	public Slugger(TileMap tm) {
		super(tm); // Вызов конструктора суперкласса
		moveSpeed = 0.3; // Скорость перемещения
		maxSpeed = 0.3; // Максимальная скорость
		fallSpeed = 0.2; // Скорость падения
		maxFallSpeed = 10.0; // Максимальная скорость падения
		width = 30; // Ширина врага
		height = 30; // Высота врага
		cwidth = 20; // Ширина коллизии врага
		cheight = 20; // Высота коллизии врага
		health = maxHealth = 2; // Здоровье врага
		damage = 1; // Урон врага

		// Загрузка спрайтов
		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/slugger.gif"
					)
			);
			sprites = new BufferedImage[3]; // Массив для спрайтов
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
						i * width,
						0,
						width,
						height
				);
			}
		}
		catch(Exception e) {
			e.printStackTrace(); // Обработка исключений
		}

		animation = new Animation(); // Создание анимации
		animation.setFrames(sprites); // Установка спрайтов для анимации
		animation.setDelay(300); // Установка задержки между кадрами анимации
		right = true; // Начальное направление вправо
		facingRight = true; // Начальное направление вправо
	}

	private void getNextPosition() {
		// Движение влево
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		// Движение вправо
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		// Падение
		if(falling) {
			dy += fallSpeed;
		}
	}

	public void update() {
		// Обновление позиции
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// Проверка на нахождение в состоянии тряски
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400) {
				flinching = false;
			}
		}

		// Если враг ударился о стену, меняем направление
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}

		// Обновление анимации
		animation.update();
	}

	public void draw(Graphics2D g) {
		// Установка позиции на карте
		setMapPosition();
		// Вызов метода отрисовки суперкласса
		super.draw(g);
	}
}











