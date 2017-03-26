package com.example.ctheadimage;

import java.io.IOException;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SideView extends Fragment{
	private SeekBar seekbar;
	private ImageView imgview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_side, container, false);
		seekbar = (SeekBar)view.findViewById(R.id.side_slider);
		imgview = (ImageView)view.findViewById(R.id.side_img);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				Bitmap curimg = null;
				curimg = CTData.getInstance().createBitmap(progress, 3);
				imgview.setImageBitmap(curimg);
			}
		});
		Bitmap img = null;
		img = CTData.getInstance().createBitmap(seekbar.getProgress(), 3);
		imgview.setImageBitmap(img);
		return view;
	}
}
