package org.easycomm;

import java.util.List;
import java.util.Map;

import org.easycomm.model.HistoryData;
import org.easycomm.model.HistoryDatabase;
import org.easycomm.util.CUtil;
import org.easycomm.util.Constant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

public class HistoryActivity extends Activity {
	

	public static final String ARG_HISTORY_OPTION = "historyOption"; 
	private HistoryDatabase mHistoryDB;
	private int mHistoryOption;
	private HistoryListviewAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mHistoryOption = getIntent().getIntExtra(ARG_HISTORY_OPTION, 3);
		System.err.println("in HistoryActivity mHistoryOption = "+mHistoryOption);
		mHistoryDB = HistoryDatabase.getInstance(getResources().getAssets());
		
		setContentView(R.layout.activity_history);
		
		//Attach adapters
		ListView listView = (ListView) findViewById(R.id.history_listview);
		View header=getLayoutInflater().inflate(R.layout.history_listview_header, null);
		listView.addHeaderView(header);
		mAdapter = new HistoryListviewAdapter();
		mAdapter.setList(mHistoryDB.getHistory());
		listView.setAdapter(mAdapter);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem menuNever = menu.findItem(R.id.never); 
		MenuItem menu7Days = menu.findItem(R.id.sevenDays); 
		MenuItem menuForever = menu.findItem(R.id.forever); 
		
		switch(mHistoryOption){
			case 1:
				menuNever.setChecked(true);menu7Days.setChecked(false);menuForever.setChecked(false);
				break;
			case 2:
				menuNever.setChecked(false);menu7Days.setChecked(true);menuForever.setChecked(false);
				break;
			case 3:
				menuNever.setChecked(false);menu7Days.setChecked(false);menuForever.setChecked(true);
				break;
			
		}     
	    return true;
	}
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
			
		case R.id.action_clear:
			clearHistory();
			return true;
			
		case R.id.never:
			mHistoryOption = 1;
			invalidateOptionsMenu();
			return true;
			
		case R.id.sevenDays:
			mHistoryOption = 2;
			invalidateOptionsMenu();
			return true;
			
		case R.id.forever:
			mHistoryOption = 3;
			invalidateOptionsMenu();
			return true;
			
			
		default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	private void clearHistory(){
		System.err.println("clearHistory");
		mHistoryDB.clear();		
		mAdapter.setList(mHistoryDB.getHistory());
		mAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onBackPressed() {		
			Intent intent = new Intent();
			intent.putExtra(ARG_HISTORY_OPTION, mHistoryOption);
			setResult(Activity.RESULT_OK, intent);
			super.onBackPressed();
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
			LayoutInflater inflater = getLayoutInflater();
	        View row;
	        row = inflater.inflate(R.layout.history_listview_layout, parent, false);
	        TextView dateTextview, displayTextview;

	        dateTextview = (TextView) row.findViewById(R.id.history_date);
	        displayTextview = (TextView) row.findViewById(R.id.history_display);
	        dateTextview.setText(data.getDate());
	        displayTextview.setText(data.getDisplayText());

	        return (row);
			
		}

	}
	
}
