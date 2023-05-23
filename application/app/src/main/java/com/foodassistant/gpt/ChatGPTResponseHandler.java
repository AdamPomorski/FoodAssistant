package com.foodassistant.gpt;

import com.foodassistant.recipe.NutritionInfo;
import com.foodassistant.recipe.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatGPTResponseHandler {
    private Map<String, String> sectionPatterns;
    private String name;
    private String cookingTime;
    private String servingSize;
    private String diffLvl;
    private ArrayList<String> ingredients;
    private NutritionInfo nutritionInfo;
    private ArrayList<String> instructions;
    private ArrayList<Recipe> listOfRecipes;

    public ChatGPTResponseHandler(ArrayList<Recipe> listOfRecipes) {
        this.listOfRecipes = listOfRecipes;
    }

    private final String[] sectionNames = {"Ingredients", "Nutritional Information", "Instructions", "Recipe", "Cooking Time", "Serving size", "Difficulty Level"};
    private final String[] sectionValues = {"Ingredient", "Nutrition", "Instruct", "Recipe", "Cooking Time", "Serving", "Difficult"};

    private Map<String, String> fillMap(Map<String, String> sectionPatterns) {
        for (int i = 0; i < sectionNames.length; i++) {
            sectionPatterns.put(sectionNames[i], sectionValues[i]);
        }
        return sectionPatterns;
    }

    public ArrayList<Recipe> saveRecipes(String respone) {
        sectionPatterns = new HashMap<>();


        fillMap(sectionPatterns);
        String currentSection = null;
        String[] recipes = respone.split("Recipe");
        for (int i = 1; i < recipes.length; i++) {
            ingredients = new ArrayList<>();
            nutritionInfo = new NutritionInfo();
            instructions = new ArrayList<>();
            recipes[i] = "Recipe " + recipes[i];
            String[] lines = recipes[i].split("\\n");
            for (String line : lines) {
                String matchedSection = getMatchedSection(line, sectionPatterns);
                if (line.equals("")) {
                    continue;
                }
                if (matchedSection != null) {
                    currentSection = matchedSection;
                    if (matchedSection.equals("Cooking Time") || matchedSection.equals("Serving size") || matchedSection.equals("Difficulty Level") || matchedSection.equals("Recipe")) {
                        addToSection(line, currentSection, ingredients, nutritionInfo, instructions);
                    }
                } else if (currentSection != null) {
                    addToSection(line, currentSection, ingredients, nutritionInfo, instructions);
                }

            }

            listOfRecipes.add(new Recipe(name, ingredients, nutritionInfo, instructions, cookingTime, servingSize, diffLvl));

        }

        return listOfRecipes;
    }
//    public ArrayList<Recipe> saveRecipes(String response) {
//        sectionPatterns = new HashMap<>();
//        fillMap(sectionPatterns);
//        String[] recipes = response.split("Recipe");
//
//        for (int i = 1; i < recipes.length; i++) {
//            recipes[i] = "Recipe " + recipes[i];
//            String[] lines = recipes[i].split("\\n");
//            processRecipeLines(lines);
//        }
//
//        return listOfRecipes;
//    }
//
//    private void processRecipeLines(String[] lines) {
//        String currentSection = null;
//
//        for (String line : lines) {
//            if (line.isEmpty()) {
//                continue;
//            }
//
//            String matchedSection = getMatchedSection(line, sectionPatterns);
//
//            if (matchedSection != null) {
//                currentSection = matchedSection;
//                if (shouldAddToSection(matchedSection)) {
//                    addToSection(line, currentSection, ingredients, nutritionInfo, instructions);
//                }
//            } else if (currentSection != null) {
//                addToSection(line, currentSection, ingredients, nutritionInfo, instructions);
//            }
//        }
//
//        listOfRecipes.add(new Recipe(name, ingredients, nutritionInfo, instructions, cookingTime, servingSize, diffLvl));
//    }
//
//    private boolean shouldAddToSection(String section) {
//        return section.equals("Cooking Time") || section.equals("Serving Size") ||
//                section.equals("Difficulty Level") || section.equals("Recipe");
//    }


    private String getMatchedSection(String line, Map<String, String> sectionPatternsLocal) {
        for (Map.Entry<String, String> entry : sectionPatternsLocal.entrySet()) {

            if (line.contains(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void addToSection(String line, String currentSection, List<String> ingredients, NutritionInfo nutritionInfo, List<String> instructions) {

            switch (currentSection) {
                case "Ingredients":
                    if (!line.contains("Ingredient")) {
                        ingredients.add(line.replace("-", "").trim());
                    }
                    break;
                case "Nutritional Information":
                    if (!line.contains("Nutrition")) {
                        if (line.contains("Calories")) {
                            StringBuilder calories = new StringBuilder();
                            calories.append(line.split(":")[1].trim());
                            calories.append(" kcal");
                            nutritionInfo.setCalories(calories.toString());
                        } else if (line.contains("Fat")) {
                            nutritionInfo.setFat(line.split(":")[1].trim());
                        } else if (line.contains("Carbohydrates")) {
                            nutritionInfo.setCarbohydrates(line.split(":")[1].trim());
                        } else if (line.contains("Protein")) {
                            nutritionInfo.setProtein(line.split(":")[1].trim());
                        }
                    }
                    break;
                case "Instructions":
                    if (!line.contains("Instruct")) {
                        instructions.add(line);
                    }
                    break;
                case "Recipe":
                    name = line.split(":")[1].trim();
                    break;
                case "Cooking Time":
                    cookingTime = line.split(":")[1].trim();
                    break;
                case "Serving size":
                    servingSize = line.split(":")[1].trim();
                    break;
                case "Difficulty Level":
                    diffLvl = line.split(":")[1].trim();
                    break;
                default:
                    break;
            }
        }


}
