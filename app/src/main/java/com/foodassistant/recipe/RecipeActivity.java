package com.foodassistant.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodassistant.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();
    ImageView recipeImage;
    TextView name, instructions, ingredients, diffLvl, servingSize, cookingTime, calories, carbo, fat, protein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        RecipeManager recipeManager = RecipeManager.getInstance();
        recipes = recipeManager.getRecipes();
        name = findViewById(R.id.titleTextView);
        instructions = findViewById(R.id.instructionsContent);
        ingredients = findViewById(R.id.ingredientsContent);
        diffLvl = findViewById(R.id.difficultyLevelContent);
        servingSize = findViewById(R.id.servingSizeContent);
        cookingTime = findViewById(R.id.cookingTimeContent);
        calories = findViewById(R.id.caloriesContent);
        carbo = findViewById(R.id.carboContent);
        fat = findViewById(R.id.fatContent);
        protein = findViewById(R.id.proteinContent);
        recipeImage = findViewById(R.id.recipeImage);

        Intent intent = getIntent();
        imageUrls = intent.getStringArrayListExtra("imageUrls");

        for (String image: imageUrls
             ) {

            Picasso.get()
                    .load(image)
                    .into(recipeImage);
        }
        for (Recipe r: recipes
             ) {
            name.setText(r.getName());
            String instructionsString = getArrayListToTextView(r.getIngredients(),r.getIngredients().size());
            ingredients.setText(instructionsString);
            String ingredientsString = getArrayListToTextView(r.getInstructions(),r.getInstructions().size());
            instructions.setText(ingredientsString);
            diffLvl.setText(r.getDifficultyLevel());
            servingSize.setText(r.getServingSize());
            cookingTime.setText(r.getCookingTime());
            calories.setText(r.getNutritionInfo().getCalories());
            protein.setText(r.getNutritionInfo().getProtein());
            fat.setText(r.getNutritionInfo().getFat());
            carbo.setText(r.getNutritionInfo().getCarbohydrates());

        }

    }
    private String getArrayListToTextView(ArrayList<String> list, int size){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            String str = list.get(i);
            stringBuilder.append(str);
            if (i < size - 1) {
                stringBuilder.append("\n\n");
            }
        }
        return stringBuilder.toString();
    }
}