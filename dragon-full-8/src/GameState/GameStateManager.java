package GameState;

import java.util.ArrayList;

public class GameStateManager {
	private GameState[] gameStates; // Массив состояний игры
	private int currentState; // Текущее состояние игры
	public static final int NUMGAMESTATES = 2; // Количество состояний
	public static final int MENUSTATE = 0; // Индекс состояния меню
	public static final int LEVEL1STATE = 1; // Индекс состояния уровня

	// Конструктор
	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES]; // Инициализация массива состояний
		currentState = MENUSTATE; // Установка начального состояния
		loadState(currentState); // Загрузка начального состояния
	}

	// Метод для загрузки состояния
	private void loadState(int state) {
		// Создание нового экземпляра состояния в зависимости от переданного индекса
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
	}

	// Метод для выгрузки состояния
	private void unloadState(int state) {
		gameStates[state] = null; // Освобождение ресурсов состояния
	}

	// Метод для установки текущего состояния
	public void setState(int state) {
		unloadState(currentState); // Выгрузка текущего состояния
		currentState = state; // Установка нового текущего состояния
		loadState(currentState); // Загрузка нового текущего состояния
	}

	// Метод для обновления текущего состояния
	public void update() {
		try {
			gameStates[currentState].update(); // Вызов метода обновления текущего состояния
		} catch(Exception e) {} // Обработка исключений
	}

	// Метод для отрисовки текущего состояния
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g); // Вызов метода отрисовки текущего состояния
		} catch(Exception e) {} // Обработка исключений
	}

	// Метод для обработки нажатия клавиши
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k); // Передача нажатой клавиши текущему состоянию
	}

	// Метод для обработки отпускания клавиши
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k); // Передача отпущенной клавиши текущему состоянию
	}
}










