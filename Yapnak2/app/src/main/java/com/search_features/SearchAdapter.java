package com.search_features;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uq.yapnak.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vahizan on 02/09/2015.
 */
public class SearchAdapter extends ArrayAdapter<SearchItem> {

    private SearchItem item;
    private List<SearchItem>  mSearch = new ArrayList<>();

    public SearchAdapter(Context context, int resource) {
        super(context, R.layout.search_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.search_item, parent, false);

            //item = getItem(position);
             item = mSearch.get(position);


            TextView result = (TextView) convertView.findViewById(R.id.searchResult);
            result.setText(item.getSearchResult());
            TextView number = (TextView) convertView.findViewById(R.id.count);
            number.setText(item.getNumber());


        }else{

            item = (SearchItem) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mSearch.size();
    }

    @Override
    public SearchItem getItem(int position) {
        return mSearch.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return mSearch.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateEntries(ArrayList<SearchItem> list){
        this.mSearch = list;
    }
}
