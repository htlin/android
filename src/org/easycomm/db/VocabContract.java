package org.easycomm.db;

import android.provider.BaseColumns;

public final class VocabContract {

	public VocabContract() {}
	
	public static abstract class VocabEntry implements BaseColumns {
        public static final String TABLE_NAME = "vocab";
        public static final String COLUMN_NAME_IS_DIR = "is_dir";
        public static final String COLUMN_NAME_PARENT_ID = "parent_id";
        public static final String COLUMN_NAME_DISPLAY_TEXT = "display_text";
        public static final String COLUMN_NAME_SPEECH_TEXT = "speech_text";
        public static final String COLUMN_NAME_IMAGE_FILE = "image_file";
        public static final String COLUMN_NAME_POS = "pos";
	}
	
}
