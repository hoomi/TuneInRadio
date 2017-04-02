package com.hoomi.tuneinapi.categories.mvp;


import com.hoomi.tuneinapi.categories.model.BrowsableItem;

import java.util.List;

public interface GenreDetailsContract {
    interface View extends CommonView {

        void showDetails(List<BrowsableItem> browsableItems);
    }

    interface Presenter {
        void init(String url);
    }
}
