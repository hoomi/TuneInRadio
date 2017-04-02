package com.hoomi.tuneinapi.categories.adapter;


import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoomi.tuneinapi.NavigationHelper;
import com.hoomi.tuneinapi.R;
import com.hoomi.tuneinapi.categories.model.BrowsableItem;
import com.hoomi.tuneinapi.categories.model.Category;
import com.hoomi.tuneinapi.categories.model.TuneInItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenreDetailsAdapter extends RecyclerView.Adapter<GenreDetailsAdapter.ViewHolder> implements View.OnClickListener {

    private final static int TYPE_GENRE = 0;
    private final static int TYPE_ITEM = 1;
    private RecyclerView recyclerView;

    @IntDef({TYPE_GENRE, TYPE_ITEM})
    @interface ItemType {
    }

    private List<BrowsableItem> browsableItems;
    private final NavigationHelper navigationHelper;

    public GenreDetailsAdapter(List<BrowsableItem> browsableItems, NavigationHelper navigationHelper) {
        this.browsableItems = browsableItems;
        this.navigationHelper = navigationHelper;
    }

    @ItemType
    @Override
    public int getItemViewType(int position) {
        return browsableItems.get(position) instanceof TuneInItem ? TYPE_ITEM : TYPE_GENRE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, @ItemType int viewType) {
        int layout = viewType == TYPE_ITEM ? R.layout.list_item_genre_details : R.layout.list_item_category;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        if (viewType == TYPE_GENRE) {
            view.setOnClickListener(this);
        }
        return new GenreDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BrowsableItem item = browsableItems.get(position);
        holder.title.setText(item.getText());
        if (holder.getItemViewType() == TYPE_ITEM) {
            TuneInItem tuneInItem = (TuneInItem) item;
            Picasso.with(holder.itemView.getContext()).load(tuneInItem.getImage()).fit().centerCrop().into(holder.image);
            holder.subTitle.setText(tuneInItem.getSubtext());
        }
        holder.item  = item;
    }

    @Override
    public int getItemCount() {
        return browsableItems == null ? 0 : browsableItems.size();
    }

    @Override
    public void onClick(View v) {
        if (null != navigationHelper && recyclerView != null) {
            GenreDetailsAdapter.ViewHolder holder = (GenreDetailsAdapter.ViewHolder) recyclerView.getChildViewHolder(v);
            navigationHelper.showCategory(holder.item);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public void setItems(List<BrowsableItem> browsableItems) {
        this.browsableItems = browsableItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.item_title)
        TextView title;
        @Nullable
        @BindView(R.id.item_subtitle)
        TextView subTitle;
        @Nullable
        @BindView(R.id.item_image)
        ImageView image;

        BrowsableItem item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
