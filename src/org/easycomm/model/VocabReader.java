package org.easycomm.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.easycomm.model.graph.Vocab;
import org.easycomm.util.CUtil;
import org.easycomm.util.FileUtil;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

public class VocabReader {

	private static final String ASSET_DIR = "vocab";
	
	private static VocabReader Singleton; 
	
	private List<Vocab> mAllVocabs;
	private Map<String, Vocab> mAllVocabMap;
	
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
				String id = displayName;
				mAllVocabs.add(new Vocab(id, displayName, displayName, file, d));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.sort(mAllVocabs, new Comparator<Vocab>() {
			@Override
			public int compare(Vocab o1, Vocab o2) {
				return o1.getFilename().compareTo(o2.getFilename());
			}
		});
		
		mAllVocabMap = CUtil.makeMap();
		for (Vocab v : mAllVocabs) {
			mAllVocabMap.put(v.getID(), v);
		}
	}
	
	public List<Vocab> getAllVocabs() {
		return mAllVocabs;
	}

	public Vocab getVocab(String id) {
		return mAllVocabMap.get(id);
	}
	
	public int indexOf(String id) {
		for (int i = 0; i < mAllVocabs.size(); i++) {
			if (mAllVocabs.get(i).getID().equals(id)) {
				return i;
			}
		}
		
		return -1;
	}
	
}
