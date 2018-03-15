package com.tuinforma.tuinforma.Secciones;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.tuinforma.tuinforma.R;

public class Videos extends Fragment {
    private VideoView videoView;
    MediaController mc;

    public Videos() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.activity_videos, container, false);
        View view = inflater.inflate(R.layout.activity_videos, container, false);

        videoView =(VideoView)view.findViewById(R.id.videoView);

        mc = new MediaController(getActivity());

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String videoPath = "android.resource://com.tuinforma.tuinforma/"+R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mc);
        mc.setAnchorView(videoView);
        return view;
    }
}
