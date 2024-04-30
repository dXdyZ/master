package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import GameState.GameStateManager;

/**
 * @author Ильин Тимур
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
	// Размеры окна
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;

	// Поток игры
	private Thread thread;
	private boolean running;
	private int FPS = 60; // Желаемое количество кадров в секунду
	private long targetTime = 1000 / FPS; // Время на каждый кадр

	// Изображение
	private BufferedImage image;
	private Graphics2D g;

	// Менеджер состояний игры
	private GameStateManager gsm;

	// Конструктор
	public GamePanel() {
		super();
		// Устанавливаем предпочтительные размеры панели
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true); // Позволяет панели получать фокус ввода
		requestFocus(); // Запрашиваем фокус у панели
	}

	// Метод для запуска потока игры при добавлении панели на экран
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			// Создаем новый поток и запускаем его
			thread = new Thread(this);
			addKeyListener(this); // Добавляем слушателя клавиатуры
			thread.start();
		}
	}

	// Метод инициализации игры
	private void init() {
		// Создаем изображение
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		running = true;

		// Инициализируем менеджер состояний игры
		gsm = new GameStateManager();
	}

	// Основной игровой цикл
	public void run() {
		init(); // Инициализируем игру

		long start;
		long elapsed;
		long wait;

		while (running) {
			start = System.nanoTime();

			update(); // Обновляем состояние игры
			draw(); // Рисуем игру на изображении
			drawToScreen(); // Отображаем изображение на экране

			elapsed = System.nanoTime() - start;

			// Вычисляем время ожидания до следующего кадра
			wait = targetTime - elapsed / 1000000;
			if (wait < 0) wait = 5; // Не даем отрицательное время ожидания

			try {
				Thread.sleep(wait); // Приостанавливаем поток на указанное время
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Метод для обновления состояния игры
	private void update() {
		gsm.update();
	}

	// Метод для отрисовки игры на изображении
	private void draw() {
		gsm.draw(g);
	}

	// Метод для отображения изображения на экране
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		// Рисуем изображение на экране с учетом масштаба
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose(); // Освобождаем ресурсы графики
	}

	// Методы обработки событий клавиатуры
	public void keyTyped(KeyEvent key) {
	}

	public void keyPressed(KeyEvent key) {
		// Передаем нажатие клавиши в менеджер состояний игры
		gsm.keyPressed(key.getKeyCode());
	}

	public void keyReleased(KeyEvent key) {
		// Передаем отпускание клавиши в менеджер состояний игры
		gsm.keyReleased(key.getKeyCode());
	}
}
















