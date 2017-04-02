package com.hoomi.tuneinapi.categories.mvp;

import com.hoomi.tuneinapi.categories.model.BrowsableItem;
import com.hoomi.tuneinapi.categories.service.TuneInRepository;

import java.util.List;

public class GenreDetailsPresenterImp implements GenreDetailsContract.Presenter, TuneInRepository.TuneInResponseListener<List<BrowsableItem>> {

    private GenreDetailsContract.View view;
    private TuneInRepository tuneInRepository;

    public GenreDetailsPresenterImp(GenreDetailsContract.View view, TuneInRepository tuneInRepository) {
        this.view = view;
        this.tuneInRepository = tuneInRepository;
    }

    @Override
    public void init(String url) {
        view.showProgress();
        tuneInRepository.requestGenreDetails(url, this);
    }


    @Override
    public void onSuccess(List<BrowsableItem> response) {
        if (response != null) {
            view.showDetails(response);
        }
        view.hideProgress();
    }

    @Override
    public void onError(String errorMessage) {
        view.showError(errorMessage);
        view.hideProgress();
    }
}
