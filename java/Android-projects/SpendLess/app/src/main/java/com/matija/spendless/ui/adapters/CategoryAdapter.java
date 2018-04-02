package com.matija.spendless.ui.adapters;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.matija.spendless.R;
import com.matija.spendless.model.Category;
import com.matija.spendless.ui.CategoriesActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by matija on 10.3.18..
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    public interface CategoryClickListener {
        /**
         * On category item clicked
         * @param category
         */
        void onClick(Category category);

        /**
         * On category long press
         * @param category
         * @param item
         */
        void onCategoryMenuItemClick(Category category, MenuItem item);
    }

    private List<Category> mCategories = new ArrayList<>();
    private Context mContext;
    private CategoryClickListener categoryClickListener;

    public CategoryAdapter(List<Category> categories, Context context) {
        mContext = context;
        mCategories = categories;
        categoryClickListener = (CategoriesActivity) context;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(v,categoryClickListener);
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
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return CategoryAdapter.this.mCategories.size();
            }

            @Override
            public int getNewListSize() {
                return categories.size();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                List<Category> oldCats = CategoryAdapter.this.mCategories;
                return oldCats.get(oldItemPosition).getName().equals(categories.get(newItemPosition).getName());
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                List<Category> oldCats = CategoryAdapter.this.mCategories;
                return oldCats.get(oldItemPosition).getId() == categories.get(newItemPosition).getId();
            }
        });
        mCategories = categories;
        diffResult.dispatchUpdatesTo(this);
    }

    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {
        private View mItemView;
        private TextView mTextView;
        private ImageView mImageView;
        private CategoryClickListener categoryClickListener;

        public CategoryHolder(View viewItem, CategoryClickListener categoryClickListener) {
            super(viewItem);
            mItemView = viewItem;
            mTextView = mItemView.findViewById(R.id.categoryItemTextView);
            mImageView = mItemView.findViewById(R.id.imageView);
            this.categoryClickListener = categoryClickListener;
        }

        public void bindItem(Category category) {
            mTextView.setText(category.getName());
            if (category.getDrawableId() == R.drawable.oval) {

                category.setDrawableId(R.drawable.party); // ??????????
            }
            mImageView.setImageResource(category.getDrawableId());
            mItemView.setOnClickListener(v -> categoryClickListener.onClick(category));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.menu_categories, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Category category = mCategories.get(getAdapterPosition());
            categoryClickListener.onCategoryMenuItemClick(category, item);
            return false;
        }
    }
}
