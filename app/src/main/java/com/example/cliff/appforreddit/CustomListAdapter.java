package com.example.cliff.appforreddit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

// A list containing multiple elements needs a custom adapter

class CustomListAdapter extends ArrayAdapter<SubredditPost> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;

    public CustomListAdapter(Context context, int resource, List<SubredditPost> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    // The ViewHolder will hold all the views in each list item
    static class ViewHolder {
        TextView author;
        TextView title;
        TextView updated;
        ProgressBar progressBar;
        ImageView image;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        // Get person's information
        String author = getItem(position).getAuthor();
        String title = getItem(position).getTitle();
        String updated = getItem(position).getDate_updated();
        String imgURL = getItem(position).getThumbnail_url();

        // Best design pattern for listView
        if (convertView == null) {

            // Inflate the view
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            // Get the views with ViewHolder
            holder = new ViewHolder();
            holder.author = (TextView) convertView.findViewById(R.id.cardAuthor);
            holder.title = (TextView) convertView.findViewById(R.id.cardTitle);
            holder.updated = (TextView) convertView.findViewById(R.id.cardUpdated);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.cardProgressDialog);
            holder.image = (ImageView) convertView.findViewById(R.id.cardImage);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the views
        holder.title.setText(title);
        holder.author.setText(author);
        holder.updated.setText(updated);

        // Use Picasso to load in the image
        int placeholder = mContext.getResources().getIdentifier("@drawable/placeholder", null, mContext.getPackageName());
        Picasso.with(mContext)
                .load(imgURL)
                .placeholder(placeholder)
                //.resize(50, 50)
                //.centerCrop()
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        return convertView;
    }
}