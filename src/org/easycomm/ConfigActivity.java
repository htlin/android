package org.easycomm;

import java.util.ArrayList;
import java.util.List;

import org.easycomm.config.AddModifyVocabDialogFragment;
import org.easycomm.config.AddModifyVocabDialogFragment.AddVocabDialogListener;
import org.easycomm.config.ButtonFactory;
import org.easycomm.config.ConfirmBackDialogFragment;
import org.easycomm.config.ConfirmBackDialogFragment.ConfirmBackDialogListener;
import org.easycomm.config.ViewSelector;
import org.easycomm.config.VocabFragment;
import org.easycomm.config.VocabFragment.VocabActionListener;
import org.easycomm.fragment.NavigationFragment;
import org.easycomm.fragment.NavigationFragment.NavigationListener;
import org.easycomm.model.VocabData;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.VocabReader;
import org.easycomm.model.graph.Folder;
import org.easycomm.model.graph.Leaf;
import org.easycomm.model.graph.Link;
import org.easycomm.model.graph.Vocab;
import org.easycomm.model.graph.VocabGraph;
import org.easycomm.model.visitor.FolderChanger;
import org.easycomm.model.visitor.VocabVisitor;
import org.easycomm.model.visitor.VocabVisitorAdapter;
import org.easycomm.util.CUtil;
import org.easycomm.util.Constant;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ConfigActivity extends Activity implements
		ConfirmBackDialogListener,
		AddVocabDialogListener,
		VocabActionListener ,
		NavigationListener {

	private VocabReader mVocabReader;
	private VocabDatabase mVocabDB;
	private ButtonFactory mButtonFactory;
	private ViewSelector mSelector;
	
	//Instance states
	private boolean mLayoutChanged;
	private ArrayList<String> mFolderPathIDs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		if (savedInstanceState != null) {
			mLayoutChanged = savedInstanceState.getBoolean(Constant.LAYOUT_CHANGED);
			mFolderPathIDs = savedInstanceState.getStringArrayList(Constant.FOLDER_PATH);
		} else {
			mFolderPathIDs = getIntent().getStringArrayListExtra(Constant.FOLDER_PATH);
		}
		
		mVocabReader = VocabReader.getInstance(getResources().getAssets());
		mVocabDB = VocabDatabase.getInstance(getResources().getAssets());
		mButtonFactory = new ButtonFactory(this, mVocabDB);
		mButtonFactory.setCurrentFolder(getCurrentFolder());
		mSelector = new ViewSelector();
		
		setContentView(R.layout.activity_vocab_config);
		
		NavigationFragment navFrag = (NavigationFragment) getFragmentManager().findFragmentById(R.id.frag_config_navigation);
		navFrag.setOpenButtonVisibility(true);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    updateFolderPath();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(Constant.LAYOUT_CHANGED, mLayoutChanged);
		outState.putStringArrayList(Constant.FOLDER_PATH, mFolderPathIDs);
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
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
			
		case R.id.action_add_vocab:
			showAddModifyDialog(VocabDatabase.PROTOTYPE_LEAF_ID, null);
			return true;
			
		case R.id.action_add_folder:
			showAddModifyDialog(VocabDatabase.PROTOTYPE_FOLDER_ID, null);
			return true;
			
		case R.id.action_add_link:
			showAddModifyDialog(VocabDatabase.PROTOTYPE_LINK_ID, null);
			return true;
			
		case R.id.action_modify:
			String selectedID = mSelector.getSelectedID();
			String followupFolderID = mSelector.getFollowupFolderID();
			showAddModifyDialog(selectedID, followupFolderID);
			return true;
			
		case R.id.action_remove:
			removeVocab();
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
	
	private void openFolder() {
		String folderID = mSelector.getFollowupFolderID();
		mFolderPathIDs.add(folderID);
		updateFolderPath();
		
		mSelector.deselect();
		VocabFragment vocabFragment = (VocabFragment) getFragmentManager().findFragmentById(R.id.frag_config_vocab);
		vocabFragment.invalidate();
		invalidateOptionsMenu();		
	}
	
	private void removeVocab() {		
		// check whether the selected item is a folder or not 
		Vocab vocab = mVocabDB.getVocab(mSelector.getSelectedID());
		if (vocab instanceof Folder) {
			// folder selected
			String folderID = mSelector.getFollowupFolderID();
			removeFolder( folderID );
		
		}
		else {
			// leaf or link selected
			String selectedID = mSelector.getSelectedID();
			mVocabDB.getGraph().remove(selectedID);
		}
		
		mSelector.deselect();
		updateLayout();
	}
	
	private void removeFolder(String folderID) {		
		List<Vocab> children = mVocabDB.getGraph().getChildren(folderID);
		for( Vocab v : children){
			if(v instanceof Folder){
				removeFolder(v.getID());
				mVocabDB.getGraph().remove(v.getID());
			}
			else {
				mVocabDB.getGraph().remove(v.getID());
			}
		}
		
		List<Link> sourceLlinks = mVocabDB.getGraph().getSourceLinks(folderID);
		for( Link l : sourceLlinks){
			mVocabDB.getGraph().remove(l.getID());
		}
		mVocabDB.getGraph().remove(folderID);
		
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
	
	public String getCurrentFolder(){
		int count = mFolderPathIDs.size();
		if (count == 0) return null;
		else return mFolderPathIDs.get(count - 1);		
	}

	private void showConfirmBackDialog() {
		DialogFragment newFragment = new ConfirmBackDialogFragment();
	    newFragment.show(getFragmentManager(), "confirm");
	}
		
	private void showAddModifyDialog(String selectedID, String followupFolderID) {
		DialogFragment newFragment = new AddModifyVocabDialogFragment();
		Bundle args = new Bundle();
		args.putString(AddModifyVocabDialogFragment.ARG_VOCAB_ID, selectedID);
		args.putString(AddModifyVocabDialogFragment.ARG_FOLDER_ID, followupFolderID);		
		newFragment.setArguments(args);
	    newFragment.show(getFragmentManager(), "add/modify");
	}

	@Override
	public void onBackPressed() {
		if (mLayoutChanged) {
			showConfirmBackDialog();
		} else {
			Intent intent = new Intent();
			intent.putStringArrayListExtra(Constant.FOLDER_PATH, mFolderPathIDs);

			setResult(Activity.RESULT_OK, intent);
			super.onBackPressed();
		}
	}
	
	@Override
	public void onConfirmBackDialogPositiveClick(DialogFragment dialog) {
		mVocabDB.save();
		Intent intent = new Intent();
		intent.putStringArrayListExtra(Constant.FOLDER_PATH, mFolderPathIDs);

		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	@Override
	public void onConfirmBackDialogNegativeClick(DialogFragment dialog) {
		mVocabDB.revert();
		Intent intent = new Intent();
		intent.putStringArrayListExtra(Constant.FOLDER_PATH, mFolderPathIDs);

		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	@Override
	public void onAddVocabDialogPositiveClick(final String id, final VocabData data, final String followupFolderID) {
		Vocab selectedVocab = mVocabDB.getVocab(id);
		if (mVocabDB.isPrototype(id)) {
			selectedVocab.accept(new VocabVisitor() {
				@Override
				public void visit(Leaf v) {
					Leaf leaf = mVocabDB.getGraph().makeLeaf(data);
					mVocabDB.getGraph().addChild(getCurrentFolder(), leaf.getID());
				}

				@Override
				public void visit(Folder v) {
					Folder folder = mVocabDB.getGraph().makeFolder(data);
					mVocabDB.getGraph().addChild(getCurrentFolder(), folder.getID());
				}

				@Override
				public void visit(Link v) {
					Link link = mVocabDB.getGraph().makeLink(data);
					mVocabDB.getGraph().addChild(getCurrentFolder(), link.getID());
					mVocabDB.getGraph().addChild(link.getID(), followupFolderID);
				}				
			});
		} else {
			VocabData vocab = selectedVocab.getData();
			vocab.setDisplayText(data.getDisplayText());
			vocab.setSpeechText(data.getSpeechText());
			vocab.setFilename(data.getFilename());
			vocab.setImage(data.getImage());
			
			selectedVocab.accept(new VocabVisitorAdapter() {
				@Override
				public void visit(Link v) {
					mVocabDB.getGraph().removeChild(id, mSelector.getFollowupFolderID());
					mVocabDB.getGraph().addChild(id, followupFolderID);
				}				
			});
		}
		
		updateLayout();
		mSelector.deselect();
	}
	
	@Override
	public void onVocabButtonClick(String id, View v) {
		boolean changed = mSelector.select(id, v);
		String selectedID = mSelector.getSelectedID();
		Vocab vocab = mVocabDB.getVocab(selectedID);
		vocab.accept(FolderChanger.INSTANCE);
		String newID = FolderChanger.INSTANCE.getResult();
		mSelector.setFollowupFolderID(newID);
		
		NavigationFragment navFrag = (NavigationFragment) getFragmentManager().findFragmentById(R.id.frag_config_navigation);
		navFrag.setOpenButtonState(mSelector.hasFollowupFolder());
		
		if (changed) {
			invalidateOptionsMenu();
		}
	}

	@Override
	public void onVocabDragDrop(String sourceID, String targetID) {
		if (!sourceID.equals(targetID)) {
			String lastFolderID = mFolderPathIDs.get(mFolderPathIDs.size() - 1);
			mVocabDB.getGraph().move(lastFolderID, sourceID, targetID);
			updateLayout();
		}
		
		mSelector.deselect();
		invalidateOptionsMenu();
	}
	
	
	@Override
	public void onHomeButtonClick() {
		if (mFolderPathIDs.size() > 1) {
			mFolderPathIDs.clear();
			mFolderPathIDs.add(VocabGraph.ROOT_ID);
			updateFolderPath();
		}
	}
	
	@Override
	public void onBackButtonClick() {
		if (mFolderPathIDs.size() > 1) {
			mFolderPathIDs.remove(mFolderPathIDs.size() - 1);
			updateFolderPath();
		}
	}
	
	@Override
	public void onOpenButtonClick() {
		openFolder();
	}
	
	private void updateFolderPath() {
		List<String> folderTexts = CUtil.makeList();
		for (String id : mFolderPathIDs) {
			String text = mVocabDB.getVocabData(id).getDisplayText();
			folderTexts.add(text);
		}
		
		NavigationFragment navFrag = (NavigationFragment) getFragmentManager().findFragmentById(R.id.frag_config_navigation);
		navFrag.setCurrentPath(folderTexts);
		
		String currentFolder = mFolderPathIDs.get(mFolderPathIDs.size() - 1);
		mButtonFactory.setCurrentFolder(currentFolder);
		
		VocabFragment vocabFrag = (VocabFragment) getFragmentManager().findFragmentById(R.id.frag_config_vocab);
		vocabFrag.invalidate();
	}

}
