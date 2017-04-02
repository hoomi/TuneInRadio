package com.hoomi.tuneinapi.categories.service;

import com.hoomi.tuneinapi.categories.model.BrowsableItem;
import com.hoomi.tuneinapi.categories.model.Category;

import java.util.List;

import retrofit2.Call;

public abstract class TuneInRepository {
    private static TuneInRepository tuneInRepository;

    public static synchronized TuneInRepository getInstance() {
        if (tuneInRepository == null) {
            tuneInRepository = new TuneInRepositoryImp();
        }
        return tuneInRepository;
    }

    public abstract Call<List<Category>> requestCategories(TuneInResponseListener<List<Category>> tuneInResponseListener);

    public abstract Call<List<Category>> requestGenres(String url, TuneInRepository.TuneInResponseListener<List<Category>> listener);

    public abstract Call<List<BrowsableItem>> requestGenreDetails(String url, TuneInRepository.TuneInResponseListener<List<BrowsableItem>> listener);

    public interface TuneInResponseListener<T> {
        void onSuccess(T response);

        void onError(String errorMessage);
    }
}
