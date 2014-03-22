package org.easycomm.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.easycomm.util.CUtil;
import org.easycomm.util.FileUtil;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

public class VocabDatabase {

	private static final String ASSET_DIR = "vocab";
	
	private static VocabDatabase Singleton; 
	
	private List<Vocab> mVocabs;
	private Map<String, Vocab> mVocabMap;

	//Temporary to mock persistent files
	private List<Vocab> mLastVocabs;
	private Map<String, Vocab> mLastVocabMap;
	
	public static VocabDatabase getInstance(AssetManager assets) {
		if (Singleton == null) {
			Singleton = new VocabDatabase(assets);
		}
		
		return Singleton;
	}
	
	private VocabDatabase(AssetManager assets) {
		mVocabs = CUtil.makeList();
		try {
			for (String file : assets.list(ASSET_DIR)) {
				InputStream in = assets.open(ASSET_DIR + "/" + file);
				Drawable d = Drawable.createFromStream(in , null);
				String displayName = FileUtil.getName(file);
				String id = displayName;
				mVocabs.add(new Vocab(id, displayName, d));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mVocabMap = CUtil.makeMap();
		for (Vocab v : mVocabs) {
			mVocabMap.put(v.getID(), v);
		}
		
		save();
	}
	
	public int size() {
		return mVocabs.size();
	}
	
	public Vocab getVocab(String id) {
		return mVocabMap.get(id);
	}
	
	public Vocab getVocab(int index) {
		return mVocabs.get(index);
	}

	public String getText(String id) {
		return mVocabMap.get(id).getText();
	}

	public void remove(String id) {
		Vocab v = mVocabMap.remove(id);
		mVocabs.remove(v);
	}

	public void save() {
		mLastVocabs = CUtil.makeList(mVocabs);
		mLastVocabMap = CUtil.makeMap(mVocabMap);
	}

	public void revert() {
		mVocabs = CUtil.makeList(mLastVocabs);
		mVocabMap = CUtil.makeMap(mLastVocabMap);
	}

}