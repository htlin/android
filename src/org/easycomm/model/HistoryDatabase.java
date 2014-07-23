package org.easycomm.model;

import org.easycomm.model.graph.VocabGraph;
import org.easycomm.util.CUtil;

import android.content.res.AssetManager;

import java.util.List;

public class HistoryDatabase {
	
	private static HistoryDatabase Singleton; 
	
	private List<HistoryData> mHistory;
	
	public static HistoryDatabase getInstance(AssetManager assets) {
		if (Singleton == null) {
			Singleton = new HistoryDatabase(assets);
		}
		
		return Singleton;
	}
	
	private HistoryDatabase(AssetManager assets) {
		mHistory = CUtil.makeList();
	}
	
	public void append(HistoryData data){
		mHistory.add(data);
	}
	
	public List<HistoryData> getHistory(){
		return mHistory;
	}
	
	public void clear(){
		mHistory.clear();
	}

}
