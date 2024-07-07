package com.flamexander.multithreading.homework;

import org.w3c.dom.ls.LSOutput;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

class HomeWork {
    public static void main(String[] args) {
        ArrayBlockingQueue<Car> carParking = new ArrayBlockingQueue<>(4);
        for (int i = 0; i < 10; i++) {
            int w = i;
            new Thread(() -> {
                System.out.println("Машина " + w + " подъехала");
                try {
                    if (carParking.offer(new Car("Машина " + w), 10L, TimeUnit.SECONDS)) {
                        System.out.println("Машина встала " + w);
                    } else {
                        System.out.println("Машина не встала " + w);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    String carName = carParking.take().getName();
                    System.out.println(carName + " уехала");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}

class Car {
    String name;
    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}