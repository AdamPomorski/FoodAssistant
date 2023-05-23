# FoodAssistant
## Overview
FoodAssistant is a project of mobile application that creates recipes using ChatGPT API of ingredients selected by the users. It also creates images of dishes using DALL-E based on recipe name.

Current features of the application:
- adding ingredients to a list
- sending requests to Open AI API
- presenting necessary information about recipes: name of a dish, image of a dish, instructions, informations, cooking time, difficulty level of preperation, serving size, nutritional information

## Demo
https://github.com/AdamPomorski/FoodAssistant/assets/73637115/b3052543-30c9-4984-8a68-4e8c71b105c2

## Problems with the solution
- usage of the application is quite expensive: 0.002$ / 1K tokens when using chatgpt-3.5-turbo and 0.016$ / per image.
- responses of ChatGPT might be sometimes incomplete in terms of the content
- at certain hours during the day the service is overloaded which extends the response time or even does not generate the response

## Potential upgrades
- adding the choice of meals and number of recipes to generate
- adding user profile
- adding unwanted ingredients, allergens to the profile


