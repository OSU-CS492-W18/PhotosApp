package com.example.android.photosapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.photosapp.R;
import com.example.android.photosapp.utils.FlickrUtils;

/**
 * Created by hessro on 3/15/18.
 */

public class FlickrPhotoPagerAdapter extends FragmentStatePagerAdapter {
    private static final String ARG_PHOTO_BUNDLE = "photoURL";

    private FlickrUtils.FlickrPhoto[] mPhotos;

    public FlickrPhotoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        if (mPhotos != null) {
            return mPhotos.length;
        } else {
            return 0;
        }
    }

    public void updatePhotos(FlickrUtils.FlickrPhoto[] photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new FlickrPhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PHOTO_BUNDLE, mPhotos[position].url_l);
        fragment.setArguments(args);
        return fragment;
    }

    public static class FlickrPhotoFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View itemView = inflater.inflate(R.layout.photo_pager_item, container, false);
            Bundle args = getArguments();
            String photoUrl = args.getString(ARG_PHOTO_BUNDLE);
            ImageView photoIV = itemView.findViewById(R.id.iv_photo);
            Glide.with(photoIV.getContext())
                    .load(photoUrl)
                    .into(photoIV);
            return itemView;
        }
    }
}
