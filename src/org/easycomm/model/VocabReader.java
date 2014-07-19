package org.easycomm.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.easycomm.util.CUtil;
import org.easycomm.util.FileUtil;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

public class VocabReader {

	private static final String ASSET_DIR = "vocab";
	
	private static VocabReader Singleton; 
	
	private List<VocabData> mAllVocabs;
	private Map<String, VocabData> mFileVocabMap;
	
	public static VocabReader getInstance(AssetManager assets) {
		if (Singleton == null) {
			Singleton = new VocabReader(assets);
		}
		
		return Singleton;
	}
	
	public VocabReader(AssetManager assets) {
		mAllVocabs = CUtil.makeList();
		try {
			for (String file : assets.list(ASSET_DIR)) {
				InputStream in = assets.open(ASSET_DIR + "/" + file);
				Drawable d = Drawable.createFromStream(in, null);
				String displayName = FileUtil.getName(file);
				mAllVocabs.add(new VocabData(displayName, displayName, file, d));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.sort(mAllVocabs, new Comparator<VocabData>() {
			@Override
			public int compare(VocabData o1, VocabData o2) {
				return o1.getFilename().compareTo(o2.getFilename());
			}
		});
		
		mFileVocabMap = CUtil.makeMap();
		for (VocabData v : mAllVocabs) {
			mFileVocabMap.put(v.getFilename(), v);
		}
	}
	
	public List<VocabData> getAllVocabData() {
		return mAllVocabs;
	}

	public VocabData getVocabData(String filename) {
		return mFileVocabMap.get(filename);
	}
	
}
