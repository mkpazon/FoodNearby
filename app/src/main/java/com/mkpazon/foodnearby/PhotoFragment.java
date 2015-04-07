package com.mkpazon.foodnearby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by mkpazon on 7/4/15.
 * -=Bitbitbitbit=-
 */
public class PhotoFragment extends Fragment {

    private static final String TAG = "PhotoFragment";
    private static final String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?";
    public static final String EXTRA_PHOTO_REFERENCE = "photoReference";

    private ImageView ivPhoto;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ivPhoto = (ImageView) view.findViewById(R.id.imageView_photo);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String photoReference = bundle.getString(EXTRA_PHOTO_REFERENCE);
            Log.d(TAG, "PhotoReference:" + photoReference);
            String url = PHOTO_URL + "maxwidth=400"
                    + "&photoreference=" + photoReference
                    + "&key="+getActivity().getString(R.string.google_browser_key);

            ImageLoader.getInstance().displayImage(url, ivPhoto);

        } else {
            Log.w(TAG, "Failed to get photo reference. Bundle data is null.");
        }
    }
}
