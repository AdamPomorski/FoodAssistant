package com.foodassistant.recipe;

import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe{
    private String name;
    private ArrayList<String> ingredients;
    private NutritionInfo nutritionInfo;
    private ArrayList<String> instructions;
    private String cookingTime;
    private String servingSize;
    private String difficultyLevel;


    public Recipe(String name, ArrayList<String> ingredients, NutritionInfo nutritionalInfo, ArrayList<String> instructions, String cookingTime, String servingSize, String difficultyLevel) {
        this.name = name;
        this.ingredients = ingredients;
        this.nutritionInfo = nutritionalInfo;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.servingSize = servingSize;
        this.difficultyLevel = difficultyLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public NutritionInfo getNutritionInfo() {
        return nutritionInfo;
    }

    public void setNutritionInfo(NutritionInfo nutritionInfo) {
        this.nutritionInfo = nutritionInfo;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}