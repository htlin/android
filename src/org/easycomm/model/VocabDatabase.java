package org.easycomm.model;

import java.util.Map;

import org.easycomm.model.graph.Folder;
import org.easycomm.model.graph.Leaf;
import org.easycomm.model.graph.Link;
import org.easycomm.model.graph.Vocab;
import org.easycomm.model.graph.VocabGraph;
import org.easycomm.util.CUtil;

import android.content.res.AssetManager;

public class VocabDatabase {

	public static final String PROTOTYPE_LEAF_ID = "pro_leaf";
	public static final String PROTOTYPE_FOLDER_ID = "pro_folder";
	public static final String PROTOTYPE_LINK_ID = "pro_link";
	
	private static VocabDatabase Singleton; 
	
	private VocabGraph mVocabGraph;
	
	private Map<String, Vocab> mPrototypes;
	
	public static VocabDatabase getInstance(AssetManager assets) {
		if (Singleton == null) {
			Singleton = new VocabDatabase(assets);
		}
		
		return Singleton;
	}
	
	private void makePrototypes() {
		mPrototypes = CUtil.makeMap();
		mPrototypes.put(PROTOTYPE_LEAF_ID, new Leaf(PROTOTYPE_LEAF_ID, null));
		mPrototypes.put(PROTOTYPE_FOLDER_ID, new Folder(PROTOTYPE_FOLDER_ID, null));
		mPrototypes.put(PROTOTYPE_LINK_ID, new Link(PROTOTYPE_LINK_ID, null));
	}
	
	private VocabDatabase(AssetManager assets) {
		makePrototypes();
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

	public boolean isPrototype(String id) {
		return mPrototypes.containsKey(id);
	}

	public Vocab getPrototype(String id) {
		return mPrototypes.get(id);
	}
	
	public Vocab getVocab(String id) {
		Vocab p = getPrototype(id);
		if (p == null) {
			return mVocabGraph.getVocab(id);
		} else {
			return p;
		}
	}
	
	public VocabData getVocabData(String id) {
		return getVocab(id).getData();
	}
	
	public void save() {
	}

	public void revert() {
	}

}