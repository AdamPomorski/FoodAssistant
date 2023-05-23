package com.foodassistant.recipe;

public class NutritionInfo {
    private String Calories;
    private String Fat;
    private String Carbohydrates;
    private String Protein;

    public NutritionInfo() {
    }

    public NutritionInfo(String calories, String fat, String carbohydrates, String protein) {
        Calories = calories;
        Fat = fat;
        Carbohydrates = carbohydrates;
        Protein = protein;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getCarbohydrates() {
        return Carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        Carbohydrates = carbohydrates;
    }

    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }
}
