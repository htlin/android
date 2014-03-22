package org.easycomm;

import org.easycomm.config.ButtonFactory;
import org.easycomm.config.ConfirmBackDialogFragment;
import org.easycomm.config.ConfirmBackDialogFragment.ConfirmBackDialogListener;
import org.easycomm.config.ViewSelector;
import org.easycomm.config.VocabFragment;
import org.easycomm.config.VocabFragment.VocabActionListener;
import org.easycomm.db.VocabDatabase;
import org.easycomm.util.Constant;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ConfigActivity extends Activity implements ConfirmBackDialogListener, VocabActionListener {

	private VocabDatabase mVocabDB;
	private ButtonFactory mButtonFactory;
	private ViewSelector mSelector;
	private boolean mLayoutChanged;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mVocabDB = VocabDatabase.getInstance(getResources().getAssets());
		mButtonFactory = new ButtonFactory(this, mVocabDB);
		mSelector = new ViewSelector(getResources());
		
		if (savedInstanceState != null) {
			mLayoutChanged = savedInstanceState.getBoolean(Constant.LAYOUT_CHANGED);
		}
		
		setContentView(R.layout.activity_vocab_config);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(Constant.LAYOUT_CHANGED, mLayoutChanged);
		
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.vocab_config, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean isEnabled = mSelector.isSelected();
        menu.findItem(R.id.action_modify).setEnabled(isEnabled);
        menu.findItem(R.id.action_remove).setEnabled(isEnabled);
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
			
		case R.id.action_new:
			return true;
			
		case R.id.action_modify:
			return true;
			
		case R.id.action_remove:
			String selectedID = mSelector.getSelectedID();
			mVocabDB.remove(selectedID);
			updateLayout();
			mSelector.deselect();
			invalidateOptionsMenu();
			return true;
			
		case R.id.action_save:
			mVocabDB.save();
			mLayoutChanged = false;
			return true;
			
		default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	private void updateLayout() {
		VocabFragment vocab = (VocabFragment) getFragmentManager().findFragmentById(R.id.frag_config_vocab);
		vocab.invalidate();
		mLayoutChanged = true;
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
		mVocabDB.save();
		setResult(Activity.RESULT_OK);
		finish();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		mVocabDB.revert();
		setResult(Activity.RESULT_OK);
		finish();
	}

	
	@Override
	public void onVocabButtonClick(String id, View v) {
		boolean changed = mSelector.select(id, v);
		if (changed) {
			invalidateOptionsMenu();
		}
	}

	@Override
	public void onVocabDragDrop(String sourceID, String targetID) {
		
	}
	
}
