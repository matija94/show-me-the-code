package com.matija.spendless.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.matija.spendless.R;
import com.matija.spendless.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matija on 10.3.18..
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private List<Category> mCategories = new ArrayList<>();
    private Context mContext;

    public CategoryAdapter(List<Category> categories, Context context) {
        mContext = context;
        mCategories = categories;
    }

    static class CategoryHolder extends RecyclerView.ViewHolder {
        private View mItemView;
        private TextView mTextView;
        private ImageView mImageView;

        public CategoryHolder(View viewItem) {
            super(viewItem);
            mItemView = viewItem;
            mTextView = mItemView.findViewById(R.id.categoryItemTextView);
            mImageView = mItemView.findViewById(R.id.imageView);
        }

        public void bindItem(Category category) {
            mTextView.setText(category.getName());
            if (category.getDrawableId() == R.drawable.oval) {

                category.setDrawableId(R.drawable.party); // ??????????
            }
            mImageView.setImageResource(category.getDrawableId());
        }
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        Category category = mCategories.get(position);
        holder.bindItem(category);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public void addCategoryItems(List<Category> categories) {
        this.mCategories.addAll(categories);
    }
}
