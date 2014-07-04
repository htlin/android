package org.easycomm.model.graph;

import org.easycomm.model.VocabData;


public abstract class Vocab {

	protected String mID;
	protected VocabData mData;
	
	public Vocab(String id, VocabData data) {
		mID = id;
		mData = data;
	}

	public String getID() {
		return mID;
	}
	
	public void setID(String id) {
		mID = id;		
	}
	
	public VocabData getData() {
		return mData;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vocab)) return false;
		
		Vocab o = (Vocab) obj;
		return mID.equals(o.mID);
	}

	@Override
	public int hashCode() {
		return mID.hashCode();
	}
	
}
