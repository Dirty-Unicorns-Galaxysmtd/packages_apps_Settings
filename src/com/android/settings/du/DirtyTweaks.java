/*
 * Copyright (C) 2013 The Dirty Unicorns project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.du;

import android.app.ActivityManagerNative;
import android.content.Context;
import android.content.ContentResolver;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.preference.Preference;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManagerGlobal;
import android.view.IWindowManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class DirtyTweaks extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "DirtyTweaks";

    private static final String CATEGORY_NAVBAR = "navigation_bar";
    private static final String KEY_SHOW_NAVBAR = "show_navigation_bar";

    private CheckBoxPreference mShowNavbarPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.dirtytweaks);
        PreferenceScreen prefSet = getPreferenceScreen();

        try {
            boolean hasNavBar = WindowManagerGlobal.getWindowManagerService().hasNavigationBar();

            mShowNavbarPref =
                    (CheckBoxPreference) findPreference(KEY_SHOW_NAVBAR);

            mShowNavbarPref.setOnPreferenceChangeListener(this);
            mShowNavbarPref.setChecked(hasNavBar);

        } catch (RemoteException e) {
            Log.e(TAG, "Error getting navigation bar status");
        }
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mShowNavbarPref) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), Settings.System.SHOW_NAVIGATION,
                    value ? 1 : 0);
            return true;
        }

        return false;
    }
}
