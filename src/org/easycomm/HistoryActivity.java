package org.easycomm;

import java.util.List;
import java.util.Map;

import org.easycomm.fragment.HistoryListviewFragment;
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
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

public class HistoryActivity extends Activity {
	

	public static final String ARG_HISTORY_OPTION = "historyOption"; 
	private int mHistoryOption;
	private CalendarView mCalendar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mHistoryOption = getIntent().getIntExtra(ARG_HISTORY_OPTION, 3);
		System.err.println("in HistoryActivity mHistoryOption = "+mHistoryOption);
		
		setContentView(R.layout.activity_history);
		
		HistoryListviewFragment historyFrag = (HistoryListviewFragment) getFragmentManager().findFragmentById(R.id.frag_history_listview);
		mCalendar = (CalendarView) findViewById(R.id.calendar_view);
		mCalendar.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				selectDate(year, month, dayOfMonth);
			}
		});
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
		
		HistoryListviewFragment historyFrag = (HistoryListviewFragment) getFragmentManager().findFragmentById(R.id.frag_history_listview);
		historyFrag.clearHistory();
		
		
	}
	
	private void selectDate(int y, int m, int d){
		
		HistoryListviewFragment historyFrag = (HistoryListviewFragment) getFragmentManager().findFragmentById(R.id.frag_history_listview);
		historyFrag.showByDate(y, m, d);
	}
	
	@Override
	public void onBackPressed() {		
			Intent intent = new Intent();
			intent.putExtra(ARG_HISTORY_OPTION, mHistoryOption);
			setResult(Activity.RESULT_OK, intent);
			super.onBackPressed();
	}
	
	
	
}
