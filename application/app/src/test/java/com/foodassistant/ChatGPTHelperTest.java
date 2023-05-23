package com.foodassistant;

import static org.junit.Assert.*;

import com.foodassistant.gpt.ChatGPTHelper;
import com.foodassistant.recipe.MealType;
import com.theokanning.openai.service.OpenAiService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ChatGPTHelperTest {
    private ChatGPTHelper chatGPTHelper;
    private OpenAiService openAiService;
    private String result;
    private List<String> products = Arrays.asList("Eggs", "Sausages", "Cucumbers");

    @Before
    public void setUp() throws Exception {
        chatGPTHelper = new ChatGPTHelper();
        openAiService = chatGPTHelper.getService();
        result = chatGPTHelper.getMealIdea(1, MealType.LUNCH.getValue(), products);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getBreakFastIdeaNotNull() {assertNotNull(result);}

    @Test
    public void getBreakFastIdeaResult() {assertTrue(result.contains("eggs"));}



}