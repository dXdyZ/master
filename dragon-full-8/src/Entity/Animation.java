package Entity;

import java.awt.image.BufferedImage;

public class Animation {
	private BufferedImage[] frames; // Массив кадров анимации
	private int currentFrame; // Текущий кадр
	private long startTime; // Время начала анимации
	private long delay; // Задержка между кадрами
	private boolean playedOnce; // Флаг, указывающий на то, была ли анимация воспроизведена один раз

	public Animation() {
		playedOnce = false; // Инициализация флага
	}

	public void setFrames(BufferedImage[] frames) {
		this.frames = frames; // Установка массива кадров
		currentFrame = 0; // Установка начального кадра
		startTime = System.nanoTime(); // Получение текущего времени
		playedOnce = false; // Сброс флага
	}

	public void setDelay(long d) { delay = d; } // Установка задержки между кадрами
	public void setFrame(int i) { currentFrame = i; } // Установка текущего кадра

	public void update() {
		if(delay == -1) return; // Если задержка равна -1, выход

		long elapsed = (System.nanoTime() - startTime) / 1000000; // Просчитываем прошедшее время

		if(elapsed > delay) { // Если прошедшее время превышает задержку
			currentFrame++; // Переходим к следующему кадру
			startTime = System.nanoTime(); // Обновляем время начала анимации
		}

		if(currentFrame == frames.length) { // Если достигнут конец массива кадров
			currentFrame = 0; // Возвращаемся к начальному кадру
			playedOnce = true; // Устанавливаем флаг, что анимация воспроизведена один раз
		}
	}

	public int getFrame() { return currentFrame; } // Получение текущего кадра
	public BufferedImage getImage() { return frames[currentFrame]; } // Получение изображения текущего кадра
	public boolean hasPlayedOnce() { return playedOnce; } // Проверка, была ли анимация воспроизведена один раз
}

















