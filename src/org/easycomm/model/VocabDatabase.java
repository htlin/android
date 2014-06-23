package org.easycomm.model;

import java.util.List;
import java.util.Map;

import org.easycomm.model.tree.LinkedTree;
import org.easycomm.util.CUtil;

import android.content.res.AssetManager;

public class VocabDatabase {

	private static VocabDatabase Singleton; 
	
	private LinkedTree<Vocab> mVocabTree;
	
//	private List<Vocab> mVocabs;
//	private Map<String, Vocab> mVocabMap;
	
	public static VocabDatabase getInstance(AssetManager assets) {
		if (Singleton == null) {
			Singleton = new VocabDatabase(assets);
		}
		
		return Singleton;
	}
	
	
	private VocabDatabase(AssetManager assets) {
		//TODO - currently the same from readAll
		List<Vocab> vocabs = VocabReader.getInstance(assets).getAllVocabs();
		
		mVocabTree = new LinkedTree<Vocab>();
		
		save();
	}
	
	public int size() {
		return mVocabTree.size();
	}
	
	public Vocab getVocab(String id) {
		return mVocabTree.getNode(id).getObject();
	}
	
	public Vocab getVocab(int index) {
		return mVocabs.get(index);
	}

	public void add(Vocab v) {
		mVocabs.add(v);
		mVocabMap.put(v.getID(), v);
	}
	
	public void remove(String id) {
		mVocabTree.removeNode(id);
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
	}

	public void revert() {
	}

}