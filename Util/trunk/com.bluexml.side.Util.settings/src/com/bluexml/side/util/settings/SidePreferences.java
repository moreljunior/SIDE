package com.bluexml.side.util.settings;

public class SidePreferences {
	/**
	 * Utility classes don't need to (and shouldn't) be instantiated.
	 */
	private SidePreferences() {
		// prevents instantiation
	}

	public static void setKey(String key){
		Activator.getDefault().getPreferenceStore().setValue(Activator.KEY_PREFERENCE, key);
	}

	public static String getKey(){
		return Activator.getDefault().getPreferenceStore().getString(Activator.KEY_PREFERENCE);
	}

	public static void setDefaultKey() {
		Activator.getDefault().getPreferenceStore().setValue(Activator.KEY_PREFERENCE, Activator.KEY_DEFAULT);
	}

	public static void setFeedBackPreference(int feedBackPreference){
		Activator.getDefault().getPreferenceStore().setValue(Activator.FEEDBACK_PREFERENCE, feedBackPreference);
	}

	public static int getFeedBackPreference(){
		return Activator.getDefault().getPreferenceStore().getInt(Activator.FEEDBACK_PREFERENCE);
	}

}
