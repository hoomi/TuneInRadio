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
import com.hoomi.tuneinapi.categories.adapter.GenreDetailsAdapter;
import com.hoomi.tuneinapi.categories.model.BrowsableItem;
import com.hoomi.tuneinapi.categories.mvp.GenreDetailsContract;
import com.hoomi.tuneinapi.categories.mvp.GenreDetailsPresenterImp;
import com.hoomi.tuneinapi.categories.service.TuneInRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenreDetailsFragment extends Fragment implements GenreDetailsContract.View {

    private static final String KEY_URL = "ur;";

    @BindView(R.id.gener_details_recycler_view)
    RecyclerView genereDetailsRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.error_textview)
    TextView errorTextView;
    private GenreDetailsContract.Presenter presenter;
    private NavigationHelper listener;

    public static GenreDetailsFragment newInstance(String url) {
        GenreDetailsFragment genreDetailsFragment = new GenreDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        genreDetailsFragment.setArguments(bundle);
        return genreDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GenreDetailsPresenterImp(this, TuneInRepository.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(getArguments().getString(KEY_URL));
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
        genereDetailsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        genereDetailsRecyclerView.setVisibility(View.VISIBLE);
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
    public void showError(String errorMessage) {
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetails(List<BrowsableItem> browsableItems) {
        RecyclerView.Adapter adapter = genereDetailsRecyclerView.getAdapter();
        if (adapter == null) {
            genereDetailsRecyclerView.setAdapter(new GenreDetailsAdapter(browsableItems, listener));
        } else {
            ((GenreDetailsAdapter) adapter).setItems(browsableItems);
        }
    }
}
