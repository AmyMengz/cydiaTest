package org.phoneos.cydiahook.hook;


public class XSharedPreferences {
	/*private static final String TAG = "ReadOnlySharedPreferences";
	private final File mFile;
	private boolean mLoaded = false;
	public XSharedPreferences(String packageName, String prefFileName) {
//		SharedPreferences
		mFile = new File(Environment.getDataDirectory(), "data/" + packageName + "/shared_prefs/" + prefFileName + ".xml");
		startLoadFromDisk();
	}
	
	private void startLoadFromDisk() {
		synchronized (this) {
			mLoaded = false;
		}
		new Thread("XSharedPreferences-load") {
			@Override
			public void run() {
				synchronized (XSharedPreferences.this) {
					loadFromDiskLocked();
				}
			}
		}.start();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadFromDiskLocked() {
		if (mLoaded) {
			return;
		}
		Map map = null;
		long lastModified = 0;
		long fileSize = 0;
		if (mFile.canRead()) {
			lastModified = mFile.lastModified();
			fileSize = mFile.length();
			BufferedInputStream str = null;
			try {
				str = new BufferedInputStream(
						new FileInputStream(mFile), 16*1024);
				map = XmlUtils.readMapXml(str);
				str.close();
			} catch (XmlPullParserException e) {
				Log.w(TAG, "getSharedPreferences", e);
			} catch (IOException e) {
				Log.w(TAG, "getSharedPreferences", e);
			} finally {
				if (str != null) {
					try {
						str.close();
					} catch (RuntimeException rethrown) {
						throw rethrown;
					} catch (Exception ignored) {
					}
				}
			}
		}
		mLoaded = true;
		if (map != null) {
			mMap = map;
			mLastModified = lastModified;
			mFileSize = fileSize;
		} else {
			mMap = new HashMap<String, Object>();
		}
		notifyAll();
	}*/
}
