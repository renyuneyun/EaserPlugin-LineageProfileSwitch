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

import ryey.easer.remote_plugin.RemotePlugin;

public class ProfileInfoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (RemotePlugin.ACTION_REQUEST_PLUGIN_INFO.equals(intent.getAction())) {
            Intent response = new Intent(RemotePlugin.ACTION_RESPONSE_PLUGIN_INFO);
            response.putExtra(RemotePlugin.EXTRA_PACKAGE_NAME, context.getPackageName());
            response.putExtra(RemotePlugin.EXTRA_PLUGIN_NAME, PluginInfo.NAME(context.getResources()));
            response.putExtra(RemotePlugin.EXTRA_PLUGIN_ID, PluginInfo.ID);
            response.putExtra(RemotePlugin.EXTRA_PLUGIN_TYPE, RemotePlugin.TYPE_OPERATION_PLUGIN);
            response.putExtra(RemotePlugin.OperationPlugin.EXTRA_PLUGIN_CATEGORY, PluginInfo.CATEGORY);
            response.putExtra(RemotePlugin.EXTRA_ACTIVITY_EDIT_DATA, PluginInfo.EDIT_DATA_ACTIVITY);
            String replyPackage = intent.getStringExtra(RemotePlugin.EXTRA_REPLY_PACKAGE);
            response.setPackage(replyPackage);
            context.sendBroadcast(response);
        }
    }
}
