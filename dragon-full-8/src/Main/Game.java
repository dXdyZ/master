package Main;

import javax.swing.JFrame;

/**
 * @author Ильин
 */
public class Game {
	public static void main(String[] args) {
		// Создаем новое окно JFrame с названием "Dragon Tale"
		JFrame window = new JFrame("Dragon Tale");
		// Устанавливаем содержимое окна на экземпляр GamePanel
		window.setContentPane(new GamePanel());
		// Устанавливаем операцию закрытия окна по умолчанию, чтобы при закрытии приложение завершалось
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Запрещаем изменение размеров окна
		window.setResizable(false);
		// Автоматически подстраиваем размеры окна под его содержимое
		window.pack();
		// Делаем окно видимым
		window.setVisible(true);
	}
}
