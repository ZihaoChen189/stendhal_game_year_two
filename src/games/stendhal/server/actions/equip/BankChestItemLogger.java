package games.stendhal.server.actions.equip;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import games.stendhal.server.entity.mapstuff.chest.PersonalChest;


public class BankChestItemLogger extends EquipAction{
	private static Map<String, List<String>> bankListMap = new HashMap<>();

	public static Map<String, List<String>> getList() {
		return bankListMap;
	}
	
	public static void getLog(final SourceObject source, final DestinationObject dest, final String itemName) {
		if (dest.parent instanceof PersonalChest || source.parent instanceof PersonalChest) {
			String name = null;
			String actionName = null;
			
			if (dest.parent instanceof PersonalChest) {
				name = dest.parent.getZone().getName();
				actionName = "deposited";
			}
			else {
				name = source.parent.getZone().getName();
				actionName = "withdrawed";
			}


			List<String> logger = null;
			if (bankListMap.containsKey(name)) {
				logger = bankListMap.get(name);
			} else {
				logger = new ArrayList<>();
				bankListMap.put(name, logger);
			}
			if (actionName == "deposited") {
				logger.add("    " + source.getQuantity() + "    " + itemName);
			}
			else if (actionName == "withdrawed") {
				for (int i=0; i < logger.size(); i++) {
					if (logger.get(i).contains(itemName)) {
						logger.remove(i);
						break;
					}
				}
			}
		}	
	}
	
}