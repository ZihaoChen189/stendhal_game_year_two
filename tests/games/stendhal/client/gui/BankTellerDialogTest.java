package games.stendhal.client.gui;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.ados.bank.BankNPC;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.PlayerTestHelper;


public class BankTellerDialogTest {
	private Player player;
	SpeakerNPC npc;
	Engine en;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		final StendhalRPZone zone = new StendhalRPZone("int_ados_bank");
		MockStendlRPWorld.get().addRPZone(zone);
		new BankNPC().configureZone(zone, null);
	}

	@Before
	public void setUp() {
		player = PlayerTestHelper.createPlayer("bob");
		npc = SingletonRepository.getNPCList().get("Rachel");
		en = npc.getEngine();
	}

	@Test
	public void testConversation() {
		en.step(player, "hi");
		en.step(player, "bankstatement");
		assertEquals("Bankstatement is a new feature. After you adding things to your bank chest, you can check your bankstatement in the travel log panel. There is also #update function.", getReply(npc));
		en.step(player, "update");
		assertEquals("You can click the 'update' bottom in the travel log panel to update your bankstatement. You are on free trial by now, so the service is totally free.", getReply(npc));
	}

}
