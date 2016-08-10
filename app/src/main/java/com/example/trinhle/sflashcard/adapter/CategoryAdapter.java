package com.example.trinhle.sflashcard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trinhle.sflashcard.R;
import com.example.trinhle.sflashcard.model.Category;

import java.util.List;

/**
 * Created by Trinh Le on 05/08/2016.
 */
public class CategoryAdapter extends BaseAdapter {

    Context context;
    List<Category> categoryList;


    // Constructor
    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    // Get the number of category
    @Override
    public int getCount() {
        return categoryList.size();
    }

    // Get Category item at position
    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    // Get position of category item
    @Override
    public long getItemId(int position) {
        return position;
    }


    // Get a view displays Category data
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CategoryViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_item, null);
            holder = new CategoryViewHolder();
            holder.tvCategoryName = (TextView) view.findViewById(R.id.tv_category_name);
            holder.tvDescription = (TextView) view.findViewById(R.id.tv_description);
            holder.tvNumCol = (TextView) view.findViewById(R.id.tv_num_col);

            view.setTag(holder);
        } else {
            holder = (CategoryViewHolder) view.getTag();
        }

        Category category = categoryList.get(position);
        String categoryName = category.getCategoryName();
        holder.tvCategoryName.setText(categoryName);
        String description = category.getDescription();
        holder.tvDescription.setText(description);
        int numCol = category.getNumCol();
        holder.tvNumCol.setText(Integer.toString(numCol));

        return view;
    }

    class CategoryViewHolder {
        private TextView tvCategoryName;
        private TextView tvDescription;
        private TextView tvNumCol;
    }

    public void populateThumblink() {

    }
}
