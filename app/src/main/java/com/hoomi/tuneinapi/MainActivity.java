package com.hoomi.tuneinapi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.hoomi.tuneinapi.categories.CategoriesFragment;
import com.hoomi.tuneinapi.categories.GenreDetailsFragment;
import com.hoomi.tuneinapi.categories.GenresFragment;
import com.hoomi.tuneinapi.categories.model.BrowsableItem;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationHelper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showCategories();
    }


    @Override
    public void showCategory(BrowsableItem category) {
        String fragmentTag = GenresFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = GenresFragment.newInstance(category.getUrl());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, fragment, fragmentTag)
                    .addToBackStack(fragmentTag)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void showGenre(BrowsableItem genre) {
        String fragmentTag = GenreDetailsFragment.class.getSimpleName();
        Fragment fragment = GenreDetailsFragment.newInstance(genre.getUrl());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss();
    }

    private void showCategories() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CategoriesFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = CategoriesFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, fragment, CategoriesFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
    }
}
