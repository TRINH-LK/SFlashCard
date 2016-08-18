package com.example.trinhle.sflashcard.utils;

import android.graphics.Bitmap;

import com.example.trinhle.sflashcard.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Trinh Le on 11/08/2016.
 */
public interface DisplayImage {
    DisplayImageOptions option = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.placeholder)
            .showImageForEmptyUri(R.drawable.placeholder)
            .showImageOnFail(R.drawable.problem)
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    ImageLoader imageLoader = ImageLoader.getInstance();

}
