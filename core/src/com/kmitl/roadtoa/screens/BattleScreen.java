package com.kmitl.roadtoa.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kmitl.roadtoa.RoadToA;
import com.kmitl.roadtoa.entities.Characters;

public class BattleScreen implements Screen {
	
	private RoadToA game;
	private Characters player, enemy;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private Stage stage;
	private ShapeRenderer shapeRenderer;
	
	private boolean isPlayerTurn = true;
	private int time = 30, btnW = 100, btnH = 40, faculty, cooldown, totaldmg = 0;
	private float warpx, warpy;
	
	//Texture declaration
	private Texture attackButtonActive, attackButtonInActive;
	private Texture powerAttackButtonActive, powerAttackButtonInActive, powerAttackButtonCooldown;
	private Texture healButtonActive, healButtonInActive, healButtonCooldown;
	private Texture playerHealthBar, enemyHealthBar, background, arrow;
	private Texture playerTexture, playerAttackTexture, playerPowerAttackTexture, enemyTexture, enemyAttackTexture, healTexture;
	
	private Image playerImg, enemyImg, playerAttackImg, playerPowerAttackImg, enemyAttackImg, healImg1, healImg2, healImg3;
	
	private Music music;
	private Sound attackSound, powerAttackSound, healSound;
	
	//Constructor
	public BattleScreen(RoadToA game, String backgroundImg, int enemyHP, int enemyAP, float warpx, float warpy, int faculty, int isBoss) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, RoadToA.WIDTH, RoadToA.HEIGHT);
		this.stage = new Stage(new StretchViewport(RoadToA.WIDTH, RoadToA.HEIGHT, camera));
		Gdx.input.setInputProcessor(stage);
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale(1.5f);
		textureLoader();
		background = new Texture(backgroundImg);
		arrow = new Texture("battle/arrow.png");
		
		shapeRenderer = new ShapeRenderer();
		
		//create characters
		player = new Characters(250, 20);
		enemy = new Characters(enemyHP, enemyAP);
		
		//load character texture and set image
		playerTexture = new Texture(Gdx.files.internal("battle/player1.png"));
		playerImg = new Image(playerTexture);
		playerImg.setPosition(200, 100);
		playerImg.setSize(125, 225);
		stage.addActor(playerImg);
		if (isBoss == 0) {
			enemyHealthBar = new Texture("battle/enemybar_1.png");
			enemyTexture = new Texture(Gdx.files.internal("battle/enemy1.png"));
			enemyAttackTexture = new Texture(Gdx.files.internal("battle/enemy2.png"));
		}
		else{
			enemyHealthBar = new Texture("battle/bossbar_1.png");
			enemyTexture = new Texture(Gdx.files.internal("battle/boss1.png"));
			enemyAttackTexture = new Texture(Gdx.files.internal("battle/boss2.png"));
		}
		enemyImg = new Image(enemyTexture);
		enemyImg.setPosition(450, 100);
		enemyImg.setSize(275, 375);
		stage.addActor(enemyImg);
		
		playerAttackTexture = new Texture(Gdx.files.internal("battle/fireball.png"));
		playerPowerAttackTexture = new Texture(Gdx.files.internal("battle/fireball.png"));
		healTexture = new Texture(Gdx.files.internal("battle/healeffect.png"));
		healImg1 = new Image(healTexture);
		healImg2 = new Image(healTexture);
		healImg3 = new Image(healTexture);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("battle/battle.mp3"));
		music.setLooping(true);
		music.play();
		
		attackSound = Gdx.audio.newSound(Gdx.files.internal("battle/attack.mp3"));
		powerAttackSound = Gdx.audio.newSound(Gdx.files.internal("battle/powerattack.mp3"));
		healSound = Gdx.audio.newSound(Gdx.files.internal("battle/heal.mp3"));
		
		this.warpx = warpx;
		this.warpy = warpy;
		this.faculty = faculty;		
	}
	
	private void textureLoader(){
		attackButtonActive = new Texture("battle/atk1.png");
		attackButtonInActive = new Texture("battle/atk2.png");
		powerAttackButtonActive = new Texture("battle/pa1.png");
		powerAttackButtonInActive = new Texture("battle/pa2.png");
		powerAttackButtonCooldown = new Texture("battle/pa3.png");
		healButtonActive = new Texture("battle/heal1.png");
		healButtonInActive = new Texture("battle/heal2.png");
		healButtonCooldown = new Texture("battle/heal3.png");
		playerHealthBar = new Texture("battle/playerbar_1.png");
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		time++;

		//draw background
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		drawHealthBar();

		//draw button and health bar frame
		batch.begin();
		batch.draw(playerHealthBar, 50, 500, 300, 75);
		batch.draw(enemyHealthBar, 450, 500, 300, 75);
		buttonCreator(btnW, btnH, 200, 50, attackButtonActive, attackButtonInActive, attackButtonInActive, "attack");
		buttonCreator(btnW, btnH, 350, 50, powerAttackButtonActive, powerAttackButtonInActive, powerAttackButtonCooldown, "powerAttack");
		buttonCreator(btnW, btnH, 500, 50, healButtonActive, healButtonInActive, healButtonCooldown, "heal");
		font.draw(batch, "Cooldown : " + cooldown, 625, 80);
		if (isPlayerTurn) {
			batch.draw(arrow, 200, 425, 75, 75);
		}
		else {
			batch.draw(arrow, 525, 425, 75, 75);
		}
		batch.end();

		checkWinConditions();
		
		//cheat
		if (Gdx.input.isKeyJustPressed(Keys.Z)) {
			enemy.setCurrentHP(0);
		}
		
		//enemy's turn
		if (!isPlayerTurn && time > 80) {
			attack(enemy, player);
			isPlayerTurn = true;
			time = 0;
			
			enemyImg.addAction(sequence(fadeOut(0.1f)));
			enemyAttackImg = new Image(enemyAttackTexture);
			enemyAttackImg.setPosition(450, 100);
			enemyAttackImg.setSize(275, 375);
			stage.addActor(enemyAttackImg);
			enemyAttackImg.addAction(sequence(moveBy(-350, 0, 0.3f), moveBy(350, 0, 0.3f), fadeOut(0.1f)));
			enemyImg.addAction(sequence(fadeIn(0.1f)));
		}
		
		stage.act(delta);
		stage.draw();
	}
	
	private void drawHealthBar(){
		shapeRenderer.begin(ShapeType.Filled);
		//player health bar
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(100, 535, 240, 14);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(100, 535, 240 * (float) player.getCurrentHP() / (float) player.getMaxHP(), 14);
		//enemy health bar
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(460, 535, 240, 14);
		shapeRenderer.setColor(Color.RED);
		if (totaldmg > 100) {
			totaldmg = 100;
		}
		shapeRenderer.rect(460, 535, (totaldmg * 240) / 100, 14);
		shapeRenderer.end();
	}
	
	private void checkWinConditions(){
		if (enemy.getCurrentHP() <= 0) {
			RoadToA.SCORE++;
			switch (faculty) {
			case 1: RoadToA.Architecture = 1; break;
			case 2: RoadToA.IT = 1; break;
			case 3: RoadToA.Engineer = 1; break;
			case 4: RoadToA.Library = 1; break;
			case 5: RoadToA.Science = 1; break;
			case 6: RoadToA.PrathepBuilding = 1; break;
			case 7: RoadToA.ED = 1; break;
			}
			if(RoadToA.IT == 1 && time > 15){
				music.stop();
				game.setScreen(new Auditorium());
			}
			else if (time > 15) {
				game.setScreen(new MapScreen(game, warpx, warpy));
				music.stop();
			}
		}
		if (player.getCurrentHP() <= 0 && time > 5) {
			game.setScreen(new GameOverScreen(game));
			music.stop();
		}
		
	}

	private void attack(Characters attacker, Characters defender) {
		defender.damaged(attacker.getAttackPower());
	}

	private void powerAttack(Characters attacker, Characters defender) {
		defender.damaged(attacker.getAttackPower() * 3);
	}

	private void buttonCreator(int btnWidth, int btnHeight, int btnX, int btnY, Texture btnActive, Texture btnInactive, Texture btnCooldown,
			String a) {
		if (Gdx.input.getX() < btnX + btnWidth && Gdx.input.getX() > btnX && RoadToA.HEIGHT - Gdx.input.getY() < btnY + btnHeight
				&& RoadToA.HEIGHT - Gdx.input.getY() > btnY) {
			if (cooldown == 0) {
				batch.draw(btnActive, btnX, btnY, btnWidth, btnHeight);
				if (Gdx.input.isTouched()) {
					buttonAction(a);
				}
			}
			else if (cooldown > 0){
				if (a.equals("attack") && Gdx.input.isTouched()) {
					buttonAction(a);					
				}
				if (a.equals("attack")) {
					batch.draw(btnActive, btnX, btnY, btnWidth, btnHeight);
				}
				else{
					batch.draw(btnCooldown, btnX, btnY, btnWidth, btnHeight);
				}
			}
			
		} 
		else if (cooldown > 0 && !a.equals("attack")) {
			batch.draw(btnCooldown, btnX, btnY, btnWidth, btnHeight);
		}
		else {
			batch.draw(btnInactive, btnX, btnY, btnWidth, btnHeight);
		}
	}

	private void buttonAction(String a) {
		if (a.equals("attack") && isPlayerTurn && time > 80) {
			attack(player, enemy);
			isPlayerTurn = false;
			time = 0;
			totaldmg += 15;
			attackSound.play();
			playerAttackImg = new Image(playerAttackTexture);
			playerAttackImg.setPosition(200, 200);
			playerAttackImg.setSize(200, 100);
			stage.addActor(playerAttackImg);
			playerAttackImg.addAction(sequence(moveBy(200, 0, 0.3f), fadeOut(0.1f)));
			cooldown--;
			if (cooldown < 0) {
				cooldown = 0;
			}
		}
		if (a.equals("powerAttack") && isPlayerTurn && time > 80 && cooldown <= 0) {
			powerAttack(player, enemy);
			isPlayerTurn = false;
			time = 0;
			cooldown = 2;
			totaldmg += 45;
			powerAttackSound.play();
			playerPowerAttackImg = new Image(playerPowerAttackTexture);
			playerPowerAttackImg.setPosition(200, 200);
			playerPowerAttackImg.setSize(400, 200);
			stage.addActor(playerPowerAttackImg);
			playerPowerAttackImg.addAction(sequence(moveBy(400, 0, 0.3f),fadeOut(0.1f)));
		}
		if (a.equals("heal") && isPlayerTurn && time > 80 && cooldown <= 0) {
			player.heal(50);
			isPlayerTurn = false;
			time = 0;
			cooldown = 2;
			healImg1.setSize(50, 50);
			healImg2.setSize(50, 50);
			healImg3.setSize(50, 50);
			healImg1.setPosition(200, 150);
			healImg2.setPosition(230, 125);
			healImg3.setPosition(250, 175);
			stage.addActor(healImg1);
			stage.addActor(healImg2);
			stage.addActor(healImg3);
			healSound.play();
			healImg1.addAction(sequence(alpha(0f), parallel(moveBy(0, 55, 0.3f), fadeIn(0.1f)), fadeOut(0.1f)));
			healImg2.addAction(sequence(alpha(0f), parallel(moveBy(0, 50, 0.5f), fadeIn(0.1f)), fadeOut(0.1f)));
			healImg3.addAction(sequence(alpha(0f), parallel(moveBy(0, 85, 0.4f), fadeIn(0.1f)), fadeOut(0.1f)));
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		font.dispose();
		attackButtonActive.dispose();
		attackButtonInActive.dispose();
		powerAttackButtonActive.dispose();
		powerAttackButtonCooldown.dispose();
		powerAttackButtonInActive.dispose();
		healButtonActive.dispose();
		healButtonCooldown.dispose();
		healButtonInActive.dispose();
		playerHealthBar.dispose();
		enemyHealthBar.dispose();
		healTexture.dispose();
		background.dispose();
		arrow.dispose();
		playerTexture.dispose();
		playerAttackTexture.dispose();
		playerPowerAttackTexture.dispose();
		enemyTexture.dispose();
		enemyAttackTexture.dispose();
		attackSound.dispose();
		powerAttackSound.dispose();
		healSound.dispose();
	}

}
