package com.foodassistant.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodassistant.R;

import java.util.ArrayList;

public class IngredientsListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> userInputListL = new ArrayList<>();
    LayoutInflater inflater;

    public IngredientsListAdapter(Context context, ArrayList<String> userInputList) {
        this.context = context;
        this.userInputListL = userInputList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userInputListL.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.ingredients_list_item, null);
        ImageView deleteBtn = convertView.findViewById(R.id.deleteIngBtn);
        TextView ingredientTextView = convertView.findViewById(R.id.ingredientItem);
        ingredientTextView.setText(userInputListL.get(position));


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInputListL.remove(position);
                notifyDataSetChanged();

            }
        });

        return convertView;

    }

}

