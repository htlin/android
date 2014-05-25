package org.easycomm.db;

import java.util.List;
import java.util.Map;

import org.easycomm.util.CUtil;

import android.content.res.AssetManager;

public class VocabDatabase {

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
		//TODO - currently the same from readAll
		mVocabs = VocabReader.getInstance(assets).getAllVocabs();
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

	public String getDisplayText(String id) {
		return mVocabMap.get(id).getDisplayText();
	}

	public String getSpeechText(String id) {
		return mVocabMap.get(id).getSpeechText();
	}

	public void add(Vocab v) {
		mVocabs.add(v);
		mVocabMap.put(v.getID(), v);
	}
	
	public void remove(String id) {
		Vocab v = mVocabMap.remove(id);
		mVocabs.remove(v);
	}

	public void move(String sourceID, String targetID) {
		if (sourceID.equals(targetID)) return;
		
		Vocab sourceVocab = mVocabMap.get(sourceID);
		Vocab targetVocab = mVocabMap.get(targetID);
		
		int targetIndex = mVocabs.indexOf(targetVocab);
		mVocabs.remove(sourceVocab);
		mVocabs.add(targetIndex, sourceVocab);
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