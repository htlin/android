package org.easycomm;

import org.easycomm.config.ButtonFactory;
import org.easycomm.config.ConfirmBackDialogFragment;
import org.easycomm.config.ConfirmBackDialogFragment.ConfirmBackDialogListener;
import org.easycomm.db.VocabDatabase;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ConfigActivity extends Activity implements ConfirmBackDialogListener {

	private VocabDatabase mVocabDB;
	private ButtonFactory mButtonFactory;
	private boolean mLayoutChanged;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mVocabDB = VocabDatabase.getInstance();
		mButtonFactory = new ButtonFactory(this, mVocabDB);
		
		setContentView(R.layout.activity_vocab_config);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.vocab_config, menu);
		
//		menu.findItem(R.id.action_remove).setEnabled(false);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (mLayoutChanged) {
				showConfirmBackDialog();
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
		default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	public ButtonFactory getButtonFactory() {
		return mButtonFactory;
	}

	private void showConfirmBackDialog() {
		DialogFragment newFragment = new ConfirmBackDialogFragment();
	    newFragment.show(getFragmentManager(), "confirm");
	}
	
	@Override
	public void onBackPressed() {
		if (mLayoutChanged) {
			showConfirmBackDialog();
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		System.out.println("YES!!!");
		finish();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		finish();
	}
	
}
