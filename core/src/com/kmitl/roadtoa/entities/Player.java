package com.kmitl.roadtoa.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

	/** the movement velocity */
	private Vector2 velocity = new Vector2();

	private float speed = 150, animationTime = 0;

	private TiledMapTileLayer collisionLayer;

	private String blockedKey = "blocked";

	private Animation still, left, right, up, down;

	public Player(Animation still, Animation left, Animation right, Animation up, Animation down,
			TiledMapTileLayer collisionLayer) {
		super(still.getKeyFrame(0));
		this.still = still;
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
		this.collisionLayer = collisionLayer;
		//set player size
		setSize(getWidth()*1.5f, getHeight()*2);
	}

	@Override
	public void draw(Batch Batch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(Batch);
	}

	public void update(float delta) {
		// clamp velocity
		if (velocity.y > speed)
			velocity.y = speed;
		else if (velocity.y < -speed)
			velocity.y = -speed;

		// save old position
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;

		// move on x
		setX(getX() + velocity.x * delta);

		if (velocity.x < 0) // going left
			collisionX = collidesLeft();
		else if (velocity.x > 0) // going right
			collisionX = collidesRight();

		// react to x collision
		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}

		// move on y
		setY(getY() + velocity.y * delta);

		if (velocity.y < 0) // going down
			collisionY = collidesBottom();
		else if (velocity.y > 0) // going up
			collisionY = collidesTop();

		// react to y collision
		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}

		// update animation
		if (velocity.x != 0 || velocity.y != 0)
			animationTime += delta;
		setRegion(velocity.x < 0 ? left.getKeyFrame(animationTime)
				: velocity.x > 0 ? right.getKeyFrame(animationTime)
						: velocity.y > 0 ? up.getKeyFrame(animationTime)
								: velocity.y < 0 ? down.getKeyFrame(animationTime) : still.getKeyFrame(animationTime));

	}

	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()),
				(int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}

	public boolean collidesRight() {
		for (float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if (isCellBlocked(getX() + getWidth(), getY() + step))
				return true;
		return false;
	}

	public boolean collidesLeft() {
		for (float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if (isCellBlocked(getX(), getY() + step))
				return true;
		return false;
	}

	public boolean collidesTop() {
		for (float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if (isCellBlocked(getX() + step, getY() + getHeight()))
				return true;
		return false;

	}

	public boolean collidesBottom() {
		for (float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if (isCellBlocked(getX() + step, getY()))
				return true;
		return false;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
			if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
				velocity.x = 0;
			}
			velocity.y = speed;
			animationTime = 0;
			break;
		case Keys.LEFT:
			if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.UP)) {
				velocity.y = 0;
			}
			velocity.x = -speed;
			animationTime = 0;
			break;
		case Keys.RIGHT:
			if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.UP)) {
				velocity.y = 0;
			}
			velocity.x = speed;
			animationTime = 0;
			break;
		case Keys.DOWN:
			if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
				velocity.x = 0;
			}
			velocity.y = -speed;
			animationTime = 0;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			velocity.x = 0;
			animationTime = 0.5f;
			break;
		case Keys.RIGHT:
			velocity.x = 0;
			animationTime = 1;
			break;
		case Keys.UP:
			velocity.y = 0;
			animationTime = 1.5f;
			break;
		case Keys.DOWN:
			velocity.y = 0;
			animationTime = 2;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}