package com.example.lifehelp_main.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
public class CopyFile {
	/**
	 * dirPath = "/data/data/com.baidu.locTest/databases"
	 * is = this.getApplicationContext().getResources().openRawResource(R.raw.zhy);
	 * @param is
	 */
	public static void copyFileFromResToPhone(String dirPath,String fileName,InputStream  is){
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(dir, fileName);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int i = 0;
			while((i = is.read(buffer)) != -1){
				fos.write(buffer,0,i);
			}
			if(fos != null){
				fos.close();
			}
			if(is != null){
				is.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
