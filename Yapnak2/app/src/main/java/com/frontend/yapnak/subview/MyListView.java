package com.frontend.yapnak.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by vahizan on 10/08/2015.
 */
public class MyListView extends ListView {

    private boolean blockLayout;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBlockLayout(boolean layoutBlock){
        this.blockLayout = layoutBlock;
    }

    @Override
    protected void layoutChildren() {
        if(!blockLayout) {
            super.layoutChildren();
        }
    }
}
