package org.another;

import java.io.Serializable;

public class AudioModel implements Serializable {
    private String name;
    private int duration;
    private String path;  // Путь к аудиофайлу должен быть строкой
    private int volume = 70; // Громкость аудиофайла
    private int stereoPosition = 50; // Стереоположение аудиофайла

    public class AudioModel implements Serializable {
        private String name;
        private int duration;
        private String path;  // Путь к аудиофайлу должен быть строкой
        private int volume = 70; // Громкость аудиофайла
        private int stereoPosition = 50; // Стереоположение аудиофайла

        public AudioModel(String name, int duration, String path, int volume, int stereoPosition) {
            this.name = name;
            this.duration = duration;
            this.path = path;
            this.volume = volume;
            this.stereoPosition = stereoPosition;
        }

        public AudioModel(String name, int duration, String path) {
            this.name = name;
            this.duration = duration;
            this.path = path;
        }

        // Геттеры и сеттеры
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public int getStereoPosition() {
            return stereoPosition;
        }

        public void setStereoPosition(int stereoPosition) {
            this.stereoPosition = stereoPosition;
        }
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getStereoPosition() {
        return stereoPosition;
    }

    public void setStereoPosition(int stereoPosition) {
        this.stereoPosition = stereoPosition;
    }
}