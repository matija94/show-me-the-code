package com.matija.spendless;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by matija on 28.2.18..
 */

public class CategoriesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.HORIZONTAL,false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        mRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CategoryAdapter(Arrays.asList("Bills", "Food", "Health", "Clothes", "Nightlife", "Sports", "Education"));
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {
        private View mItemView;
        private TextView mTextView;

        public CategoryHolder(View viewItem) {
            super(viewItem);
            mItemView = viewItem;
            mTextView = mItemView.findViewById(R.id.categoryItemTextView);
        }

        public void bindItem(String text) {
            mTextView.setText(text);
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
        private List<String> mCategories = new ArrayList<>();

        public CategoryAdapter(List<String> categories) {
            mCategories = categories;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.category_item, parent, false);
            return new CategoryHolder(v);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            String category = mCategories.get(position);
            holder.bindItem(category);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }
}
