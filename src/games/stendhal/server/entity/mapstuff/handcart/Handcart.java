package games.stendhal.server.entity.mapstuff.handcart;

import java.awt.geom.Rectangle2D;
import java.util.List;

import org.apache.log4j.Logger;

import games.stendhal.common.Direction;
import games.stendhal.common.Rand;
import games.stendhal.common.constants.SoundLayer;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.MovementListener;
import games.stendhal.server.entity.ActiveEntity;
import games.stendhal.server.entity.mapstuff.block.Block;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.SoundEvent;

/**
 * A solid, movable block on a map. It can have different apearances,
 * for example a farm cart.
 *
 * @author Kang Fan and Bekhruz
 */
public class Handcart extends Chest implements MovementListener {
	
	private static final Logger logger = Logger.getLogger(Block.class);
	private int startX;
	private int startY;
	private boolean multi = true;
	private static final String Z_ORDER = "z";
	private final List<String> sounds =null;
	/*
	 * The facing direction
	 */
	private Direction direction;

	/** Allows entity to walk through collision areas */

	
	public Handcart() {
		super();
//		this.sounds = null;
		this.setResistance(100);
		direction = Direction.STOP;
	}
	
//	setRPClass(CHEST_RPCLASS_NAME);
//	put("type", CHEST_RPCLASS_NAME);
//	open = false;
//	final RPSlot slot = new ChestSlot(this);


	@Override
	public void onAdded(StendhalRPZone zone) {
		super.onAdded(zone);
		zone.addMovementListener(this);
		
	}
	
	public void push(Player p, Direction d) {
		if (!this.mayBePushed(d)) {
			return;
		}

		// after push
		int x = getXAfterPush(d);
		int y = getYAfterPush(d);
		this.setPosition(x, y);
		
		this.sendSound();
		this.notifyWorldAboutChanges();
//		if (logger.isDebugEnabled()) {
//			logger.debug("Block [" + this.getID().toString() + "] pushed to (" + this.getX() + "," + this.getY() + ").");
//		}
	}

	/**
	 * should the block reset to its original position after some time?
	 *
	 * @param resetBlock true, if the block should be reset; false otherwise
//	 */
//	public void setResetBlock(boolean resetBlock) {
//		this.resetBlock = resetBlock;
//	}

	private void sendSound() {
		if (this.sounds != null && !this.sounds.isEmpty()) {
			SoundEvent e = new SoundEvent(Rand.rand(sounds), SoundLayer.AMBIENT_SOUND);
			this.addEvent(e);
			this.notifyWorldAboutChanges();
		}
	}

	public int getYAfterPush(Direction d) {
		return this.getY() + d.getdy();
	}

	public int getXAfterPush(Direction d) {
		return this.getX() + d.getdx();
	}

	private boolean wasPushed() {
		boolean xChanged = this.getInt("x") != this.startX;
		boolean yChanged = this.getInt("y") != this.startY;
		return xChanged || yChanged;
	}

	private boolean mayBePushed(Direction d) {
		boolean pushed = wasPushed();
		int newX = this.getXAfterPush(d);
		int newY = this.getYAfterPush(d);

		if (!multi && pushed) {
			return false;
		}

		// additional checks: new position must be free
		boolean collision = this.getZone().collides(this, newX, newY);

		return !collision;
	}

	/**
	 * Get the shape of this Block
	 *
	 * @return the shape or null if this Block has no shape
	 */
	public String getShape() {
		if (this.has("shape")) {
			return this.get("shape");
		}
		return null;
	}
////
	@Override
	public void onEntered(ActiveEntity entity, StendhalRPZone zone, int newX, int newY) {
		// do nothing
	}

	@Override
	public void onExited(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY) {
//		if (logger.isDebugEnabled()) {
//			logger.debug("Block [" + this.getID().toString() + "] notified about entity [" + entity + "] exiting [" + zone.getName() + "].");
//		}
////		resetInPlayerlessZone(zone, entity);
//		 do nothing
	}

	@Override
	public void onMoved(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY, int newX, int newY) {
		// do nothing on move
	}



	@Override
	public void beforeMove(ActiveEntity entity, StendhalRPZone zone, int oldX,
			int oldY, int newX, int newY) {
		if (entity instanceof Player) {
			Rectangle2D oldA = new Rectangle2D.Double(oldX, oldY, entity.getWidth(), entity.getHeight());
			Rectangle2D newA = new Rectangle2D.Double(newX, newY, entity.getWidth(), entity.getHeight());
			Direction d = Direction.getAreaDirectionTowardsArea(oldA, newA);
			this.push((Player) entity, d);
		}
	}

	

}
