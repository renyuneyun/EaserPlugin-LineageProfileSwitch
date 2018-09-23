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

import android.content.res.Resources;

public final class PluginInfo {
    public static final String ID = "ryey.lineage_profile_switch";

    public static String NAME(Resources resources) {
        return "LineageOS Profile Switch";
    }

    public static final String CATEGORY = "lineage";

    public static final String EDIT_DATA_ACTIVITY = EditDataActivity.class.getCanonicalName();

}
