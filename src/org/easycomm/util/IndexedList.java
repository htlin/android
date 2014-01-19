package org.easycomm.util;

import java.util.List;
import java.util.Map;

public class IndexedList<T> {

	private List<T> mList;
	private Map<T,Integer> mIndex;
	
	public IndexedList(List<T> list) {
		mList = list;
		mIndex = CUtil.makeMap();
		for (int i = 0; i < list.size(); i++) {
			T v = list.get(i);
			mIndex.put(v, i);
		}
	}
	
	public T get(int i) {
		return mList.get(i);
	}
	
	public int size() {
		return mList.size();
	}
	
	public boolean contains(T element) {
		return mIndex.containsKey(element);
	}
	
	public int getIndex(T element) {
		if (mIndex.containsKey(element)) {
			return mIndex.get(element);
		} else {
			return -1;
		}
	}
	
}
