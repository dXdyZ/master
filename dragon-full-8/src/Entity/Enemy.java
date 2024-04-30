package Entity;

import TileMap.TileMap;
public class Enemy extends MapObject {
	protected int health; // Здоровье врага
	protected int maxHealth; // Максимальное здоровье врага
	protected boolean dead; // Флаг, указывающий на смерть врага
	protected int damage; // Урон, который может нанести враг
	protected boolean flinching; // Флаг, указывающий на "подпрыгивание" при получении урона
	protected long flinchTimer; // Время начала "подпрыгивания" при получении урона

	public Enemy(TileMap tm) {
		super(tm); // Вызов конструктора родительского класса
	}

	public boolean isDead() { return dead; } // Метод для проверки, мертв ли враг
	public int getDamage() { return damage; } // Метод для получения урона, наносимого врагом

	public void hit(int damage) {
		if(dead || flinching) return; // Если враг мертв или уже "подпрыгивает", выход
		health -= damage; // Уменьшаем здоровье врага на значение урона
		if(health < 0) health = 0; // Если здоровье стало меньше нуля, устанавливаем его в ноль
		if(health == 0) dead = true; // Если здоровье равно нулю, враг мертв
		flinching = true; // Устанавливаем флаг "подпрыгивания"
		flinchTimer = System.nanoTime(); // Устанавливаем время начала "подпрыгивания"
	}

	public void update() {} // Обновление состояния врага
}















