package com.example.ctheadimage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class CTData extends Application{
	private short cthead[][][];
	private short min, max;
	private static CTData instance;
	
	private CTData(){
		this.cthead = new short[113][256][256];
		this.min =0;
		this.max =0;
		instance = null;
	}
	public static CTData getInstance(){
		if(instance == null)
			instance = new CTData();
		return instance;
	}
		
	
	public short getCTData(int z, int x, int y){
			return cthead[z][x][y];
	}
	
	//将2bytes CT图像转成 4bytes数据图像然后创建bitmap，第一个byte为0xff，2-4btye数据映射成【0-255】灰度级别的图像数据
    //mode: 1: Front 2: Top 3: Side
	public Bitmap createBitmap(int slice, int mode){ 
		int width, height;
        Bitmap bmp = null; 
		if(cthead == null){
			return bmp;
		} 
		switch(mode){
			case 1:
				width = 256;
				height = 113;
				break;
			case 2:
				width = 256;
				height = 256;
				break;
			case 3:
				width = 256;
				height = 113;
				break;
			default:
				width = 256;
				height = 256;
				break;
		}
    	int[] color = new int[width * height];
    	int nIndex = 0;
    	byte col;
    	short datum;
    	for(int i = 0; i < height; i++){
    		for(int j = 0; j < width; j++){
    			if(nIndex < width * height){
    				if(mode == 1)
    					datum = cthead[i][slice][j];
    				else if(mode == 2)
    					datum = cthead[slice][i][j];
    				else
    					datum = cthead[i][j][slice];
    				col=(byte)(255.0f*((float)datum-(float)min)/((float)(max-min)));
    				color[nIndex] = (col << 16 & 0x00FF0000) |   
                    (col << 8 & 0x0000FF00 ) |   
                    (col & 0x000000FF ) |   
                     0xFF000000;  
    				nIndex++;
    			}
    			else
    				break;
    		}
    	}
        try {  
        	bmp = Bitmap.createBitmap(color, 0, width, width, height,   
                    Bitmap.Config.RGB_565);  
        } catch (Exception e) {  
            // TODO: handle exception  
            return null;  
        }                 
        return bmp;  
    }  
	
	public int createCTData(Context context) throws IOException{
		int i, j, k;
		int b1,b2;
		short read;
		Log.d("CTData", "dsajdi1239213i921");
		InputStream in = context.getResources().getAssets().open("CThead");
		DataInputStream buffer = new DataInputStream(new BufferedInputStream(in));
		//loop through the data reading it in
		for (k=0; k<113; k++) {
			for (j=0; j<256; j++) {
				for (i=0; i<256; i++) {
					//because the Endianess is wrong, it needs to be read byte at a time and swapped
					b1=((int)buffer.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types (C++ is so much easier!)
					b2=((int)buffer.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types (C++ is so much easier!)
					read=(short)((b2<<8) | b1); //and swizzle the bytes around
					if (read<min) min=read; //update the minimum
					if (read>max) max=read; //update the maximum
					cthead[k][j][i]=read; //put the short into memory (in C++ you can replace all this code with one fread)
				}
			}
		}
		return 0;
	}
}
