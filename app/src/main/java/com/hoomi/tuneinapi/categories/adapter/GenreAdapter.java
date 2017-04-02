package com.hoomi.tuneinapi.categories.adapter;

import android.view.View;

import com.hoomi.tuneinapi.NavigationHelper;
import com.hoomi.tuneinapi.categories.model.Category;

import java.util.List;

public class GenreAdapter extends CategoriesAdapter {
    public GenreAdapter(List<Category> items, NavigationHelper listener) {
        super(items, listener);
    }

    @Override
    public void onClick(View v) {
        if (null != listener && recyclerView != null) {
            ViewHolder holder = (ViewHolder) recyclerView.getChildViewHolder(v);
            listener.showCategory(holder.item);
        }
    }
}
