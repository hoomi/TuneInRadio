package com.hoomi.tuneinapi.categories.mvp;

import com.hoomi.tuneinapi.categories.model.Category;

import java.util.List;

public interface GenresContract {
    interface View extends CommonView {

        void setGenres(List<Category> genres);

    }

    interface Presenter {

        void init(String url);
    }
}
