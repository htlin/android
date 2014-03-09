package org.easycomm;

import org.easycomm.main.ButtonFactory;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ConfigActivity extends Activity {

	private ButtonFactory mButtonFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mButtonFactory = new ButtonFactory(this);
		
		setContentView(R.layout.activity_vocab_config);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vocab_config, menu);
		return true;
	}
	
	
	public ButtonFactory getButtonFactory() {
		return mButtonFactory;
	}
	
}
