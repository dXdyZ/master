package Audio;

import javax.sound.sampled.*;

// Класс для воспроизведения аудиофайлов
public class AudioPlayer {
	private Clip clip;

	// Конструктор, принимающий путь к аудиофайлу
	public AudioPlayer(String s) {
		try {
			// Загрузка аудиофайла
			AudioInputStream ais =
					AudioSystem.getAudioInputStream(
							getClass().getResourceAsStream(s)
					);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false
			);
			AudioInputStream dais =
					AudioSystem.getAudioInputStream(
							decodeFormat, ais);
			// Открытие аудиоклипа
			clip = AudioSystem.getClip();
			clip.open(dais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Метод для воспроизведения аудио
	public void play() {
		if (clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}

	// Метод для остановки воспроизведения аудио
	public void stop() {
		if (clip.isRunning()) clip.stop();
	}

	// Метод для закрытия аудиофайла
	public void close() {
		stop();
		clip.close();
	}
}















