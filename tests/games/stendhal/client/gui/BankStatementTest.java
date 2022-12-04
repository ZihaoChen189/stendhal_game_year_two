package games.stendhal.client.gui;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.server.entity.player.Player;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import games.stendhal.server.actions.query.ProgressStatusQueryAction;

public class BankStatementTest extends ProgressStatusQueryAction{
	ProgressStatusQueryAction psqa = new ProgressStatusQueryAction();
		private Player playerA = null;
		List<String> rightList = Arrays.asList("Open Quests", "Completed Quests", "Production", "Bank Statement");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}
	
	@Before
	public void SetUpPlayer() {
		playerA = PlayerTestHelper.createPlayer("sky");
	}
	
	@Test
	public void TestTravelLogContent(){
		List<String>currentList = psqa.sendProgressTypes(playerA);
		assertEquals(rightList, currentList);
	}
}

