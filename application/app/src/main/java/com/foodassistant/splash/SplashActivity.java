package com.foodassistant.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.foodassistant.R;
import com.foodassistant.gpt.ChatGPTHelper;
import com.foodassistant.gpt.ChatGPTResponseHandler;
import com.foodassistant.main.MainActivity;
import com.foodassistant.recipe.MealType;
import com.foodassistant.recipe.Recipe;
import com.foodassistant.recipe.RecipeActivity;
import com.foodassistant.recipe.RecipeManager;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private ArrayList<String>userInputList;
    private ChatGPTHelper chatGPTHelper = new ChatGPTHelper();
    private ChatGPTResponseHandler responseHandler;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<String>imagesUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = getIntent();
        userInputList = intent.getStringArrayListExtra("inputList");
        responseHandler = new ChatGPTResponseHandler(recipes);
        chatGPTHelper = new ChatGPTHelper();
        GetRecipesTask getRecipesTask = new GetRecipesTask();
        getRecipesTask.execute();
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.black,getTheme()), android.graphics.PorterDuff.Mode.SRC_IN);


    }
    private class GetRecipesTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String mealIdea = chatGPTHelper.getMealIdea(1, MealType.BREAKFAST.getValue(),userInputList);
            String output="Recipe 1: Ham and Onion Grilled Cheese Sandwich\n" +
                    "Nutritional Information (per serving):\n" +
                    "Calories: 321\n" +
                    "Protein: 19g\n" +
                    "Fat: 16g\n" +
                    "Carbohydrates: 24g\n" +
                    "Cooking Time: 10 minutes\n" +
                    "Serving Size: 1 sandwich\n" +
                    "Difficulty Level: Easy\n" +
                    "\n" +
                    "Ingredients:\n" +
                    "- 2 slices of bread\n" +
                    "- 2 slices of ham\n" +
                    "- 1/4 onion, sliced\n" +
                    "- 1/2 cup shredded cheddar cheese\n" +
                    "- 1 tablespoon butter\n" +
                    "\n" +
                    "Instructions:\n" +
                    "1. Heat a skillet over medium heat.\n" +
                    "2. Spread butter on one side of each slice of bread.\n" +
                    "3. Place one slice of bread, butter side down, in the skillet.\n" +
                    "4. Layer the ham, onion, and shredded cheese on top of the bread in the skillet.\n" +
                    "5. Place the other slice of bread, butter side up, on top of the cheese.\n" +
                    "6. Cook until the bottom slice of bread is golden brown, about 5 minutes. Then, flip the sandwich and cook until the other side is golden brown and the cheese is melted, about 3-4 minutes.\n" +
                    "7. Slice the sandwich in half and serve.\n";
            String outputFaulty = "Recipe 1: Ham and Onion Grilled Cheese Sandwich\n" +
                    "Nutritional Information (per serving):\n" +
                    "Calories: 321\n" +
                    "Protein: 19g\n" +
                    "Fat: 16g\n" +
                    "Cooking Time: 10 minutes\n" +
                    "Serving Size: 1 sandwich\n" +
                    "Difficulty Level: Easy\n" +
                    "\n" +
                    "Ingredients:\n" +
                    "- 2 slices of bread\n" +
                    "- 2 slices of ham\n" +
                    "- 1/4 onion, sliced\n" +
                    "- 1/2 cup shredded cheddar cheese\n" +
                    "- 1 tablespoon butter\n" +
                    "\n" +
                    "Instructions:\n" +
                    "1. Heat a skillet over medium heat.\n" +
                    "2. Spread butter on one side of each slice of bread.\n" +
                    "3. Place one slice of bread, butter side down, in the skillet.\n" +
                    "4. Layer the ham, onion, and shredded cheese on top of the bread in the skillet.\n" +
                    "5. Place the other slice of bread, butter side up, on top of the cheese.\n" +
                    "6. Cook until the bottom slice of bread is golden brown, about 5 minutes. Then, flip the sandwich and cook until the other side is golden brown and the cheese is melted, about 3-4 minutes.\n" +
                    "7. Slice the sandwich in half and serve.\n";


            recipes = responseHandler.saveRecipes(mealIdea);
            for (Recipe r: recipes
                 ) {
                if(r.getInstructions()==null||r.getIngredients()==null||r.getName()==null||r.getCookingTime()==null||r.getDifficultyLevel()==null||r.getServingSize()==null||r.getNutritionInfo().getCalories()==null||r.getNutritionInfo().getProtein()==null||r.getNutritionInfo().getCarbohydrates()==null||r.getNutritionInfo().getFat()==null)
                   return "failure";
            }
            RecipeManager recipeManager = RecipeManager.getInstance();
            recipeManager.setRecipes(recipes);


            return "success";
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("failure")) {
                Toast.makeText(getApplicationContext(), "Recipe generation failed. Please check if the ingredients are correctly set up.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else if (result.equals("success")) {

                GetImagesTask getImagesTask = new GetImagesTask();
                getImagesTask.execute();
            }

        }
    }
    private class GetImagesTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            String imageIdea;
            for (Recipe recipe : recipes
                 ) {
                //imageIdea = recipe.getName();
                //https://oaidalleapiprodscus.blob.core.windows.net/private/org-UDMuP6i7QQemuQqbIf5kofMa/user-W8XGbFG1LhAht5svfkMrJQZ7/img-PzNXbCQ4JbgNf6tTtXVbjFBB.png?st=2023-05-14T11%3A49%3A20Z&se=2023-05-14T13%3A49%3A20Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-05-13T20%3A45%3A21Z&ske=2023-05-14T20%3A45%3A21Z&sks=b&skv=2021-08-06&sig=2/w1mOUkmvsZyhVp0Ip4xvHbtT0EdALm2dYHJrVy%2Bk4%3D
                imageIdea = chatGPTHelper.getImageUrl(recipe.getName());
                imagesUrls.add(imageIdea);

            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
            intent.putExtra("imageUrls", imagesUrls);
            startActivity(intent);

        }
    }
}