package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Ильин Тимур
 */
public class MenuState extends GameState {
	private Background bg; // Фон меню
	private int currentChoice = 0; // Текущий выбор
	private String[] options = { // Варианты меню
			"Start",
			"Help",
			"Quit"
	};
	private Color titleColor; // Цвет заголовка
	private Font titleFont; // Шрифт заголовка
	private Font font; // Шрифт текста

	// Конструктор
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm; // Устанавливаем менеджер состояний
		try {
			// Загрузка фона
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0); // Установка скорости движения фона

			// Установка цвета и шрифта заголовка
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 28);

			// Установка шрифта текста
			font = new Font("Arial", Font.PLAIN, 12);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Инициализация состояния
	public void init() {}

	// Обновление состояния
	public void update() {
		bg.update(); // Обновление фона
	}

	// Отрисовка состояния
	public void draw(Graphics2D g) {
		// Отрисовка фона
		bg.draw(g);

		// Отрисовка заголовка
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Dragon Tale", 80, 70);

		// Отрисовка вариантов меню
		g.setFont(font);
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 145, 140 + i * 15);
		}
	}

	// Метод выбора варианта меню
	private void select() {
		// Обработка выбора
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE); // Переход к игровому уровню
		}
		if (currentChoice == 1) {
			// Вывод помощи (возможно, реализуется позднее)
		}
		if (currentChoice == 2) {
			System.exit(0); // Завершение приложения
		}
	}

	// Обработка нажатия клавиш
	public void keyPressed(int k) {
		// Обработка нажатия клавиш для перемещения по меню и выбора пункта
		if (k == KeyEvent.VK_ENTER) {
			select(); // Вызов метода выбора
		}
		if (k == KeyEvent.VK_UP) {
			currentChoice--; // Перемещение вверх
			if (currentChoice == -1) {
				currentChoice = options.length - 1; // Циклическое перемещение вверх
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			currentChoice++; // Перемещение вниз
			if (currentChoice == options.length) {
				currentChoice = 0; // Циклическое перемещение вниз
			}
		}
	}

	// Обработка отпускания клавиш (в данном состоянии не используется)
	public void keyReleased(int k) {}
}











