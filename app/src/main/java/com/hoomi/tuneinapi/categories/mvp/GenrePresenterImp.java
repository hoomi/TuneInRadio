package com.hoomi.tuneinapi.categories.mvp;

import com.hoomi.tuneinapi.categories.model.Category;
import com.hoomi.tuneinapi.categories.service.TuneInRepository;

import java.util.List;

public class GenrePresenterImp implements GenresContract.Presenter, TuneInRepository.TuneInResponseListener<List<Category>> {
    private GenresContract.View view;
    private TuneInRepository tuneInRepository;

    public GenrePresenterImp(GenresContract.View view, TuneInRepository tuneInRepository) {
        this.view = view;
        this.tuneInRepository = tuneInRepository;
    }

    @Override
    public void init(String url) {
        view.showProgress();
        tuneInRepository.requestGenres(url, this);
    }

    @Override
    public void onSuccess(List<Category> response) {
        if (response != null) {
            view.setGenres(response);
        }
        view.hideProgress();
    }

    @Override
    public void onError(String errorMessage) {
        view.showError(errorMessage);
        view.hideProgress();
    }
}
