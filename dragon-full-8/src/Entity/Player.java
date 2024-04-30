package Entity;

import TileMap.*;
import Audio.AudioPlayer;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends MapObject {
	// Переменные игрока
	private int health; // Здоровье
	private int maxHealth; // Максимальное здоровье
	private int fire; // Огонь (энергия)
	private int maxFire; // Максимальное значение огня
	private boolean dead; // Мертв ли игрок
	private boolean flinching; // Подвержен ли игрок "флинчу"
	private long flinchTimer; // Таймер для флинча

	// Фаерболл
	private boolean firing; // Стрельба
	private int fireCost; // Стоимость стрельбы
	private int fireBallDamage; // Урон от фаерболла
	private ArrayList<FireBall> fireBalls; // Массив с фаерболлами

	// Скретч
	private boolean scratching; // Скретч
	private int scratchDamage; // Урон от скретча
	private int scratchRange; // Радиус скретча

	// Парение
	private boolean gliding; // Парение

	// Анимации
	private ArrayList<BufferedImage[]> sprites; // Спрайты
	private final int[] numFrames = {
			2, 8, 1, 2, 4, 2, 5
	}; // Количество кадров для каждого действия

	// Действия анимации
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;

	// Звуковые эффекты
	private HashMap<String, AudioPlayer> sfx; // Мап для звуковых эффектов

	// Конструктор
	public Player(TileMap tm) {
		super(tm);
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		facingRight = true;
		health = maxHealth = 5;
		fire = maxFire = 2500;
		fireCost = 200;
		fireBallDamage = 5;
		fireBalls = new ArrayList<FireBall>();
		scratchDamage = 8;
		scratchRange = 40;

		// Загрузка спрайтов
		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Player/playersprites.gif"
					)
			);
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++) {
				BufferedImage[] bi =
						new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					if(i != SCRATCHING) {
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					} else {
						bi[j] = spritesheet.getSubimage(
								j * width * 2,
								i * height,
								width * 2,
								height
						);
					}
				}
				sprites.add(bi);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		// Установка начальной анимации
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);

		// Инициализация звуковых эффектов
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("jump", new AudioPlayer("/Resources/SFX/jump.mp3"));
		sfx.put("scratch", new AudioPlayer("/Resources/SFX/scratch.mp3"));
	}

	// Получение здоровья
	public int getHealth() { return health; }

	// Получение максимального здоровья
	public int getMaxHealth() { return maxHealth; }

	// Получение уровня огня
	public int getFire() { return fire; }

	// Получение максимального уровня огня
	public int getMaxFire() { return maxFire; }

	// Установка состояния стрельбы
	public void setFiring() { firing = true; }

	// Установка состояния скретча
	public void setScratching() { scratching = true; }

	// Установка состояния парения
	public void setGliding(boolean b) { gliding = b; }

	// Проверка атаки
	public void checkAttack(ArrayList<Enemy> enemies) {
		// Перебор врагов
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			// Скретч
			if(scratching) {
				if(facingRight) {
					if(
							e.getx() > x &&
									e.getx() < x + scratchRange &&
									e.gety() > y - height / 2 &&
									e.gety() < y + height / 2
					) {
						e.hit(scratchDamage);
					}
				} else {
					if(
							e.getx() < x &&
									e.getx() > x - scratchRange &&
									e.gety() > y - height / 2 &&
									e.gety() < y + height / 2
					) {
						e.hit(scratchDamage);
					}
				}
			}
			// Фаерболлы
			for(int j = 0; j < fireBalls.size(); j++) {
				if(fireBalls.get(j).intersects(e)) {
					e.hit(fireBallDamage);
					fireBalls.get(j).setHit();
					break;
				}
			}
			// Проверка столкновения с врагом
			if(intersects(e)) {
				hit(e.getDamage());
			}
		}
	}

	// Попадание
	public void hit(int damage) {
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	// Получение следующей позиции
	private void getNextPosition() {
		// Движение
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			} else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}

		// Невозможность движения во время атаки, кроме прыжка
		if((currentAction == SCRATCHING || currentAction == FIREBALL) &&
				!(jumping || falling)) {
			dx = 0;
		}

		// Прыжок
		if(jumping && !falling) {
			sfx.get("jump").play();
			dy = jumpStart;
			falling = true;
		}

		// Падение
		if(falling) {
			if(dy > 0 && gliding) dy += fallSpeed * 0.1;
			else dy += fallSpeed;
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}

	// Обновление
	public void update() {
		// Обновление позиции
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// Проверка окончания атаки
		if(currentAction == SCRATCHING) {
			if(animation.hasPlayedOnce()) scratching = false;
		}
		if(currentAction == FIREBALL) {
			if(animation.hasPlayedOnce()) firing = false;
		}

		// Атака фаерболлом
		fire += 1;
		if(fire > maxFire) fire = maxFire;
		if(firing && currentAction != FIREBALL) {
			if(fire > fireCost) {
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight);
				fb.setPosition(x, y);
				fireBalls.add(fb);
			}
		}

		// Обновление фаерболлов
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}

		// Проверка завершения флинча
		if(flinching) {
			long elapsed =
					(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000) {
				flinching = false;
			}
		}

		// Установка анимации
		if(scratching) {
			if(currentAction != SCRATCHING) {
				sfx.get("scratch").play();
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				width = 60;
			}
		} else if(firing) {
			if(currentAction != FIREBALL) {
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				width = 30;
			}
		} else if(dy > 0) {
			if(gliding) {
				if(currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 30;
				}
			} else if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 30;
			}
		} else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 30;
			}
		} else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 30;
			}
		} else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 30;
			}
		}

		// Обновление анимации
		animation.update();

		// Установка направления
		if(currentAction != SCRATCHING && currentAction != FIREBALL) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}

	// Отрисовка
	public void draw(Graphics2D g) {
		setMapPosition();

		// Отрисовка фаерболлов
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).draw(g);
		}

		// Отрисовка игрока
		if(flinching) {
			long elapsed =
					(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}
}


















