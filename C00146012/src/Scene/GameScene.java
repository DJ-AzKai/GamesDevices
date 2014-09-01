package Scene;

import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import Manager.ResourceManager;
import Manager.SceneManager;
import Manager.SceneManager.SceneType;

import com.badlogic.gdx.math.Vector2;
import com.example.c00146012.BaseScene;

import Game.Player;
import Game.Strawberry;
import Game.Brick;
public class GameScene extends BaseScene {

//	Random rand = new Random();
//	private HUD gameHUD;
//	private Text scoreText;
//	private int score = 0;
//	private PhysicsWorld physicsWorld;
//	private Player player;
//	Brick[] bricks = new Brick [3];
//	Strawberry[] strawberries = new Strawberry [3];
//	private static boolean levelComplete = false;
//	private static boolean gameOver = false;
//	
//	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
//	{
//	    if (pSceneTouchEvent.isActionDown())
//	    {
//	    	
//	    }
//	    return false;
//	}
//	public static boolean getLevelComplete()
//	{
//		return levelComplete;
//	}
//	public static boolean getGameOver()
//	{
//		return gameOver;
//	}
//	
//	
//
//	private void createPhysics()
//	{
//	    physicsWorld = new PhysicsWorld(new Vector2(0, -17), false); 
//	    registerUpdateHandler(physicsWorld);
//	}
//	
	@Override
	public void createScene() {
		createBackground();		
	}
//	private void createGameObjects()
//	{
//		player = new Player(300, 750, vbom, camera, physicsWorld);
//		this.registerTouchArea(player);
//		this.setTouchAreaBindingOnActionDownEnabled(true);	
//		for(int i = 0; i < bricks.length; i++)
//		{
//			
//			bricks[i] = new Brick(0,(-500 *(i+1)),vbom, camera, physicsWorld)
//			{
//				@Override
//			    protected void onManagedUpdate(float pSecondsElapsed) 
//			    {
//			        super.onManagedUpdate(pSecondsElapsed);
//	
//			        if (player.collidesWith(this))
//			        {
//			        	
//			            player.onDie();
//			            if(player.getPlayerDead())
//			            {
//			            	gameOver = true;
//			            }
//			            this.setVisible(false);
//			            int newX = rand.nextInt(550) + 50;
//			            this.setPos(newX);
//			            this.setVisible(true);
//			        }
//			        
//			    }
//			};
//			this.attachChild(bricks[i]);
//		}
//		for(int k = 0; k < strawberries.length; k++)
//		{
//			strawberries[k] = new Strawberry(0,(-300 *(k+1)),vbom, camera, physicsWorld)
//			{
//				@Override
//			    protected void onManagedUpdate(float pSecondsElapsed) 
//			    {
//			        super.onManagedUpdate(pSecondsElapsed);
//
//			        if (player.collidesWith(this))
//			        {
//			            addToScore(10);
//			            if(score >= 100)
//			            {
//			            	levelComplete = true;
//			            }
//			            this.setVisible(false);
//			            int newX = rand.nextInt(550) + 50;
//			            this.setPos(newX);
//			            this.setVisible(true);
//			        }
//			    }
//			};
//			this.attachChild(strawberries[k]);
//		}
//	}
	private void createBackground()
	{
	    setBackground(new Background(Color.BLUE));
	}
//	private void addToScore(int i)
//	{
//		score += i;
//		scoreText.setText("Score: " + (score/10) + " / 10");
//	}
//	private void createHUD()
//	{
//	    gameHUD = new HUD();
//	    
//	    // CREATE SCORE TEXT
//	    scoreText = new Text(20, 420, ResourceManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
//	    scoreText.setSkewCenter(0, 0);    
//	    scoreText.setText("Score: 0");
//	    gameHUD.attachChild(scoreText);
//	    
//	    camera.setHUD(gameHUD);
//	}
//
	@Override
	public void onBackKeyPressed()
	{		
		SceneManager.getInstance().setGameSet(false);
	    SceneManager.getInstance().loadMenuScene(engine);	    
	}


	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene()
	{
	    camera.setHUD(null);
	    camera.setCenter(400, 240);

	    // TODO code responsible for disposing scene
	    // removing all game scene objects.
	}

}
