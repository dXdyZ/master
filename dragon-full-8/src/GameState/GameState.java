package GameState;

public abstract class GameState {
	protected GameStateManager gsm; // Менеджер состояний

	// Метод инициализации состояния
	public abstract void init();

	// Метод обновления состояния
	public abstract void update();

	// Метод отрисовки состояния
	public abstract void draw(java.awt.Graphics2D g);

	// Метод обработки нажатия клавиши
	public abstract void keyPressed(int k);

	// Метод обработки отпускания клавиши
	public abstract void keyReleased(int k);
}

