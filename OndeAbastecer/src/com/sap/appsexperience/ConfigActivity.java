package com.sap.appsexperience;

import com.sap.appsexperience.util.LocalCache;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ConfigActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	
	protected void onPause() {
		super.onPause();
		LocalCache.getInstance().updateTipoPreferencia();
	}
}
