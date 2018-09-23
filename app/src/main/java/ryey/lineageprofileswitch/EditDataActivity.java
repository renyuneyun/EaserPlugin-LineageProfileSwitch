/*
 * Copyright (c) 2018 Rui Zhao <renyuneyun@gmail.com>
 *
 * This file is part of LineageProfileSwitch.
 *
 * LineageProfileSwitch is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LineageProfileSwitch is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LineageProfileSwitch.  If not, see <http://www.gnu.org/licenses/>.
 */

package ryey.lineageprofileswitch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.UUID;

import lineageos.app.ProfileManager;
import ryey.easer.plugin.PluginDataFormat;
import ryey.easer.remote_plugin.RemoteOperationData;
import ryey.easer.remote_plugin.RemotePlugin;

public class EditDataActivity extends AppCompatActivity {

    String[] profiles;
    Spinner spinner;
    MySpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        spinner = findViewById(R.id.spinner);

        ProfileManager profileManager = ProfileManager.getInstance(this);
        profiles = profileManager.getProfileNames();
        adapter = new MySpinnerAdapter(this, profiles);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null) {
            RemoteOperationData remoteOperationData = intent.getParcelableExtra(RemotePlugin.EXTRA_DATA);
            if (remoteOperationData != null) {
                SwitchProfileData data = new SwitchProfileData(this, remoteOperationData);
                if (data.getProfileUUID() == null) {
                    Toast.makeText(this, "Profile not found; Using random instead", Toast.LENGTH_LONG).show();
                } else
                    setSelection(data.getProfileUUID());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_data, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_ok) {
            sendBackData();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void setSelection(UUID profile) {
        spinner.setSelection(adapter.getPosition(profile.toString()));
    }

    private RemoteOperationData getRemoteData() {
        String profile = (String) spinner.getSelectedItem();
        SwitchProfileData data = new SwitchProfileData(ProfileUUIDHelper.getFromName(this, profile));
        return new RemoteOperationData(PluginInfo.ID, PluginDataFormat.JSON, data.serialize(this, PluginDataFormat.JSON));
    }

    private void sendBackData() {
        Intent intent = new Intent();
        RemoteOperationData data = getRemoteData();
        intent.putExtra(RemotePlugin.EXTRA_DATA, data);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public static class MySpinnerAdapter extends ArrayAdapter<String> {

        MySpinnerAdapter(Context context, String[] profiles) {
            super(context, android.R.layout.simple_spinner_dropdown_item, profiles);
        }

    }
}
