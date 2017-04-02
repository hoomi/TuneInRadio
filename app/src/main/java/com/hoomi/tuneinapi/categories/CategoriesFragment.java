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
import com.hoomi.tuneinapi.categories.adapter.CategoriesAdapter;
import com.hoomi.tuneinapi.categories.model.Category;
import com.hoomi.tuneinapi.categories.mvp.CategoriesContract;
import com.hoomi.tuneinapi.categories.mvp.CategoriesPresenterImp;
import com.hoomi.tuneinapi.categories.service.TuneInRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesFragment extends Fragment implements CategoriesContract.View {

    @BindView(R.id.categories_recycler_view)
    RecyclerView categoriesRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.error_textview)
    TextView errorTextView;

    private NavigationHelper listener;
    private CategoriesContract.Presenter presenter;

    public CategoriesFragment() {
    }

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CategoriesPresenterImp(this, TuneInRepository.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesRecyclerView.setAdapter(new CategoriesAdapter(null, listener));
        presenter.init();
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
    public void showItems(List<Category> categories) {
        RecyclerView.Adapter adapter = categoriesRecyclerView.getAdapter();
        if (adapter == null) {
            categoriesRecyclerView.setAdapter(new CategoriesAdapter(categories,listener));
        } else {
            ((CategoriesAdapter)adapter).setItems(categories);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
        categoriesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        categoriesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        errorTextView.setVisibility(View.VISIBLE);
    }
}
