package com.example.android.photosapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.photosapp.utils.FlickrUtils;

/**
 * Created by hessro on 3/13/18.
 */

public class FlickrPhotoGridAdapter extends RecyclerView.Adapter<FlickrPhotoGridAdapter.FlickrPhotoViewHolder> {
    private FlickrUtils.FlickrPhoto[] mPhotos;
    private OnPhotoItemClickedListener mOnPhotoItemClickedListener;

    public FlickrPhotoGridAdapter(OnPhotoItemClickedListener clickedListener) {
        mOnPhotoItemClickedListener = clickedListener;
    }

    public void updatePhotos(FlickrUtils.FlickrPhoto[] photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlickrPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.photo_grid_item, parent, false);
        return new FlickrPhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrPhotoViewHolder holder, int position) {
        holder.bind(mPhotos[position]);
    }

    @Override
    public int getItemCount() {
        if (mPhotos != null) {
            return mPhotos.length;
        } else {
            return 0;
        }
    }

    public interface OnPhotoItemClickedListener {
        void onPhotoItemClicked(int photoIdx);
    }

    class FlickrPhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mPhotoIV;

        public FlickrPhotoViewHolder(View itemView) {
            super(itemView);
            mPhotoIV = itemView.findViewById(R.id.iv_photo);
            itemView.setOnClickListener(this);
        }

        public void bind(FlickrUtils.FlickrPhoto photo) {
            Glide.with(mPhotoIV.getContext())
                    .load(photo.url_m)
                    .apply(RequestOptions.placeholderOf(new SizedColorDrawable(Color.WHITE, photo.width_m, photo.height_m)))
                    .into(mPhotoIV);
        }

        @Override
        public void onClick(View v) {
            mOnPhotoItemClickedListener.onPhotoItemClicked(getAdapterPosition());
        }
    }

    class SizedColorDrawable extends ColorDrawable {
        int mWidth = -1;
        int mHeight = -1;

        public SizedColorDrawable(int color, int width, int height) {
            super(color);
            mWidth = width;
            mHeight = height;
        }

        @Override
        public int getIntrinsicWidth() {
            return mWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mHeight;
        }
    }
}
