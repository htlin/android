package org.easycomm.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListSet<T> implements Iterable<T> {

	private List<T> mList;
	private Set<T> mSet;
	
	public ListSet() {
		mList = new ArrayList<T>();
		mSet = new HashSet<T>();
	}

	public List<T> getList() {
		return mList;
	}

	public Set<T> getSet() {
		return mSet;
	}

	public T get(int location) {
		return mList.get(location);
	}
	
	public boolean contains(T a) {
		return mSet.contains(a);
	}
	
	public boolean add(T a) {
		if (mSet.add(a)) {
			mList.add(a);
			return true;
		} else {
			return false;
		}		
	}
	
	public boolean add(int location, T a) {
		if (mSet.contains(a)) {
			return false;
		} else {
			mList.add(location, a);
			mSet.add(a);
			return true;
		}		
	}
	
	public boolean remove(T a) {
		if (mSet.contains(a)) {
			mList.remove(a);
			mSet.remove(a);
			return true;
		} else {
			return false;
		}
	}
	
	public T remove(int location) {
		T a = mList.remove(location);
		mSet.remove(a);
		return a;
	}
	
	private int indexOf(T a) {
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).equals(a)) {
				return i;
			}
		}		
		return -1;
	}
	
	public void move(T source, T target) {
		int sourceIndex = indexOf(source);
		int targetIndex = indexOf(target);
		if (sourceIndex == targetIndex) return;
		
		T sourceNode = mList.remove(sourceIndex);
		mList.add(targetIndex, sourceNode);
	}

	@Override
	public Iterator<T> iterator() {
		return mList.iterator();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		ListSet<T> o = (ListSet<T>) other;
		return mList.equals(o.mList);
	}
	
	@Override
	public int hashCode() {
		return mList.hashCode();
	}

}
