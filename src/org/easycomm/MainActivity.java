package org.easycomm;

import java.util.Locale;

import org.easycomm.sentense.SentenseFragment;
import org.easycomm.sentense.SentenseFragment.SentenseActionListener;
import org.easycomm.vocab.VocabFragment.VocabActionListener;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener, VocabActionListener, SentenseActionListener {

	private TextToSpeech mTTS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTTS = new TextToSpeech(this, this);
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

	private void speak(String text) {
		mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onVocabButtonClick(String text) {
		speak(text);
		SentenseFragment sentenseFrag = (SentenseFragment) getFragmentManager().findFragmentById(R.id.frag_sentense);
		sentenseFrag.addButton(text);
	}

	@Override
	public void onSentenseButtonClick(String text) {
		speak(text);
	}

	@Override
	public void onSentenseBarClick(String text) {
		speak(text);
	}

}
