package com.example.application;

import java.util.ArrayList;
import java.util.Arrays;

public class PetGroup {
    private String number;
    private String categoryName;
    private int deliveryLevel;
    private boolean dogExistsFlg;
    private boolean catExistsFlg;
    private  int id;

    public int getId() {
        return id;
    }
    public PetGroup(String number, String categoryName, int deliveryLevel,
                      boolean dogExistsFlg, boolean catExistsFlg){
        this.number = number;
        this.categoryName = categoryName;
        this.deliveryLevel = deliveryLevel;
        this.dogExistsFlg = dogExistsFlg;
        this.catExistsFlg = catExistsFlg;
    }
    public PetGroup(int id, String number, String categoryName, int deliveryLevel,
                      boolean dogExistsFlg, boolean catExistsFlg){
        this(number,categoryName,deliveryLevel,dogExistsFlg,catExistsFlg);
        this.id = id;
    }
    public  String getNumber() {
        return number;
    }
    public  String getCategoryName() {
        return categoryName;
    }
    public int getDeliveryLevel(){
        return deliveryLevel;
    }
    public boolean isDogExistsFlg() {
        return  dogExistsFlg;
    }
    public boolean isCatExistsFlg() {
        return  catExistsFlg;
    }


    private static ArrayList<PetGroup> groups = new ArrayList<PetGroup>(
            Arrays.asList(
                    new  PetGroup( "Food", "Pets", 0, false, false),
                    new  PetGroup( "Accessories", "Pets", 0, false, false),
                    new  PetGroup( "Medicines", "Pets", 0, false, false)

            )
    );
    public static PetGroup getGroup (String groupName){
        for(PetGroup g: groups){
            if(g.getNumber().equals(groupName)){
                return g;
            }
        }
        return null;
    }
    public static ArrayList<PetGroup> getGroups() {

        return groups;
    }
    @Override
    public String toString(){
        return number;
    }
    public static void addGroup(PetGroup group){
        groups.add(group);
    }
}
