package org.easycomm;

import java.util.List;
import java.util.Locale;

import org.easycomm.sentense.SentenseFragment;
import org.easycomm.sentense.SentenseFragment.SentenseActionListener;
import org.easycomm.vocab.ButtonFactory;
import org.easycomm.vocab.VocabFragment.VocabActionListener;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener, VocabActionListener, SentenseActionListener {

	private TextToSpeech mTTS;
	private ButtonFactory mButtonFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mTTS = new TextToSpeech(this, this);
		mButtonFactory = new ButtonFactory(this);
		
		setContentView(R.layout.activity_main);
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
	
	
	public ButtonFactory getButtonFactory() {
		return mButtonFactory;
	}
	

	private void speak(String text) {
		mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onVocabButtonClick(String key) {
		speak(mButtonFactory.getText(key));
		SentenseFragment sentenseFrag = (SentenseFragment) getFragmentManager().findFragmentById(R.id.frag_sentense);
		sentenseFrag.addButton(key);
	}

	@Override
	public void onSentenseButtonClick(String key) {
		speak(mButtonFactory.getText(key));
	}

	@Override
	public void onSentenseBarClick(List<String> keys) {
		speak(getSentense(keys));
	}

	private String getSentense(List<String> keys) {
		StringBuffer buf = new StringBuffer();
		for (String key : keys) {
			buf.append(mButtonFactory.getText(key));
			buf.append(" ");
		}
		return buf.toString();
	}
	
}
