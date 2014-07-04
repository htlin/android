package org.easycomm.model;

import org.easycomm.model.graph.Vocab;
import org.easycomm.model.graph.VocabGraph;

import android.content.res.AssetManager;

public class VocabDatabase {

	private static VocabDatabase Singleton; 
	
	private VocabGraph mVocabGraph;
	
	public static VocabDatabase getInstance(AssetManager assets) {
		if (Singleton == null) {
			Singleton = new VocabDatabase(assets);
		}
		
		return Singleton;
	}
		
	private VocabDatabase(AssetManager assets) {
		mVocabGraph = new VocabGraph();
		
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
	
	public Vocab getVocab(String id) {
		return mVocabGraph.getVocab(id);
	}
	
	public void save() {
	}

	public void revert() {
	}

}