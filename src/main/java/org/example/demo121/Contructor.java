package org.example.demo121;

public class Contructor extends Employee {
    private double hourlyRate;
    private int maxHours;

    public Contructor(String name, double hourlyRate, int maxHours) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.maxHours = maxHours;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * maxHours;
    }
}

