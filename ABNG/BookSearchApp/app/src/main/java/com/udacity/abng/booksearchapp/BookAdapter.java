package com.udacity.abng.booksearchapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yul on 28.06.16.
 */
public class BookAdapter extends BaseAdapter {
    private static final String TAG = "Log_adapter";

    private ArrayList books;
    private LayoutInflater layoutInflater;

    //Create a new LocationAdapter object
    public BookAdapter(Context context, ArrayList<Book> books) {
        this.books = books;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.title_textview);
            holder.descTextView = (TextView) convertView.findViewById(R.id.author_textview);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the {@link Location} object located at this position in the list
        Book currentBook = (Book) getItem(position);

        holder.nameTextView.setText(currentBook.getBookName());
        holder.descTextView.setText(currentBook.getAuthorName());
        if (holder.imageView != null) {
            new DownloadImagesTask(holder.imageView).execute(currentBook.getImageUrl());
        }


        // Return the whole list item layout so that it can be shown in the ListView.
        return convertView;

    }

    private class ViewHolder {
        TextView nameTextView;
        TextView descTextView;
        ImageView imageView;
    }

    class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;
        ImageView imageView = null;
        String url = (String) imageView.getTag();

        public DownloadImagesTask(ImageView imageView) {
            imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d(TAG, "DownloadImagesTask doInBackground starts");
            return downloadBitmap(params[0]);
        }

        private Bitmap downloadBitmap(String ulr) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.d(TAG, "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                Log.d(TAG, "DownloadImagesTask isCanceled");
                bitmap = null;
            }
            Log.d(TAG, "Image download onPostExecute" + imageView.toString());
            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.book1);
                        imageView.setImageDrawable(placeholder);
                    }
                }
            }
        }
    }


}
