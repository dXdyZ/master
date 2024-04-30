package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {
	private Player player; // Ссылка на игрока
	private BufferedImage image; // Изображение для отображения HUD
	private Font font; // Шрифт для текста на HUD

	public HUD(Player p) {
		player = p; // Установка ссылки на игрока
		try {
			// Загрузка изображения HUD и создание шрифта
			image = ImageIO.read(
					getClass().getResourceAsStream(
							"/HUD/hud.gif"
					)
			);
			font = new Font("Arial", Font.PLAIN, 14);
		} catch(Exception e) {
			e.printStackTrace(); // Вывод информации об ошибке, если что-то пошло не так
		}
	}

	public void draw(Graphics2D g) {
		// Отрисовка изображения HUD
		g.drawImage(image, 0, 10, null);
		g.setFont(font); // Установка шрифта для текста
		g.setColor(Color.WHITE); // Установка белого цвета для текста
		// Отображение текущего здоровья игрока и его максимального здоровья
		g.drawString(
				player.getHealth() + "/" + player.getMaxHealth(),
				30,
				25
		);
		// Отображение текущего уровня огня игрока и его максимального уровня огня
		g.drawString(
				player.getFire() / 100 + "/" + player.getMaxFire() / 100,
				30,
				45
		);
	}
}














