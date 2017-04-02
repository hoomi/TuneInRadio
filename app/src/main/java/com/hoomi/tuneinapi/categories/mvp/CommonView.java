package com.hoomi.tuneinapi.categories.mvp;

public interface CommonView {
    void showProgress();

    void hideProgress();

    void showError(String error);
}
