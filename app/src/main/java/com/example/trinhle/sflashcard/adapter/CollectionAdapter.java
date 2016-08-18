package com.example.trinhle.sflashcard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trinhle.sflashcard.R;
import com.example.trinhle.sflashcard.model.Collection;
import com.example.trinhle.sflashcard.utils.DisplayImage;
import com.example.trinhle.sflashcard.utils.ImageLoaderApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Trinh Le on 05/08/2016.
 */
public class CollectionAdapter extends BaseAdapter implements DisplayImage {

    Context context;
    List<Collection> collectionList;

    public CollectionAdapter(Context context, List<Collection> collectionList){
        this.context = context;
        this.collectionList = collectionList;

    }

    @Override
    public int getCount() {
        return collectionList.size();
    }

    @Override
    public Object getItem(int position) {
        return collectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CollectionViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.collection_item, null);
            holder = new CollectionViewHolder();
            holder.ivThumbLink = (ImageView) view.findViewById(R.id.iv_thumblink);
            holder.tvCollectionName = (TextView) view.findViewById(R.id.tv_collection_name);
            holder.tvDescription = (TextView) view.findViewById(R.id.tv_collection_desc);
            holder.tvNumberBook = (TextView) view.findViewById(R.id.tv_number_book);

            view.setTag(holder);
        } else {
            holder = (CollectionViewHolder) view.getTag();
        }

        Collection item = collectionList.get(position);
        String thumbLink = item.getThumbLink();
        imageLoader.displayImage(thumbLink, holder.ivThumbLink, option);
        String itemName = item.getCollectionName();
        holder.tvCollectionName.setText(itemName);
        String itemDesc = item.getDescription();
        holder.tvDescription.setText(itemDesc);
        String itemNumber = item.getNumberBook();
        holder.tvNumberBook.setText(itemNumber);

        return view;
    }

    class CollectionViewHolder {
        private ImageView ivThumbLink;
        private TextView tvCollectionName;
        private TextView tvDescription;
        private TextView tvNumberBook;
    }
}
