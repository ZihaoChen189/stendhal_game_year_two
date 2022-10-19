/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.ados.forest.FarmerNPC;
import games.stendhal.server.maps.semos.tavern.BowAndArrowSellerNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

/*
 * A new test class was created and some specific libraries were imported.
 * Some private and static variables were designed following the original style of BowsForOuchitTest.java.
 * Therefore, the tests could be checked after the preparatory work was set through function setUp().
 */
public class KarlActionForHorseHairTest {
	private static final String QUEST_SLOT = "bows_ouchit";
	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}
	@Before
	public void setUp() {
		final StendhalRPZone zone = new StendhalRPZone("admin_test");
		// this is Ouchit
		new BowAndArrowSellerNPC().configureZone(zone, null);
		// this is Karl
		new FarmerNPC().configureZone(zone, null);
		AbstractQuest quest = new BowsForOuchit();
		quest.addToWorld();
		player = PlayerTestHelper.createPlayer("bob");
	}
	
	
	@Test
	public void testKarlActionBySayOuchit() {
		// The getEngine() was called to help construct the later JUnit tests.
		npc = SingletonRepository.getNPCList().get("Karl");
		en = npc.getEngine();

		// the state wasn't remembered across the new test method so we need to set it to what it was when we ended the last
		player.setQuest(QUEST_SLOT, "hair");

		// tests about normal features of NPC Karl
		en.step(player, "hi");
		assertEquals("Heyho! Nice to see you here at our farm.", getReply(npc));
		en.step(player, "help");
		assertEquals("You need help? I can tell you a bit about the #neighborhood.", getReply(npc));
		en.step(player, "neighborhood");
		assertEquals("In the north is a cave with bears and other creatures. If you go to the north-east you will reach after some time the great city Ados. At the east is a biiig rock. Does Balduin still live there? You want to go south-east? Well.. you can reach Ados there too, but I think the way is a bit harder.", getReply(npc));
		en.step(player, "task");
		assertEquals("I don't have time for those things, sorry. Working.. working.. working..", getReply(npc));

		/*
		 * The aim of these two tests was to check whether Karl could provide the right response when getting the key word "Ouchit" 
		 * and he should not response when get the second key word in the whole quest.
		 */
		en.step(player, "Ouchit");
		assertEquals("Hello, hello! Ouchit needs more horse hairs from my horses? No problem, here you are. Send Ouchit greetings from me.", getReply(npc));
		en.step(player, "horse hair");
		assertNull(getReply(npc));

		// tests about normal features of NPC Karl
		en.step(player, "bye");
		assertEquals("Bye bye. Be careful on your way.", getReply(npc));

		// check quest slot and item
		assertTrue(player.isEquipped("horse hair"));
		assertEquals(player.getQuest(QUEST_SLOT),"hair");
	}
	
	@Test
	public void testKarlActionBySayHorseHair() {
		npc = SingletonRepository.getNPCList().get("Karl");
		en = npc.getEngine();

		// the state wasn't remembered across the new test method so we need to set it to what it was when we ended the last
		player.setQuest(QUEST_SLOT, "hair");

		// tests about normal features of NPC Karl
		en.step(player, "hi");
		assertEquals("Heyho! Nice to see you here at our farm.", getReply(npc));
		en.step(player, "help");
		assertEquals("You need help? I can tell you a bit about the #neighborhood.", getReply(npc));
		en.step(player, "neighborhood");
		assertEquals("In the north is a cave with bears and other creatures. If you go to the north-east you will reach after some time the great city Ados. At the east is a biiig rock. Does Balduin still live there? You want to go south-east? Well.. you can reach Ados there too, but I think the way is a bit harder.", getReply(npc));
		en.step(player, "task");
		assertEquals("I don't have time for those things, sorry. Working.. working.. working..", getReply(npc));

		/*
		 * Similar to the former one, the aim of these two tests was to check whether Karl could provide the right response when getting the key word "horse hair" 
		 * and he should not response when get the second key word in the whole quest.
		 */
		en.step(player, "horse hair");
		assertEquals("Hello, hello! Ouchit needs more horse hairs from my horses? No problem, here you are. Send Ouchit greetings from me.", getReply(npc));
		en.step(player, "Ouchit");
		assertNull(getReply(npc));
		
		// tests about normal features of NPC Karl
		en.step(player, "bye");
		assertEquals("Bye bye. Be careful on your way.", getReply(npc));

		// check quest slot and item
		assertTrue(player.isEquipped("horse hair"));
		assertEquals(player.getQuest(QUEST_SLOT),"hair");
	}

}
