package games.stendhal.server.maps.ados.city;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class MakeupArtistNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "testzone";
	private static final String YOU_CAN_REPLY = "You can #buy";

	private Player player;
	private SpeakerNPC fidoreaNpc;
	private Engine fidoreaEngine;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME, new MakeupArtistNPC());
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		player = createPlayer("player");
		fidoreaNpc = SingletonRepository.getNPCList().get("Fidorea");
		fidoreaEngine = fidoreaNpc.getEngine();
	}

	public MakeupArtistNPCTest() {
		super(ZONE_NAME, "Fidorea");
	}

	@Test
	public void testDialogue() {
		startConversation();

		askForMaskList();

		endConversation();
	}

	private void startConversation() {
		fidoreaEngine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(fidoreaNpc.isTalking());
		assertEquals("Hi, there. Do you need #help with anything?", getReply(fidoreaNpc));
	}

	private void askForMaskList() {
		fidoreaEngine.step(player, "offer");
		assertTrue(fidoreaNpc.isTalking());
		String listOfMasksReply = getReply(fidoreaNpc);
		assertTrue(listOfMasksReply.startsWith(YOU_CAN_REPLY));
		assertTrue(listOfMasksReply.contains("fox"));
		assertTrue(listOfMasksReply.contains("white fox"));
		assertTrue(listOfMasksReply.contains("monkey"));
		assertTrue(listOfMasksReply.contains("penguin"));
		assertTrue(listOfMasksReply.contains("frog"));
		assertTrue(listOfMasksReply.contains("bear"));
	}

	private void endConversation() {
		fidoreaEngine.step(player, ConversationPhrases.GOODBYE_MESSAGES.get(0));
		assertFalse(fidoreaNpc.isTalking());
		assertEquals("Bye, come back soon.", getReply(fidoreaNpc));
	}
}
