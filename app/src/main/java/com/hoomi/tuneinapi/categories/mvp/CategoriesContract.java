package com.hoomi.tuneinapi.categories.mvp;


import com.hoomi.tuneinapi.categories.model.Category;

import java.util.List;

public interface CategoriesContract {
    interface View extends CommonView {
        void showItems(List<Category> categories);
    }

    interface Presenter {
        void init();
    }
}
