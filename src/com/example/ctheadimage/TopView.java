package com.example.ctheadimage;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class TopView extends Fragment{
	private SeekBar seekbar;
	private ImageView imgview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_top, container, false);
		seekbar = (SeekBar)view.findViewById(R.id.top_slider);
		imgview = (ImageView)view.findViewById(R.id.top_img);
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
				curimg = CTData.getInstance().createBitmap(progress, 2);
				imgview.setImageBitmap(curimg);
			}
		});
		Bitmap img = null;
		img = CTData.getInstance().createBitmap(seekbar.getProgress(), 2);
		imgview.setImageBitmap(img);
		return view;
	}
}
