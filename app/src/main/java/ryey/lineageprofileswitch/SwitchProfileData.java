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

import android.content.Context;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;
import java.util.UUID;

import ryey.easer.plugin.PluginDataFormat;
import ryey.easer.remote_plugin.RemoteOperationData;

public class SwitchProfileData {

    public static final String K_PROFILE_NAME = "profileName";

    private UUID profileUUID;

    public SwitchProfileData(Context context, RemoteOperationData remoteOperationData) {
        try {
            JSONObject jsonObject = new JSONObject(remoteOperationData.rawData);
            String profileName = jsonObject.getString(K_PROFILE_NAME);
            try {
                profileUUID = ProfileUUIDHelper.getFromName(context, profileName);
            } catch (NoSuchElementException e) {
                profileUUID = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public SwitchProfileData(UUID profile) {
        this.profileUUID = profile;
    }

    @Nullable
    public UUID getProfileUUID() {
        return profileUUID;
    }

    public String serialize(Context context, PluginDataFormat format) {
        return ProfileUUIDHelper.toName(context, profileUUID);
    }
}
