package org.easycomm;


import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.easycomm.fragment.NavigationFragment;
import org.easycomm.fragment.NavigationFragment.NavigationListener;
import org.easycomm.main.ButtonFactory;
import org.easycomm.main.SentenceFragment;
import org.easycomm.main.SentenceFragment.SentenceActionListener;
import org.easycomm.main.VocabFragment;
import org.easycomm.main.VocabFragment.VocabActionListener;
import org.easycomm.model.HistoryData;
import org.easycomm.model.HistoryDatabase;
import org.easycomm.model.VocabData;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.Vocab;
import org.easycomm.model.visitor.FolderChanger;
import org.easycomm.util.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.text.format.DateFormat;

public class MainActivity extends Activity implements
		TextToSpeech.OnInitListener,
		VocabActionListener,
		SentenceActionListener,
		NavigationListener {

	private TextToSpeech mTTS;
	private VocabDatabase mVocabDB;
	private ButtonFactory mButtonFactory;
	private HistoryDatabase mHistoryBD;
	private int mHistoryOption;
	
//	private static DateFormat mDateFormat;
	
	//Instance states
	private FolderNavigator mFolderNavigator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFolderNavigator = new FolderNavigator();
		mFolderNavigator.load(savedInstanceState);
		
		mTTS = new TextToSpeech(this, this);
		mVocabDB = VocabDatabase.getInstance(getResources().getAssets());
		
		mHistoryBD = HistoryDatabase.getInstance(getResources().getAssets());
		mHistoryOption = 1;
//		mDateFormat = DateFormat.getDateInstance();
		
		FolderChanger.INSTANCE.init(mVocabDB);
		mButtonFactory = new ButtonFactory(this, mVocabDB);
		mButtonFactory.setCurrentFolder(mFolderNavigator.getCurrentFolder());
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mFolderNavigator.save(outState);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) { 
			case (Constant.STATIC_CONFIG_INTEGER_VALUE):
				if (resultCode == Activity.RESULT_OK) { 
					mFolderNavigator.load(data);
					updateFolderPath();
					SentenceFragment sentence = (SentenceFragment) getFragmentManager().findFragmentById(R.id.frag_sentence);
					sentence.invalidate();
				} 
				break;
			case (Constant.STATIC_HISTORY_INTEGER_VALUE):
				System.err.println("in main onActivityResult STATIC_HISTORY_INTEGER_VALUE");
				if (resultCode == Activity.RESULT_OK) { 
					mHistoryOption = data.getIntExtra(HistoryActivity.ARG_HISTORY_OPTION, 3);
					System.err.println("in main onActivityResult "+mHistoryOption);

				} 
				break;
			
		default:
		} 
	}
	
	@Override
	protected void onResume(){
		super.onResume();
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
		case R.id.action_history:
            startHistory();
            return true;
        case R.id.action_config:
            startConfig();
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	private void startConfig() {
		Intent intent = new Intent(this, ConfigActivity.class);
		mFolderNavigator.save(intent);
		startActivityForResult(intent, Constant.STATIC_CONFIG_INTEGER_VALUE);
	}
	
	private void startHistory() {

		Intent intent = new Intent(this, HistoryActivity.class);
		intent.putExtra(HistoryActivity.ARG_HISTORY_OPTION, mHistoryOption);
		startActivityForResult(intent, Constant.STATIC_HISTORY_INTEGER_VALUE);
		//startActivity(intent);

	}

	public VocabDatabase getVocabDatabase() {
		return mVocabDB;
	}
	
	public HistoryDatabase getHistoryDatabase() {
		return mHistoryBD;
	}
	
	public ButtonFactory getButtonFactory() {
		return mButtonFactory;
	}
	
	private void speak(String text) {
		mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date()).toString();
		//String date = DateFormat.getDateInstance().format("yyyy-MM-dd hh:mm:ss", new Date());
		HistoryData data = new HistoryData(date, text);
		mHistoryBD.append(data);
		
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
		
		vocab.accept(FolderChanger.INSTANCE);
		String newID = FolderChanger.INSTANCE.getResult();
		if (newID != null) {
			mFolderNavigator.moveDown(newID);
			updateFolderPath();
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
	
	@Override
	public void onNavigationBarClick() {
		SentenceFragment sentence = (SentenceFragment) getFragmentManager().findFragmentById(R.id.frag_sentence);
		List<String> ids = sentence.getSentence();
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
		if (mFolderNavigator.reset()) {
			updateFolderPath();
		}
	}
	
	@Override
	public void onBackButtonClick() {
		if (mFolderNavigator.moveUp()) {
			updateFolderPath();
		}
	}
	
	@Override
	public void onOpenButtonClick() {		
	}
	
	private void updateFolderPath() {
		List<String> folderTexts = mFolderNavigator.getTexts(mVocabDB);
		
		NavigationFragment navFrag = (NavigationFragment) getFragmentManager().findFragmentById(R.id.frag_navigation);
		navFrag.setCurrentPath(folderTexts);
		
		mButtonFactory.setCurrentFolder(mFolderNavigator.getCurrentFolder());
		
		VocabFragment vocabFrag = (VocabFragment) getFragmentManager().findFragmentById(R.id.frag_vocab);
		vocabFrag.invalidate();
	}
	
}
