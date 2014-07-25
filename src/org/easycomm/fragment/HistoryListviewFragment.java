package org.easycomm.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.easycomm.R;
import org.easycomm.model.HistoryData;
import org.easycomm.model.HistoryDatabase;
import org.easycomm.util.CUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryListviewFragment extends Fragment {
	
	private HistoryDatabase mHistoryDB;
	private HistoryListviewAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		System.err.println("HistoryListviewFragment start...");
		mHistoryDB = HistoryDatabase.getInstance(getResources().getAssets());
		
		View view = inflater.inflate(R.layout.history_listview_layout, container, false);
	
		//Attach adapters
		ListView listView = (ListView) view.findViewById(R.id.history_listview);
		mAdapter = new HistoryListviewAdapter();
		mAdapter.setList(mHistoryDB.getHistory());
		listView.setAdapter(mAdapter);
				
		return view;
	}
	
	
	
	
	public void clearHistory(){
		System.err.println("in HistoryListviewFragment clearHistory");
		mHistoryDB.clear();		
		mAdapter.setList(mHistoryDB.getHistory());
		mAdapter.notifyDataSetChanged();
	
	}
	
	public void showByDate(int year, int month, int day){
		System.err.println("in HistoryListviewFragment showByDate "+year+","+month+","+day);
		
		List<HistoryData> allData = mHistoryDB.getHistory();
		List<HistoryData> data = CUtil.makeList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		for(HistoryData record : allData){
			String[] date = record.getDate().split("-");
			if(Integer.parseInt(date[0]) == year && Integer.parseInt(date[1]) == month 
					&& Integer.parseInt(date[2]) == day) {
				data.add(record);
			}
			
		}
		
		mAdapter.setList(data);
		mAdapter.notifyDataSetChanged();
	}
	
	
	
	class HistoryListviewAdapter extends BaseAdapter {
		
		private List<HistoryData> mHistory;
		
		public void setList(List<HistoryData> list){
			mHistory = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mHistory.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HistoryData data = mHistory.get(position);
			LayoutInflater inflater = getActivity().getLayoutInflater();
	        View row;
	        row = inflater.inflate(R.layout.history_record, parent, false);
	        TextView dateTextview, displayTextview;

	        dateTextview = (TextView) row.findViewById(R.id.history_date);
	        displayTextview = (TextView) row.findViewById(R.id.history_display);
	        String date = data.getDate() + " " + data.getTime();
	        dateTextview.setText(date);
	        displayTextview.setText(data.getDisplayText());

	        return (row);
			
		}

	}

}
