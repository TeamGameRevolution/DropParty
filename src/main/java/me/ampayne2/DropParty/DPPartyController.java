/*
 * This file is part of DropParty.
 *
 * Copyright (c) 2013-2013
 *
 * DropParty is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DropParty is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DropParty.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ampayne2.DropParty;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import me.ampayne2.DropParty.database.DatabaseManager;
import me.ampayne2.DropParty.database.tables.DPPartiesTable;

public class DPPartyController {

	public static Map<String, Boolean> isRunning = new HashMap<String, Boolean>();
	public static List<DPPartiesTable> parties = DatabaseManager.getDatabase().select(DPPartiesTable.class).execute().find();

	public static ListIterator<DPPartiesTable> li = parties.listIterator();

	public static void getParties() {
		if (!isRunning.isEmpty()) {
			isRunning.clear();
		}
		parties = DatabaseManager.getDatabase().select(DPPartiesTable.class).execute().find();
		li = parties.listIterator();
		if (parties.size() == 0) {
			return;
		}
		while (li.hasNext()) {
			DPPartiesTable entry = li.next();
			isRunning.put(entry.dpid, false);
		}
	}

	public static boolean isRunning(String dpid) {
		return isRunning.get(dpid);
	}

	public static boolean start(String dpid) {
		if (!isRunning.containsKey(dpid)) {
			return false;
		}
		if (isRunning.get(dpid).equals(dpid)) {
			isRunning.put(dpid, true);
			return true;
		}
		return false;
	}

	public static boolean stop(String dpid) {
		if (!isRunning.containsKey(dpid)) {
			return false;
		}
		if (isRunning.get(dpid).equals(dpid)) {
			isRunning.put(dpid, false);
			return true;
		}
		return false;
	}
}
