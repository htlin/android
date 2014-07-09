package org.easycomm.model;

import org.easycomm.model.graph.Folder;
import org.easycomm.model.graph.Leaf;
import org.easycomm.model.graph.Link;
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
		
		VocabData whereData = vocabReader.getVocabData("go").clone();
		whereData.setDisplayText("Go to");
		whereData.setSpeechText("Go to");
		Folder where = mVocabGraph.makeFolder(whereData);
		
		VocabData arrowData = vocabReader.getVocabData("up").clone();
		arrowData.setDisplayText("Arrow");
		arrowData.setSpeechText(null);
		Folder arrow = mVocabGraph.makeFolder(arrowData);

		VocabData animalData = vocabReader.getVocabData("bird").clone();
		animalData.setDisplayText("Animal");
		animalData.setSpeechText(null);
		Folder animal = mVocabGraph.makeFolder(animalData);
		
		mVocabGraph.addChild(mVocabGraph.getRoot(), where);
		mVocabGraph.addChild(mVocabGraph.getRoot(), animal);
		mVocabGraph.addChild(mVocabGraph.getRoot(), arrow);
		addToFolder(mVocabGraph.getRoot(),
				vocabReader.getVocabData("eat"),
				vocabReader.getVocabData("drink"),
				vocabReader.getVocabData("yes"),
				vocabReader.getVocabData("no")
			);
		
		VocabData dirData = vocabReader.getVocabData("up").clone();
		dirData.setDisplayText("Direction");
		dirData.setSpeechText(null);
		Link dir = mVocabGraph.makeLink(dirData);
		mVocabGraph.addChild(where, dir);
		mVocabGraph.addChild(dir, arrow);
		addToFolder(where,
				vocabReader.getVocabData("home"),
				vocabReader.getVocabData("toilet"),
				vocabReader.getVocabData("tree"),
				vocabReader.getVocabData("window")
			);
		addToFolder(arrow,
				vocabReader.getVocabData("left"),
				vocabReader.getVocabData("right"),
				vocabReader.getVocabData("up"),
				vocabReader.getVocabData("down")
			);
		addToFolder(animal,
				vocabReader.getVocabData("bird"),
				vocabReader.getVocabData("cat"),
				vocabReader.getVocabData("dog")
			);
	}
	
	private void addToFolder(Folder folder, VocabData ... data) {
		for (VocabData d : data) {
			Leaf leaf = mVocabGraph.makeLeaf(d);
			mVocabGraph.addChild(folder, leaf);
		}
	}

	public VocabGraph getGraph() {
		return mVocabGraph;
	}
	
	public Vocab getVocab(String id) {
		return mVocabGraph.getVocab(id);
	}
	
	public VocabData getVocabData(String id) {
		return mVocabGraph.getVocab(id).getData();
	}
	
	public void save() {
	}

	public void revert() {
	}

}