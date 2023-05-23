package com.foodassistant.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;


import com.foodassistant.gpt.ChatGPTHelper;
import com.foodassistant.gpt.ChatGPTResponseHandler;

import com.foodassistant.R;
import com.foodassistant.recipe.Recipe;
import com.foodassistant.splash.SplashActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ChatGPTHelper chatGPTHelper = new ChatGPTHelper();
    ChatGPTResponseHandler responseHandler;
    ArrayList<Recipe> recipes = new ArrayList<>();

    private ListView listView;
    private ArrayList<String> userInputList;
    private EditText userInputEditText;
    private FloatingActionButton addButton,confirmButton;
    IngredientsListManager ingredientsListManager = IngredientsListManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseHandler = new ChatGPTResponseHandler(recipes);
        chatGPTHelper = new ChatGPTHelper();
        listView = findViewById(R.id.listView);
        userInputList = new ArrayList<>();
        userInputEditText = findViewById(R.id.addIngredientEdit);
        addButton = findViewById(R.id.addButton);
        confirmButton = findViewById(R.id.confirmButton);
        userInputList = ingredientsListManager.getIngredientsList();
        IngredientsListAdapter adapter = new IngredientsListAdapter(this, userInputList);
        listView.setAdapter(adapter);
        userInputEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String userInput = userInputEditText.getText().toString().trim();
                    if (!TextUtils.isEmpty(userInput)) {
                        userInputList.add(userInput);
                        adapter.notifyDataSetChanged();
                        userInputEditText.setText(""); // Clear the EditText
                        return true; // Consume the event
                    }
                }
                return false;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = userInputEditText.getText().toString();

                for (String input: userInputList
                     ) {
                    //TODO: check for redundance
                }
                if (!TextUtils.isEmpty(userInput)) {
                    userInputList.add(userInput);
                    adapter.notifyDataSetChanged();
                    userInputEditText.setText(""); // Clear the EditText
                }
                userInputEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(userInputEditText, InputMethodManager.SHOW_IMPLICIT);

            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                intent.putExtra("inputList", userInputList);
                startActivity(intent);
            }
        });


        System.out.println("dobrze");
    }

    @Override
    protected void onPause() {
        ingredientsListManager.setIngredientsList(userInputList);
        super.onPause();
    }
}
