package org.easycomm.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.easycomm.R;
import org.easycomm.util.CUtil;
import org.easycomm.util.FileUtil;
import org.easycomm.util.IndexedList;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class ButtonFactory {

	private static final String ASSET_DIR = "vocab";
	
	private Context mContext;
	private int mSideSize;
	
	private List<String> mButtonTexts;
	private List<Drawable> mButtonImages;
	private IndexedList<String> mButtonKeys;	//indexed list of Button keys, those indexes correspond to the indexes in mButtonTexts and mButtonImages
	
	public ButtonFactory(Context c) {
		mContext = c;
		mSideSize = c.getResources().getDimensionPixelSize(R.dimen.button_side);
		load(c.getResources().getAssets());
	}

	private void load(AssetManager assets) {
		mButtonTexts = CUtil.makeList();
		mButtonImages = CUtil.makeList();
		List<String> keys = CUtil.makeList();
		try {
			for (String file : assets.list(ASSET_DIR)) {
				InputStream in = assets.open(ASSET_DIR + "/" + file);
				Drawable d = Drawable.createFromStream(in , null);
				String displayName = FileUtil.getName(file);
				String key = displayName;
				mButtonTexts.add(displayName);
				mButtonImages.add(d);
				keys.add(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mButtonKeys = new IndexedList<String>(keys);
	}

	public int size() {
		return mButtonTexts.size();
	}
	
	public String getText(String key) {
		int i = mButtonKeys.getIndex(key);
		return mButtonTexts.get(i);
	}
	
	public Button get(String key, OnClickListener onClickListener) {
		int i = mButtonKeys.getIndex(key);
		return get(i, onClickListener);
	}

	public Button get(int pos, OnClickListener onClickListener) {
		Button button = new Button(mContext);
		button.setLayoutParams(new GridView.LayoutParams(mSideSize, mSideSize));
    	button.setText(mButtonTexts.get(pos));
        button.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM);
        button.setCompoundDrawablesWithIntrinsicBounds(null, mButtonImages.get(pos), null, null);
        button.setOnClickListener(onClickListener);
        button.setTag(mButtonKeys.get(pos));
        button.setId(pos);
		return button;
	}

}
