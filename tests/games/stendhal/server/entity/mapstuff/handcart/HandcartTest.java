package games.stendhal.server.entity.mapstuff.handcart;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

//import com.sun.tools.doclint.Entity;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPClass;
import marauroa.common.game.SlotIsFullException;
import utilities.PlayerTestHelper;
//import utilities.RPClass.BlockTestHelper;

/**
 * Tests for the handcart
 *
 * @author Kang Fan and Bekhruz
 */
public class HandcartTest {

	@BeforeClass
	public static void beforeClass() {
		PlayerTestHelper.generatePlayerRPClasses();
        MockStendlRPWorld.get();
	}
	
	public void setUp() throws Exception {
		if (!RPClass.hasRPClass("entity")) {
			Entity.generateRPClass();
		}

		if (!RPClass.hasRPClass("handcart")) {
			Handcart.generateRPClass();
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPush() {
		Handcart b = new Handcart();
		b.setPosition(0, 0);
		StendhalRPZone z = new StendhalRPZone("test", 10, 10);
		Player p = PlayerTestHelper.createPlayer("pusher");
		z.add(b);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(0)));

		b.push(p, Direction.RIGHT);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(1)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(0)));

		b.push(p, Direction.LEFT);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(0)));

		b.push(p, Direction.DOWN);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(1)));

		b.push(p, Direction.UP);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(0)));
	}

	@Test
	public void testCollisionOnPush() throws Exception {
		Handcart b1 = new Handcart();
		b1.setPosition(0, 0);
		StendhalRPZone z = new StendhalRPZone("test", 10, 10);
		Player p = PlayerTestHelper.createPlayer("pusher");
		z.add(b1, false);

		// one successful push
		b1.push(p, Direction.RIGHT);
		assertThat(Integer.valueOf(b1.getX()), is(Integer.valueOf(1)));

		// now we add an obstacle right of b1
		Handcart b2 = new Handcart();
		b2.setPosition(02, 0);
		z.add(b2, false);

		// push should not be executed now and stay at the former place
		b1.push(p, Direction.RIGHT);
		assertThat(Integer.valueOf(b1.getX()), is(Integer.valueOf(1)));
	}


	/**
	 * Tests for size.
	 */
	@Test(expected = SlotIsFullException.class)
	public final void testSize() {
		final Handcart ch = new Handcart();
		assertEquals(0, ch.size());
		for (int i = 0; i < 30; i++) {
			ch.add(new PassiveEntity() {
			});
		}
		assertEquals(30, ch.size());
		ch.add(new PassiveEntity() {
		});
	}

	/**
	 * Tests for open.
	 */
	@Test
	public final void testOpen() {
		final Handcart ch = new Handcart();
		assertFalse(ch.isOpen());
		ch.open();

		assertTrue(ch.isOpen());
		ch.close();
		assertFalse(ch.isOpen());
	}

	/**
	 * Tests for onUsed.
	 */
	@Test
	public final void testOnUsed() {
		final Handcart ch = new Handcart();
		assertFalse(ch.isOpen());
		ch.onUsed(new RPEntity() {

			@Override
			protected void dropItemsOn(final Corpse corpse) {
			}

			@Override
			public void logic() {

			}
		});

		assertTrue(ch.isOpen());
		ch.onUsed(new RPEntity() {

			@Override
			protected void dropItemsOn(final Corpse corpse) {
			}

			@Override
			public void logic() {

			}
		});
		assertFalse(ch.isOpen());
	}
}