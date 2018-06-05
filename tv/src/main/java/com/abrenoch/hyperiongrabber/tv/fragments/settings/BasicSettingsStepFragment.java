package com.abrenoch.hyperiongrabber.tv.fragments.settings;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;

import com.abrenoch.hyperiongrabber.common.util.Preferences;
import com.abrenoch.hyperiongrabber.tv.R;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BasicSettingsStepFragment extends SettingsStepBaseFragment {
    private static final long ACTION_HOST_NAME = 100L;
    private static final long ACTION_PORT = 110L;
    private static final long ACTION_RECONNECT = 120L;
    private static final long ACTION_RECONNECT_DELAY = 130L;
    private static final long ACTION_MESSAGE_PRIORITY = 140L;
    private static final long ACTION_CAPTURE_RATE = 150L;

    private Preferences prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getPreferences();
    }

    @Override
    public int onProvideTheme() {
        return R.style.Theme_Example_Leanback_GuidedStep_First;
    }

    @Override
    @NonNull
    public GuidanceStylist.Guidance onCreateGuidance(@NonNull Bundle savedInstanceState) {
        String title = getString(R.string.guidedstep_basic_settings_title);
        String description = getString(R.string.guidedstep_basic_settings_description);
        String breadCrumb = getString(R.string.guidedstep_basic_settings_breadcrumb);
        Drawable icon = getActivity().getDrawable(R.drawable.ic_settings_ethernet_white_128dp);
        return new GuidanceStylist.Guidance(title, description, breadCrumb, icon);
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        String desc = getResources().getString(R.string.guidedstep_action_description);
        GuidedAction stepInfo = new GuidedAction.Builder(getContext())
                .title(getResources().getString(R.string.guidedstep_action_title))
                .description(desc)
                .multilineDescription(true)
                .focusable(false)
                .infoOnly(true)
                .enabled(false)
                .build();

        GuidedAction enterHost = new GuidedAction.Builder(getContext())
                .id(ACTION_HOST_NAME)
                .title(getString(R.string.pref_title_host))
                .description(getPreferences().getString(R.string.pref_key_hyperion_host, null))
                .descriptionEditable(true)
                .build();
        GuidedAction enterPort = new GuidedAction.Builder(getContext())
                .id(ACTION_PORT)
                .title(getString(R.string.pref_title_port))
                .description(getPreferences().getString(R.string.pref_key_hyperion_port, "19445"))
                .descriptionEditable(true)
                .descriptionInputType(InputType.TYPE_CLASS_PHONE)
                .descriptionEditInputType(InputType.TYPE_CLASS_PHONE)
                .build();

        GuidedAction reconnect = new GuidedAction.Builder(getContext())
                .id(ACTION_RECONNECT)
                .title(getString(R.string.pref_title_reconnect))
                .checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                .checked(false)
                .build();

        actions.add(stepInfo);
        actions.add(enterHost);
        actions.add(enterPort);
        actions.add(reconnect);

    }

    @Override
    public void onCreateButtonActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        actions.add(continueAction());
        actions.add(backAction());
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        if (action.getId() == SettingsStepBaseFragment.CONTINUE) {

            try {
                String host = assertValue(ACTION_HOST_NAME);
                String port = assertValue(ACTION_PORT);

                prefs.putString(R.string.pref_key_hyperion_host, host);
                prefs.putString(R.string.pref_key_hyperion_port, port);

                FragmentActivity activity = getActivity();
                activity.setResult(Activity.RESULT_OK);
                finishGuidedStepSupportFragments();

            } catch (AssertionError ignored) {}

            return;

        }

        super.onGuidedActionClicked(action);
    }


}
