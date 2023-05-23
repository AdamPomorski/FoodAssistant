package com.foodassistant.gpt;



import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.image.CreateImageRequest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ChatGPTHelper {
    private OpenAiService service;
    public ChatGPTHelper(){
        service = new OpenAiService("your-token", Duration.ofSeconds(90));

    }

    public OpenAiService getService() {
        return service;
    }

    public void setService(OpenAiService service) {
        this.service = service;
    }


    public String getImageUrl(String prompt){
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt(prompt)
                .size("256x256")
                .user("user")
                .build();
        StringBuilder stringBuilder = new StringBuilder();
        List<Image> images = service.createImage(createImageRequest).getData();
        images.stream()
                .map(Image::getUrl)
                .forEach(stringBuilder::append);
        return stringBuilder.toString();

    }
    public String getMealIdea(Integer numOfRecipes,String mealType,List<String> products)
    {
            return requestChatGPT(createPrompt(numOfRecipes,mealType,products));
    }


    private String createPrompt(Integer numOfRecipes,String mealType,List<String> products)
    {
        String allProducts = String.join(", ", products);
        String question = "From given ingredients: " + allProducts + "give "+numOfRecipes.toString()+" recipes for "+mealType+" including nutritional information(calories,protein,carbohydrates,fat),instructions , cooking time, serving size, difficulty level(start each new recipe with word Recipe)";
        return question;
    }

    private String requestChatGPT(String prompt){
        try {
            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .messages(List.of(new ChatMessage("user", prompt)))
                    .model("gpt-3.5-turbo")
                    .build();

            List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();

            StringBuilder stringBuilder = new StringBuilder();

            choices.stream()
                    .map(ChatCompletionChoice::getMessage)
                    .map(ChatMessage::getContent)
                    .forEach(stringBuilder::append);
            return stringBuilder.toString();
        }catch (Exception e){
                return "An error occurred: "+e.getMessage();

        }

    }
}
