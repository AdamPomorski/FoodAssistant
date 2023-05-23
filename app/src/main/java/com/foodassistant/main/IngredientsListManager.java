package com.foodassistant.main;

import com.foodassistant.recipe.Recipe;
import com.foodassistant.recipe.RecipeManager;

import java.util.ArrayList;

public class IngredientsListManager {

        private static com.foodassistant.main.IngredientsListManager instance;
        private ArrayList<String> ingredientsList;

        private IngredientsListManager() {
            ingredientsList = new ArrayList<>();
        }

        public static com.foodassistant.main.IngredientsListManager getInstance() {
            if (instance == null) {
                instance = new com.foodassistant.main.IngredientsListManager();
            }
            return instance;
        }

        public ArrayList<String> getIngredientsList() {
            return ingredientsList;
        }

        public void setIngredientsList(ArrayList<String> ingredientsList) {
            this.ingredientsList = ingredientsList;
        }



}
