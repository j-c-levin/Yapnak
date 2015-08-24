package com.frontend.yapnak.subview;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.uq.yapnak.R;

import java.util.ArrayList;

/**
 * Created by vahizan on 19/08/2015.
 */
public class AutocompleteEditText extends EditText {

    private ArrayAdapter adapter;
    private ListView listOptions;
    private View view;
    private String [] choose;

    public AutocompleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //listOptions=(ListView)view.findViewById(R.id.selectOption);
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getWords());
        listOptions.setAdapter(adapter);
        insertText();

    }

    public String[] getWords(){
        return choose;
    }

    public void setWords(String [] words){
        choose = words;
    }

    private CharSequence charS;

    private void setListAdapter(String [] values){

        setWords(values);
        adapter.notifyDataSetChanged();
    }

    private void insertText(){
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                final ArrayList<String> slist = new ArrayList<>();
                charS = s;
                Handler handler = new Handler();
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        int length = charS.length();
                        for (int j = 0; j < choose.length; j++) {

                            if (choose[j].contains(charS)) {
                                slist.add(choose[j]);
                            }
                        }

                        if (slist.size() > 0) {
                            listOptions.setVisibility(View.VISIBLE);
                            setListAdapter(slist.toArray(new String[slist.size()]));
                        }
                    }
                };
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}

