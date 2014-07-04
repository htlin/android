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
		VocabData[] animals = new VocabData[] {
			vocabReader.getVocabData("bird"),
			vocabReader.getVocabData("cat"),
			vocabReader.getVocabData("dog"),
		};
		
		VocabData[] dirs = new VocabData[] {
			vocabReader.getVocabData("left"),
			vocabReader.getVocabData("right"),
			vocabReader.getVocabData("up"),
			vocabReader.getVocabData("down"),
		};
		
		VocabData[] others = new VocabData[] {
			vocabReader.getVocabData("go"),
			vocabReader.getVocabData("eat"),
			vocabReader.getVocabData("drink"),
			vocabReader.getVocabData("yes"),
			vocabReader.getVocabData("no"),
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