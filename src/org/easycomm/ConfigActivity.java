package org.easycomm;

import org.easycomm.config.AddModifyVocabDialogFragment;
import org.easycomm.config.AddModifyVocabDialogFragment.AddVocabDialogListener;
import org.easycomm.config.ButtonFactory;
import org.easycomm.config.ConfirmBackDialogFragment;
import org.easycomm.config.ConfirmBackDialogFragment.ConfirmBackDialogListener;
import org.easycomm.config.ViewSelector;
import org.easycomm.config.VocabFragment;
import org.easycomm.config.VocabFragment.VocabActionListener;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.VocabReader;
import org.easycomm.model.graph.Vocab;
import org.easycomm.util.Constant;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ConfigActivity extends Activity implements ConfirmBackDialogListener, AddVocabDialogListener, VocabActionListener {

	private VocabReader mVocabReader;
	private VocabDatabase mVocabDB;
	private ButtonFactory mButtonFactory;
	private ViewSelector mSelector;
	private boolean mLayoutChanged;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mVocabReader = VocabReader.getInstance(getResources().getAssets());
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
		boolean isSelected = mSelector.isSelected();
        menu.findItem(R.id.action_modify).setEnabled(isSelected);
        menu.findItem(R.id.action_remove).setEnabled(isSelected);
        menu.findItem(R.id.action_save).setEnabled(mLayoutChanged);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String selectedID;
		
		switch (item.getItemId()) {
		case android.R.id.home:
			if (mLayoutChanged) {
				showConfirmBackDialog();
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
			
		case R.id.action_new:
			showAddModifyDialog(null);
			return true;
			
		case R.id.action_add_vocab:
			showAddDialog(1);
			return true;
			
		case R.id.action_add_folder:
			showAddDialog(2);
			return true;
			
		case R.id.action_add_link:
			showAddDialog(3);
			return true;
			
		case R.id.action_modify:
			selectedID = mSelector.getSelectedID();
			showAddModifyDialog(selectedID);
			return true;
			
		case R.id.action_remove:
			selectedID = mSelector.getSelectedID();
//			mVocabDB.getTree().remove(selectedID);
			mSelector.deselect();
			updateLayout();
			return true;
			
		case R.id.action_save:
			mVocabDB.save();
			mLayoutChanged = false;
			invalidateOptionsMenu();
			return true;
			
		default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	private void showAddDialog(int i) {
		// TODO Auto-generated method stub
		// to be implemented ...
		
	}

	private void updateLayout() {
		VocabFragment vocab = (VocabFragment) getFragmentManager().findFragmentById(R.id.frag_config_vocab);
		vocab.invalidate();
		mLayoutChanged = true;
		invalidateOptionsMenu();
	}

	public ButtonFactory getButtonFactory() {
		return mButtonFactory;
	}

	private void showConfirmBackDialog() {
		DialogFragment newFragment = new ConfirmBackDialogFragment();
	    newFragment.show(getFragmentManager(), "confirm");
	}

	private void showAddModifyDialog(String selectedID) {
		DialogFragment newFragment = new AddModifyVocabDialogFragment();
		Bundle args = new Bundle();
		if (selectedID != null) {
			args.putString(AddModifyVocabDialogFragment.ARG_VOCAB_ID, selectedID);
		}
		newFragment.setArguments(args);
	    newFragment.show(getFragmentManager(), "add");
	}

	@Override
	public void onBackPressed() {
		if (mLayoutChanged) {
			showConfirmBackDialog();
		} else {
			setResult(Activity.RESULT_OK);
			super.onBackPressed();
		}
	}
	
	@Override
	public void onConfirmBackDialogPositiveClick(DialogFragment dialog) {
		mVocabDB.save();
		setResult(Activity.RESULT_OK);
		finish();
	}

	@Override
	public void onConfirmBackDialogNegativeClick(DialogFragment dialog) {
		mVocabDB.revert();
		setResult(Activity.RESULT_OK);
		finish();
	}

	@Override
	public void onAddVocabDialogPositiveClick(AddModifyVocabDialogFragment dialog) {
		String displayText = dialog.getDisplayText();
		String speechText = dialog.getSpeechText();
		String vocabID = dialog.getVocabID();
		
		String selectedID = mSelector.getSelectedID();
		if (selectedID == null) {
			//Add
			//TODO
//			Vocab vocab = mVocabReader.getVocab(vocabID).copy();
//			vocab.setDisplayText(displayText);
//			vocab.setSpeechText(speechText);
//			mVocabDB.getTree().add(vocab);
			updateLayout();
		} else {
			//Modify
			
		}
	}

	@Override
	public void onAddVocabDialogNegativeClick(AddModifyVocabDialogFragment dialog) {
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
		if (!sourceID.equals(targetID)) {
//			mVocabDB.getTree().move(sourceID, targetID);
			updateLayout();
		}
		
		mSelector.deselect();
		invalidateOptionsMenu();
	}

}
