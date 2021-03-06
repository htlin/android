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
		
		VocabData whereData = vocabReader.getVocabData("go.png").clone();
		whereData.setDisplayText("Go to");
		whereData.setSpeechText("Go to");
		Folder where = mVocabGraph.makeFolder(whereData);
		
		VocabData arrowData = vocabReader.getVocabData("up.png").clone();
		arrowData.setDisplayText("Arrow");
		arrowData.setSpeechText(null);
		Folder arrow = mVocabGraph.makeFolder(arrowData);

		VocabData animalData = vocabReader.getVocabData("bird.png").clone();
		animalData.setDisplayText("Animal");
		animalData.setSpeechText(null);
		Folder animal = mVocabGraph.makeFolder(animalData);
		
		mVocabGraph.addChild(mVocabGraph.getRoot(), where);
		mVocabGraph.addChild(mVocabGraph.getRoot(), animal);
		mVocabGraph.addChild(mVocabGraph.getRoot(), arrow);
		addToFolder(mVocabGraph.getRoot(),
				vocabReader.getVocabData("eat.png"),
				vocabReader.getVocabData("drink.png"),
				vocabReader.getVocabData("yes.png"),
				vocabReader.getVocabData("no.png")
			);
		
		VocabData dirData = vocabReader.getVocabData("up.png").clone();
		dirData.setDisplayText("Direction");
		dirData.setSpeechText(null);
		Link dir = mVocabGraph.makeLink(dirData);
		mVocabGraph.addChild(where, dir);
		mVocabGraph.addChild(dir, arrow);
		addToFolder(where,
				vocabReader.getVocabData("home.png"),
				vocabReader.getVocabData("toilet.png"),
				vocabReader.getVocabData("tree.png"),
				vocabReader.getVocabData("window.png")
			);
		addToFolder(arrow,
				vocabReader.getVocabData("left.png"),
				vocabReader.getVocabData("right.png"),
				vocabReader.getVocabData("up.png"),
				vocabReader.getVocabData("down.png")
			);
		addToFolder(animal,
				vocabReader.getVocabData("bird.png"),
				vocabReader.getVocabData("cat.png"),
				vocabReader.getVocabData("dog.png")
			);
		// Mass producing vocab
		for(int i=0; i<100; i++){
			addToFolder(animal,
					vocabReader.getVocabData("bird.png"),
					vocabReader.getVocabData("cat.png"),
					vocabReader.getVocabData("dog.png")
				);
		}
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