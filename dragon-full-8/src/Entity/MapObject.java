package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.Rectangle;

public abstract class MapObject {
	// Переменные для работы с тайлами
	protected TileMap tileMap; // Карта тайлов
	protected int tileSize; // Размер тайла
	protected double xmap; // Координата x на карте
	protected double ymap; // Координата y на карте

	// Переменные позиции и вектора
	protected double x; // Координата x
	protected double y; // Координата y
	protected double dx; // Вектор скорости по x
	protected double dy; // Вектор скорости по y

	// Размеры
	protected int width; // Ширина объекта
	protected int height; // Высота объекта

	// Коллизионные коробки
	protected int cwidth; // Ширина коллизионной коробки
	protected int cheight; // Высота коллизионной коробки

	// Коллизия
	protected int currRow; // Текущий ряд
	protected int currCol; // Текущий столбец
	protected double xdest; // Целевая координата x
	protected double ydest; // Целевая координата y
	protected double xtemp; // Временная координата x
	protected double ytemp; // Временная координата y
	protected boolean topLeft; // Верхний левый угол коллизионной коробки
	protected boolean topRight; // Верхний правый угол коллизионной коробки
	protected boolean bottomLeft; // Нижний левый угол коллизионной коробки
	protected boolean bottomRight; // Нижний правый угол коллизионной коробки

	// Анимация
	protected Animation animation; // Анимация
	protected int currentAction; // Текущее действие
	protected int previousAction; // Предыдущее действие
	protected boolean facingRight; // Направление вправо

	// Движение
	protected boolean left; // Движение влево
	protected boolean right; // Движение вправо
	protected boolean up; // Движение вверх
	protected boolean down; // Движение вниз
	protected boolean jumping; // Прыжок
	protected boolean falling; // Падение

	// Атрибуты движения
	protected double moveSpeed; // Скорость движения
	protected double maxSpeed; // Максимальная скорость
	protected double stopSpeed; // Скорость остановки
	protected double fallSpeed; // Скорость падения
	protected double maxFallSpeed; // Максимальная скорость падения
	protected double jumpStart; // Начальная скорость прыжка
	protected double stopJumpSpeed; // Скорость остановки прыжка

	// Конструктор
	public MapObject(TileMap tm) {
		tileMap = tm; // Установка карты тайлов
		tileSize = tm.getTileSize(); // Получение размера тайла
	}

	// Проверка пересечения с другим объектом
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle(); // Получение границ текущего объекта
		Rectangle r2 = o.getRectangle(); // Получение границ объекта o
		return r1.intersects(r2); // Возвращает true, если границы пересекаются
	}

	// Получение прямоугольника коллизии
	public Rectangle getRectangle() {
		return new Rectangle(
				(int)x - cwidth,
				(int)y - cheight,
				cwidth,
				cheight
		);
	}

	// Вычисление углов коллизионной коробки
	public void calculateCorners(double x, double y) {
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
	}

	// Проверка коллизии с картой тайлов
	public void checkTileMapCollision() {
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		xdest = x + dx;
		ydest = y + dy;
		xtemp = x;
		ytemp = y;
		calculateCorners(x, ydest);
		if(dy < 0) {
			if(topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else {
				ytemp += dy;
			}
		}
		if(dy > 0) {
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			else {
				ytemp += dy;
			}
		}

		calculateCorners(xdest, y);
		if(dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
	}

	// Получение координаты x
	public int getx() { return (int)x; }

	// Получение координаты y
	public int gety() { return (int)y; }

	// Получение ширины объекта
	public int getWidth() { return width; }

	// Получение высоты объекта
	public int getHeight() { return height; }

	// Получение ширины коллизионной коробки
	public int getCWidth() { return cwidth; }

	// Получение высоты коллизионной коробки
	public int getCHeight() { return cheight; }

	// Установка позиции
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// Установка вектора
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	// Установка позиции карты
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}

	// Установка движения влево
	public void setLeft(boolean b) { left = b; }

	// Установка движения вправо
	public void setRight(boolean b) { right = b; }

	// Установка движения вверх
	public void setUp(boolean b) { up = b; }

	// Установка движения вниз
	public void setDown(boolean b) { down = b; }

	// Установка состояния прыжка
	public void setJumping(boolean b) { jumping = b; }

	// Проверка находится ли объект за пределами экрана
	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
				x + xmap - width > GamePanel.WIDTH ||
				y + ymap + height < 0 ||
				y + ymap - height > GamePanel.HEIGHT;
	}

	// Отрисовка объекта
	public void draw(java.awt.Graphics2D g) {
		if(facingRight) {
			g.drawImage(
					animation.getImage(),
					(int)(x + xmap - width / 2),
					(int)(y + ymap - height / 2),
					null
			);
		} else {
			g.drawImage(
					animation.getImage(),
					(int)(x + xmap - width / 2 + width),
					(int)(y + ymap - height / 2),
					-width,
					height,
					null
			);
		}
	}
}

















