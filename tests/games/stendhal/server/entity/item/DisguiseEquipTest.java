package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.EquipActionConsts;
import games.stendhal.server.actions.equip.EquipAction;
import games.stendhal.server.actions.equip.EquipmentAction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import marauroa.common.game.RPAction;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;
import utilities.ZoneAndPlayerTestImpl;


/**
 * Test cases for disguise armour.
 *
 * @author kavan
 */
public class DisguiseEquipTest extends ZoneAndPlayerTestImpl{
	
	private static final String ZONE_NAME = "0_semos_city";

	public DisguiseEquipTest() {
	    super(ZONE_NAME);
    }
	
	
	/**
	 * initialize the world.
	 *
	 * @throws Exception
	 */
	@BeforeClass
	public static void buildWorld() throws Exception {
		new DatabaseFactory().initializeDatabase();
		setupZone(ZONE_NAME);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}
	
	/**
	 * Test picking up a disguise armor item from ground and equipping it.
	 */
	@Test
	public void testPickUp() {
		final Player player = PlayerTestHelper.createPlayer("bob");
		StendhalRPZone localzone = new StendhalRPZone("testzone", 20, 20);
		SingletonRepository.getRPWorld().addRPZone(localzone);
		Item item = SingletonRepository.getEntityManager().getItem("orc disguise"); // Create
		
		assertEquals(15, item.getDefense());
		assertEquals("armor", item.getItemClass());
		assertEquals("orc_disguise", item.getItemSubclass());
		assertEquals(0, item.getAttack());
		
		localzone.add(item);
		localzone.add(player);
		RPAction equip = new RPAction();
		equip.put("type", "equip");
		equip.put(EquipActionConsts.BASE_ITEM, item.getID().getObjectID());
		equip.put(EquipActionConsts.TARGET_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.TARGET_SLOT, "armor");
		equip.put(EquipActionConsts.QUANTITY, "1");


		final EquipmentAction action = new EquipAction();
		action.onAction(player, equip);

		Assert.assertEquals(1, player.events().size());
		assertEquals(0, localzone.getItemsOnGround().size());
		player.equip("armor", item);
		assertTrue(player.isEquipped("orc disguise", 1));
	}
}
