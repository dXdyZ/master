package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1State extends GameState {
	private TileMap tileMap; // Карта уровня
	private Background bg; // Фон
	private Player player; // Игрок
	private ArrayList<Enemy> enemies; // Список врагов
	private ArrayList<Explosion> explosions; // Список взрывов
	private HUD hud; // Интерфейс игрока
	private AudioPlayer bgMusic; // Фоновая музыка

	// Конструктор, принимающий менеджер состояний
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init(); // Инициализация состояния
	}

	// Метод инициализации состояния
	public void init() {
		// Создание карты
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);

		// Создание фона
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

		// Создание игрока
		player = new Player(tileMap);
		player.setPosition(100, 100);

		// Создание врагов
		populateEnemies();

		// Создание списка взрывов
		explosions = new ArrayList<Explosion>();

		// Создание интерфейса игрока
		hud = new HUD(player);

		// Воспроизведение фоновой музыки
		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		bgMusic.play();
	}

	// Метод для создания врагов на карте
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		Slugger s;
		Point[] points = new Point[] {
				new Point(200, 100),
				new Point(860, 200),
				new Point(1525, 200),
				new Point(1680, 200),
				new Point(1800, 200)
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
	}

	// Метод обновления состояния
	public void update() {
		// Обновление игрока
		player.update();

		// Позиционирование карты относительно игрока
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety()
		);

		// Обновление фона
		bg.setPosition(tileMap.getx(), tileMap.gety());

		// Проверка атаки игрока
		player.checkAttack(enemies);

		// Обновление всех врагов
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
						new Explosion(e.getx(), e.gety()));
			}
		}

		// Обновление взрывов
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
	}

	// Метод отрисовки состояния
	public void draw(Graphics2D g) {
		// Отрисовка фона
		bg.draw(g);

		// Отрисовка карты
		tileMap.draw(g);

		// Отрисовка игрока
		player.draw(g);

		// Отрисовка врагов
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		// Отрисовка взрывов
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
					(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}

		// Отрисовка интерфейса игрока
		hud.draw(g);
	}

	// Метод обработки нажатия клавиши
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
	}

	// Метод обработки отпускания клавиши
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
	}
}













