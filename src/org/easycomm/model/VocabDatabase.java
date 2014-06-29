package org.easycomm.model;

import java.util.Map;

import org.easycomm.model.tree.LinkedTree;
import org.easycomm.util.CUtil;

import android.content.res.AssetManager;

public class VocabDatabase {

	private static VocabDatabase Singleton; 
	
	private LinkedTree<Vocab> mVocabTree;
	private Map<String, Vocab> mVocabMap;
	
	public static VocabDatabase getInstance(AssetManager assets) {
		if (Singleton == null) {
			Singleton = new VocabDatabase(assets);
		}
		
		return Singleton;
	}
	
	
	private VocabDatabase(AssetManager assets) {
		mVocabTree = new LinkedTree<Vocab>();
		mVocabMap = CUtil.makeMap();
		
		VocabReader vocabReader = VocabReader.getInstance(assets);
		Vocab[] animals = new Vocab[] {
			vocabReader.getVocab("bird"),
			vocabReader.getVocab("cat"),
			vocabReader.getVocab("dog"),
		};
		
		Vocab[] dirs = new Vocab[] {
			vocabReader.getVocab("left"),
			vocabReader.getVocab("right"),
			vocabReader.getVocab("up"),
			vocabReader.getVocab("down"),
		};
		
		Vocab[] others = new Vocab[] {
			vocabReader.getVocab("go"),
			vocabReader.getVocab("eat"),
			vocabReader.getVocab("drink"),
			vocabReader.getVocab("yes"),
			vocabReader.getVocab("no"),
		};
		
		
	}
	
	public LinkedTree<Vocab> getTree() {
		return mVocabTree;
	}
	
	public Map<String, Vocab> getMap() {
		return mVocabMap;
	}
	
	public void save() {
	}

	public void revert() {
	}

}