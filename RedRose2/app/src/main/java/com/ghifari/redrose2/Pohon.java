package com.ghifari.redrose2;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Observable;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CXXXV on 25/02/2017.
 */

public class Pohon{

    //generate pohon
    public Pohon(){
        setAge(0);
        setHeight(10);
        setNutrition(100);
        setType(0);
    }

    public void GrowPohon()
    {
        nutrition   -= 10;
        if(nutrition > 0) {
            height += 1;
        }
        else/*kurang nutrisi*/{
            nutrition = 0;
        }
    }

    public void Siram()
    {
        nutrition += 100;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNutrition() {
        return nutrition;
    }

    public void setNutrition(int nutrition) {
        this.nutrition = nutrition;;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int height;
    private int nutrition;
    private int age;
    private int type;
}
