package org.easycomm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.easycomm.main.ButtonFactory;
import org.easycomm.main.NavigationFragment;
import org.easycomm.main.NavigationFragment.NavigationListener;
import org.easycomm.main.SentenceFragment;
import org.easycomm.main.SentenceFragment.SentenceActionListener;
import org.easycomm.main.VocabFragment;
import org.easycomm.main.VocabFragment.VocabActionListener;
import org.easycomm.model.VocabData;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.Vocab;
import org.easycomm.model.graph.VocabGraph;
import org.easycomm.model.visitor.FolderChanger;
import org.easycomm.util.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements
		TextToSpeech.OnInitListener,
		VocabActionListener,
		SentenceActionListener,
		NavigationListener {

	private TextToSpeech mTTS;
	private VocabDatabase mVocabDB;
	private ArrayList<String> mFolderPath;
	private ButtonFactory mButtonFactory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			mFolderPath = new ArrayList<String>();
		} else {
			mFolderPath = savedInstanceState.getStringArrayList(Constant.FOLDER_PATH);
		}
		
		if (mFolderPath.isEmpty()) {
			mFolderPath.add(VocabGraph.ROOT_ID);
		}
		
		mTTS = new TextToSpeech(this, this);
		mVocabDB = VocabDatabase.getInstance(getResources().getAssets());
		mButtonFactory = new ButtonFactory(this, mVocabDB);
		mButtonFactory.setCurrentFolder(getCurrentFolder());
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList(Constant.FOLDER_PATH, mFolderPath);
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) { 
		case (Constant.STATIC_INTEGER_VALUE):
			if (resultCode == Activity.RESULT_OK) { 
				VocabFragment vocab = (VocabFragment) getFragmentManager().findFragmentById(R.id.frag_vocab);
				vocab.invalidate();
				
				SentenceFragment sentence = (SentenceFragment) getFragmentManager().findFragmentById(R.id.frag_sentence);
				sentence.invalidate();
			} 
			break;
			
		default:
		} 
	}
	
	@Override
	protected void onDestroy() {
		if (mTTS != null) {
			mTTS.stop();
			mTTS.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {			 
			int result = mTTS.setLanguage(Locale.US);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} 
		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case R.id.action_config:
            startConfig();
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	private void startConfig() {
		Intent intent = new Intent(this, ConfigActivity.class);
		startActivityForResult(intent, Constant.STATIC_INTEGER_VALUE);
	}

	public VocabDatabase getVocabDatabase() {
		return mVocabDB;
	}
	
	public ButtonFactory getButtonFactory() {
		return mButtonFactory;
	}
	
	public String getCurrentFolder() {
		return mFolderPath.get(mFolderPath.size() - 1);
	}

	private void speak(String text) {
		mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onVocabButtonClick(String id) {
		Vocab vocab = mVocabDB.getVocab(id);
		VocabData vocabData = vocab.getData();
		if (vocabData.hasSpeechText()) {
			speak(vocabData.getSpeechText());
			SentenceFragment sentenseFrag = (SentenceFragment) getFragmentManager().findFragmentById(R.id.frag_sentence);
			sentenseFrag.addButton(id);
		}
		
		FolderChanger.INSTANCE.init(mVocabDB);
		vocab.accept(FolderChanger.INSTANCE);
		String newID = FolderChanger.INSTANCE.getResult();
		if (newID != null) {
			mFolderPath.add(newID);
			mButtonFactory.setCurrentFolder(newID);
			
			String newFolderText = mVocabDB.getVocabData(newID).getDisplayText();
			NavigationFragment navFrag =  (NavigationFragment) getFragmentManager().findFragmentById(R.id.frag_navigation);
			navFrag.setCurrentFolder(newFolderText);
			
			VocabFragment vocabFrag = (VocabFragment) getFragmentManager().findFragmentById(R.id.frag_vocab);
			vocabFrag.invalidate();
		}
	}

	@Override
	public void onSentenceButtonClick(String id) {
		speak(mVocabDB.getVocabData(id).getSpeechText());
	}

	@Override
	public void onSentenceBarClick(List<String> ids) {
		speak(getSentense(ids));
	}

	private String getSentense(List<String> ids) {
		StringBuffer buf = new StringBuffer();
		for (String id : ids) {
			buf.append(mVocabDB.getVocabData(id).getSpeechText());
			buf.append(" ");
		}
		return buf.toString();
	}
	
	@Override
	public void onHomeButtonClick() {
		//TODO  To be implemented later
		NavigationFragment navFrag =  (NavigationFragment) getFragmentManager().findFragmentById(R.id.frag_navigation);
		navFrag.setCurrentFolder("Home pressed");
	}
	
	@Override
	public void onBackButtonClick() {
		//TODO  To be implemented later
		NavigationFragment navFrag = (NavigationFragment) getFragmentManager().findFragmentById(R.id.frag_navigation);
		navFrag.setCurrentFolder("Back pressed");
	}
	
}
