// $Id$
package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//import java.util.Arrays;
//import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.common.EquipActionConsts;
//import games.stendhal.common.EquipActionConsts;
//import games.stendhal.common.EquipActionConsts;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.Creature;
//import games.stendhal.server.entity.item.Item;
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
public class pipeTest extends ZoneAndPlayerTestImpl {



	public pipeTest() {
	    super("test");
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

		setupZone("test");
	}

	/**
	 * Test for Pipe Item .
	 */
	@Test
	public void testPipe() {
		final StendhalRPZone localzone = new StendhalRPZone("test", 20, 20); // zone with disabled collision detection
		final Player player = PlayerTestHelper.createPlayer("bob");
		final Creature creature = SingletonRepository.getEntityManager().getCreature("spider");
		player.setPosition(5, 5);
		creature.setPosition(5, 4);
		localzone.add(creature);
		
		localzone.add(player);

		creature.setTarget(player);
		assertTrue(creature.attack());
		assertEquals(0, player.getAllEquipped("pipe").size());
		Item item = SingletonRepository.getEntityManager().getItem("pipe");
		player.equip("lhand", item);
		assertEquals(1, player.getAllEquipped("pipe").size());
		creature.setTarget(player);
		assertTrue(!creature.attack());
		
	
		

	}
}