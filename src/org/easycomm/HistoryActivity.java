package org.easycomm;

import java.util.List;
import java.util.Map;

import org.easycomm.model.HistoryData;
import org.easycomm.model.HistoryDatabase;
import org.easycomm.util.CUtil;
import org.easycomm.util.Constant;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class HistoryActivity extends Activity {
	

	private HistoryDatabase mHistoryDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mHistoryDB = HistoryDatabase.getInstance(getResources().getAssets());
		
		setContentView(R.layout.activity_history);
		
		//Attach adapters
		ListView listView = (ListView) findViewById(R.id.history_listview);
		
		SimpleAdapter adapter = getAdapter();
		listView.setAdapter(adapter);
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
	
	private SimpleAdapter getAdapter() {

		List<HistoryData> historyData = mHistoryDB.getHistory();
		List<Map<String, Object>> list = CUtil.makeList();
		for (HistoryData data : historyData) {
			Map<String, Object> map = CUtil.makeMap();
			map.put("date", data.getDate());
			map.put("text", data.getDisplayText());
			list.add(map);
		}	
				
		String[] from = { "date", "text" };
		int[] to = { R.id.history_date, R.id.history_display};

		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.history_listview_layout, from, to);

		return adapter;
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
			
		case R.id.filter_1:
			
			filter_1();
			return true;
			
		case R.id.filter_2:
			filter_2();
			return true;
			
		case R.id.filter_3:
			filter_3();
			return true;
			
			
		default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	private void clearHistory(){
		mHistoryDB.clear();
		ListView listView = (ListView) findViewById(R.id.history_listview);
		listView.invalidate();
	}
	
	private void filter_1(){
		System.err.println("filter_1");

	}
	
	private void filter_2(){
		System.err.println("filter_2");
		
	}
	
	private void filter_3(){
		System.err.println("filter_3");
		
	}

}
