package org.easycomm;

import java.util.List;
import java.util.Locale;

import org.easycomm.main.NavigationFragment;
import org.easycomm.main.NavigationFragment.NavigationListener;
import org.easycomm.main.ButtonFactory;
import org.easycomm.main.SentenceFragment;
import org.easycomm.main.SentenceFragment.SentenceActionListener;
import org.easycomm.main.VocabFragment;
import org.easycomm.main.VocabFragment.VocabActionListener;
import org.easycomm.model.VocabDatabase;
import org.easycomm.util.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener, VocabActionListener,
	SentenceActionListener, NavigationListener {

	private TextToSpeech mTTS;
	private VocabDatabase mVocabDB;
	private ButtonFactory mButtonFactory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTTS = new TextToSpeech(this, this);
		mVocabDB = VocabDatabase.getInstance(getResources().getAssets());
		mButtonFactory = new ButtonFactory(this, mVocabDB);
		
		setContentView(R.layout.activity_main);
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
	
	

	private void speak(String text) {
		mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onVocabButtonClick(String id) {
		speak(mVocabDB.get().getVocab(id).getSpeechText());
		SentenceFragment sentenseFrag = (SentenceFragment) getFragmentManager().findFragmentById(R.id.frag_sentence);
		sentenseFrag.addButton(id);
	}

	@Override
	public void onSentenceButtonClick(String id) {
		speak(mVocabDB.get().getVocab(id).getSpeechText());
	}

	@Override
	public void onSentenceBarClick(List<String> ids) {
		speak(getSentense(ids));
	}

	private String getSentense(List<String> ids) {
		StringBuffer buf = new StringBuffer();
		for (String id : ids) {
			buf.append(mVocabDB.get().getVocab(id).getSpeechText());
			buf.append(" ");
		}
		return buf.toString();
	}
	
	@Override
	public void onHomeButtonClick() {
		//  To be implemented later
		NavigationFragment navigationFrag =  (NavigationFragment) getFragmentManager().findFragmentById(R.id.frag_navigation);

		navigationFrag.displayCurrentFolder("Home pressed");
		
	}
	
	@Override
	public void onBackButtonClick() {
		//  To be implemented later
		
	}
	
}
