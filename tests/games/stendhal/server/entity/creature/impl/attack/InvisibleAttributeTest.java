package games.stendhal.server.entity.creature.impl.attack;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.common.EquipActionConsts;
import games.stendhal.server.actions.equip.EquipAction;
import games.stendhal.server.actions.equip.EquipmentAction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPAction;
import utilities.PlayerTestHelper;

public class InvisibleAttributeTest {
	
  @BeforeClass
  public static void beforeClass() {
    MockStendlRPWorld.get();
  }

  
  // tests for Orc Attack
  @Test
  public void testAttackOrc() {
    // game zone and creature "Orc" is created
    final StendhalRPZone zone = new StendhalRPZone("hthtest");
    final Creature creature = new Creature();
    creature.setEntityClass("orc");
    creature.setName("orc");
    creature.setPosition(10, 10);
    
    assertTrue(creature.getName().equals("orc"));
    
    // a new player is set here
    final Player victim = PlayerTestHelper.createPlayer("bob");
    victim.setHP(100);  // HP value of the player is set, which must be larger than 0.
    zone.add(victim);

    
    // equip player with the new armor "orc disguise" from the Item
    Item item = SingletonRepository.getEntityManager().getItem("orc disguise"); 
    
    // put the armor on the victim firstly
    zone.add(item);
    RPAction equip = new RPAction();
    equip.put("type", "equip");
    
    equip.put(EquipActionConsts.BASE_ITEM, item.getID().getObjectID());
    equip.put(EquipActionConsts.TARGET_OBJECT, victim.getID().getObjectID());
    equip.put(EquipActionConsts.TARGET_SLOT, "armor");  // item
    equip.put(EquipActionConsts.QUANTITY, "1");  // number
    
    final EquipmentAction action = new EquipAction();
    action.onAction(victim, equip);

    victim.equip("armor", item);  // equip
    assertTrue(victim.isEquipped("orc disguise", 1));  // check equipped successfully
    
    // put the creature and the victim together
    zone.add(creature);   
    victim.setPosition(9, 10);
    assertTrue(creature.nextTo(victim));
    assertTrue(victim.nextTo(creature));
    
    // test attack results
    for (final RPEntity enemy : creature.getEnemyList()) {
      assertFalse(enemy.getName().equals("bob"));
    }   
   }
  
  
  // tests for Rat Attack
  @Test
  public void testAttackRat() {
	  
    // game zone and creature "Rat" is created
    final StendhalRPZone zone = new StendhalRPZone("hthtest");
    final Creature creature = new Creature();
    creature.setEntityClass("rat");
    creature.setName("rat");
    creature.setPosition(10, 10);
    
    // a new player is set here
    final Player victim = PlayerTestHelper.createPlayer("bob");
    victim.setHP(1);  // HP value of the player is set, which must be larger than 0.
    zone.add(creature);
    zone.add(victim);
    creature.setTarget(victim);  // get target
    
    // put the creature and the victim together
    victim.setPosition(9, 10);
    assertTrue(creature.nextTo(victim));
    assertTrue(victim.nextTo(creature));
    
    // test attack results
    assertTrue(creature.isAttacking());
  }
}
