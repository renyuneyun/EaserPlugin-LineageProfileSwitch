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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import lineageos.app.ProfileManager;
import ryey.easer.remote_plugin.RemoteOperationData;
import ryey.easer.remote_plugin.RemotePlugin;

public class TriggerOperationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (RemotePlugin.OperationPlugin.ACTION_TRIGGER.equals(intent.getAction())) {
            RemoteOperationData remoteOperationData = intent.getParcelableExtra(RemotePlugin.EXTRA_DATA);
            SwitchProfileData data = new SwitchProfileData(context, remoteOperationData);
            boolean result = false;
            if (context.getPackageManager().hasSystemFeature("org.lineageos.profiles")) {
                ProfileManager.getInstance(context).setActiveProfile(data.getProfileUUID());
                result = true;
            }
            Intent reply = new Intent(RemotePlugin.OperationPlugin.ACTION_TRIGGER_RESULT);
            String replyPackage = intent.getStringExtra(RemotePlugin.EXTRA_REPLY_PACKAGE);
            // For a correctly implemented communication (since Easer v0.7.9), we could `assert replyPackage != null`
            reply.setPackage(replyPackage);
            reply.putExtra(RemotePlugin.OperationPlugin.EXTRA_SUCCESS, result);
            context.sendBroadcast(reply);
        }
    }
}
