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
	
	public LinkedTree<Vocab> get() {
		return mVocabTree;
	}
	
	public void save() {
	}

	public void revert() {
	}

}