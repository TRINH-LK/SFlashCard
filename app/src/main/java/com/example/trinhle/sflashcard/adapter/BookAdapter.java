package com.example.trinhle.sflashcard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trinhle.sflashcard.R;
import com.example.trinhle.sflashcard.model.Book;
import com.example.trinhle.sflashcard.utils.DisplayImage;


import java.util.List;

/**
 * Created by Trinh Le on 09/08/2016.
 */
public class BookAdapter extends BaseAdapter implements DisplayImage{

    Context context;
    List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }
    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        BookViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.book_item, null);
            holder = new BookViewHolder();
            holder.ivBookImage = (ImageView) view.findViewById(R.id.iv_thumblink_book);
            holder.tvBookName = (TextView) view.findViewById(R.id.tv_book_name);
            holder.tvBookDesc = (TextView) view.findViewById(R.id.tv_book_desc);
            holder.tvNumCard = (TextView) view.findViewById(R.id.tv_book_numcard);

            view.setTag(holder);
        } else {
            holder = (BookViewHolder) view.getTag();
        }

        Book book = bookList.get(position);
        String url = book.getUrl();
        imageLoader.displayImage(url, holder.ivBookImage, option);
        String bookName = book.getBookName();
        holder.tvBookName.setText(bookName);
        String bookDesc = book.getDescription();
        holder.tvBookDesc.setText(bookDesc);
        int numCard = book.getNumCard();
        holder.tvNumCard.setText(Integer.toString(numCard));
        return view;
    }

    class BookViewHolder {
        private ImageView ivBookImage;
        private TextView tvBookName;
        private TextView tvBookDesc;
        private TextView tvNumCard;
    }
}
