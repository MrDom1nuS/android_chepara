package com.example.application;

import java.util.ArrayList;
import java.util.Arrays;

public class Pet {
    private String name;
    private String groupName;

    public Pet(String name, String groupName) {
        this.name = name;
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }



    public static ArrayList<Pet> getPets() {
        return getPets("");
    }
    public static ArrayList<Pet> getPets(String groupName) {
        ArrayList<Pet> stList = new ArrayList<>();
        for (Pet s : pets) {
            if (s.getGroupName().equals(groupName) || (groupName == "")) {
                stList.add(s);
            }
        }
        return stList;
    }
    private final static ArrayList<Pet> pets = new ArrayList<Pet>(
            Arrays.asList(
                    new Pet("Dry feed", "Food"),
                    new Pet("Conserve", "Food"),
                    new Pet("Treat", "Food"),
                    new Pet("Collar", "Accessories"),
                    new Pet("Leash", "Accessories"),
                    new Pet("Pills", "Medicines"),
                    new Pet("Drops", "Medicines")
            )
    );


    @Override
    public String toString() {
        return name;
    }
}
