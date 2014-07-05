package org.easycomm.model;

import org.easycomm.model.graph.Folder;
import org.easycomm.model.graph.Leaf;
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
		Folder animal = mVocabGraph.makeFolder(vocabReader.getVocabData("bird"));
		addToFolder(animal,
				vocabReader.getVocabData("bird"),
				vocabReader.getVocabData("cat"),
				vocabReader.getVocabData("dog")
			);
		Folder dir = mVocabGraph.makeFolder(vocabReader.getVocabData("up"));
		addToFolder(dir,
				vocabReader.getVocabData("left"),
				vocabReader.getVocabData("right"),
				vocabReader.getVocabData("up"),
				vocabReader.getVocabData("down")
			);
		addToFolder(mVocabGraph.getRoot(),
				vocabReader.getVocabData("go"),
				vocabReader.getVocabData("eat"),
				vocabReader.getVocabData("drink"),
				vocabReader.getVocabData("yes"),
				vocabReader.getVocabData("no")
			);
	}
	
	private void addToFolder(Folder folder, VocabData ... data) {
		for (VocabData d : data) {
			Leaf leaf = mVocabGraph.makeLeaf(d);
			mVocabGraph.add(folder.getID(), leaf);
		}
	}

	public VocabGraph getGraph() {
		return mVocabGraph;
	}
	
	public VocabData getVocabData(String id) {
		return mVocabGraph.getVocab(id).getData();
	}
	
	public void save() {
	}

	public void revert() {
	}

}