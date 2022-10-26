// $Id$
package games.stendhal.server.actions.equip;

import static org.junit.Assert.assertEquals;

//import java.util.Arrays;
//import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.EquipActionConsts;
//import games.stendhal.common.EquipActionConsts;
//import games.stendhal.common.EquipActionConsts;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
//import marauroa.common.game.RPObject;
//import marauroa.common.game.RPObject;
import utilities.PlayerTestHelper;
import utilities.ZoneAndPlayerTestImpl;

/**
 * Test cases for DisplaceAction.
 */
public class ZekielsPracticalQuestTest extends ZoneAndPlayerTestImpl {

	private static final String ZONE_NAME = "int_semos_wizards_tower_1";

	public ZekielsPracticalQuestTest() {
	    super(ZONE_NAME);
    }

	/**
	 * Initialise the world.
	 *
	 * @throws Exception
	 */
	@BeforeClass
	public static void buildWorld() throws Exception {
		// initialise world
		SingletonRepository.getRPWorld();

		setupZone(ZONE_NAME);
	}

	/**
	 * Test for candle drop action in practical quest .
	 */
	@Test
	public void testCandleDrop() {
		final StendhalRPZone localzone = new StendhalRPZone(ZONE_NAME, 20, 20); // zone with disabled collision detection
		final Player player = PlayerTestHelper.createPlayer("bob");
		localzone.add(player);

		Item item = SingletonRepository.getEntityManager().getItem("candle");
		player.equipToInventoryOnly(item);

		assertEquals(0, localzone.getItemsOnGround().size());

		item = player.getFirstEquipped("candle");
		RPObject parent = item.getContainer();
		RPAction action = new RPAction();
		action.put("type", "drop");
		action.put("baseitem", item.getID().getObjectID());
		action.put(EquipActionConsts.BASE_OBJECT, parent.getID().getObjectID());
		action.put(EquipActionConsts.BASE_SLOT, item.getContainerSlot().getName());
		action.put("x", player.getX());
		action.put("y", player.getY() + 1);

		new DropAction().onAction(player, action);
		assertEquals(1, player.getAllEquipped("candle").size());
		

	}
}
