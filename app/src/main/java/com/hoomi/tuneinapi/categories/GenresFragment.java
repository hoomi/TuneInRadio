package com.hoomi.tuneinapi.categories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hoomi.tuneinapi.NavigationHelper;
import com.hoomi.tuneinapi.R;
import com.hoomi.tuneinapi.categories.adapter.GenreAdapter;
import com.hoomi.tuneinapi.categories.model.Category;
import com.hoomi.tuneinapi.categories.mvp.GenrePresenterImp;
import com.hoomi.tuneinapi.categories.mvp.GenresContract;
import com.hoomi.tuneinapi.categories.service.TuneInRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresFragment extends Fragment implements GenresContract.View {

    public static final String KEY_URL = "url";

    @BindView(R.id.genres_recycler_view)
    RecyclerView genresRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.error_textview)
    TextView errorTextView;
    private GenresContract.Presenter presenter;
    private NavigationHelper listener;

    public static GenresFragment newInstance(String key) {
        GenresFragment genresFragment = new GenresFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, key);
        genresFragment.setArguments(bundle);
        return genresFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GenrePresenterImp(this, TuneInRepository.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(getArguments().getString(KEY_URL));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationHelper) {
            listener = (NavigationHelper) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NavigationHelper");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
        genresRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        genresRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setGenres(List<Category> genres) {
        if (genresRecyclerView.getAdapter() == null) {
            genresRecyclerView.setAdapter(new GenreAdapter(genres, listener));
        }
    }
}
