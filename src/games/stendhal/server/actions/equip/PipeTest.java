// $Id$
package games.stendhal.server.actions.equip;

import static org.junit.Assert.assertEquals;

//import java.util.Arrays;
//import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.common.EquipActionConsts;
//import games.stendhal.common.EquipActionConsts;
//import games.stendhal.common.EquipActionConsts;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
//import marauroa.common.game.RPAction;
//import marauroa.common.game.RPObject;
//import marauroa.common.game.RPObject;
//import marauroa.common.game.RPObject;
import utilities.PlayerTestHelper;
import utilities.ZoneAndPlayerTestImpl;

/**
 * Test cases for DisplaceAction.
 */
public class PipeTest extends ZoneAndPlayerTestImpl {

	private static final String ZONE_NAME = "int_semos_wizards_tower_1";

	public PipeTest() {
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
	public void testPipe() {
		final StendhalRPZone localzone = new StendhalRPZone(ZONE_NAME, 20, 20); // zone with disabled collision detection
		final Player player = PlayerTestHelper.createPlayer("bob");
		localzone.add(player);
		

		Item item = SingletonRepository.getEntityManager().getItem("pipe");
		assertEquals(0,player.getAllEquipped("pipe").size());
		player.equip("1hand", item);
		assertEquals(1, player.getAllEquipped("pipe").size());
	
		

	}
}