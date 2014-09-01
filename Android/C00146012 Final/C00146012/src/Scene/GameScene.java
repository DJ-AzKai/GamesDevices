package Scene;

import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.util.GLState;
import org.andengine.util.HorizontalAlign;

import Game.Brick;
import Game.Player;
import Game.Strawberry;
import Manager.ResourceManager;
import Manager.SceneManager;
import Manager.SceneManager.SceneType;
import android.content.Intent;

import com.badlogic.gdx.math.Vector2;
import com.example.c00146012.BaseScene;
import com.example.c00146012.GameActivity;
import com.example.c00146012.Level2Activity;
public class GameScene extends BaseScene implements IUpdateHandler{

	private int level = 1;
	/*Game Variables*/
	Random rand = new Random();
	private HUD gameHUD;
	private Text scoreText;
	private int score = 0;
	private PhysicsWorld physicsWorld;
	private Player player;
	Brick[] bricks = new Brick [3];
	Strawberry[] strawberries = new Strawberry [3];
	private static boolean levelComplete = false;
	private static boolean gameOver = false;
	boolean loaded = false;
	ResourceManager resMan;
	
	//game Music
	public Music gameMusic;	
	public Music absorb;
	public Music splat;
	public Music smash;

	@Override
	public void createScene() {
		resMan = ResourceManager.getInstance();
		createBackground();	
		createHUD();
		onLoad();
		playAudio();
		createPhysics();
	}
	private void createPhysics()
	{
	    physicsWorld = new PhysicsWorld(new Vector2(0, -17), false); 
	    engine.registerUpdateHandler(physicsWorld);
	}
	public void onLoad()
	{
//create the sprites. 
    	
    	player = new Player(300, 750, resMan.vbom, resMan.camera, physicsWorld);
		this.registerTouchArea(player);
		this.setTouchAreaBindingOnActionDownEnabled(true);	
		for(int i = 0; i < bricks.length; i++)
		{
			
			bricks[i] = new Brick(0,(-500 *(i+1)),resMan.vbom, resMan.camera, physicsWorld);
			this.attachChild(bricks[i]);
		}
		for(int k = 0; k < strawberries.length; k++)
		{
			strawberries[k] = new Strawberry(0,(-300 *(k+1)),resMan.vbom, camera, physicsWorld);
			this.attachChild(strawberries[k]);
		}
    	
		this.attachChild(player);
	}
	private void playAudio()
	{
		ResourceManager.menuMusic.stop();
        ResourceManager.gameMusic.play();
        ResourceManager.gameMusic.setLooping(true); 
	}
	 private void addToScore(int i)
		{
			score += i;
			scoreText.setText("Score: " + (score/10) + " / 10");
		}
		private void createHUD()
		{
		    gameHUD = new HUD();	    
		    // CREATE SCORE TEXT
		    scoreText = new Text(20, 420, ResourceManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), resMan.vbom);
		    scoreText.setSkewCenter(0, 0);    
		    scoreText.setText("Score: 0");
		    gameHUD.attachChild(scoreText);	    
		    camera.setHUD(gameHUD);
		}
		private void loadMusic()
		{
			gameMusic = ResourceManager.gameSounds()[0];
			splat = ResourceManager.gameSounds()[1];
			smash = ResourceManager.gameSounds()[2];
			gameMusic = ResourceManager.gameSounds()[3];
		}
	private void createBackground()
	{
		attachChild(new Sprite(100, 0, ResourceManager.game_background, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    });
	}
	@Override
	public void onBackKeyPressed()
	{		
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
	}
	@Override
	public void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		final Intent myIntent = new Intent(GameActivity.g, Level2Activity.class);
    	engine.registerUpdateHandler(new IUpdateHandler() {

			public void onUpdate(float pSecondsElapsed) 
			{
				//here goes the game Logic
				if(!gameOver && !levelComplete) //so if the game is still running
				{
					if(!loaded)
					{
						loadMusic();
						loaded = true;
					}
					//what to do?
					for(int i = 0; i < 3; i++)
					{
						if (player.collidesWith(bricks[i]))
				        {
				        	
				            player.onDie();
				            if(player.getPlayerDead())
				            {
				            	gameOver = true;
				            }
				            ResourceManager.smash.play();
				            bricks[i].setVisible(false);
				            int newX = rand.nextInt(550) + 50;
				            bricks[i].setPos(newX);
				            bricks[i].setVisible(true);
				        }
					}
					for(int k = 0; k < strawberries.length; k++)
					{
						if (player.collidesWith(strawberries[k]))
				        {
				            addToScore(10);
				            if(score >= 100)
				            {
				            	levelComplete = true;
				            }
				            ResourceManager.absorb.play();
				            strawberries[k].setVisible(false);
				            int newX = rand.nextInt(550) + 50;
				            strawberries[k].setPos(newX);
				            strawberries[k].setVisible(true);
				        }
					}
				}
				else if(levelComplete)
				{
					myIntent.putExtra("Score", score);
					myIntent.putExtra("Level", level);
					GameActivity.g.startActivity(myIntent);
					engine.unregisterUpdateHandler(this);
				}
				else if(gameOver)
				{
					System.exit(0);
				}
    	}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}});
	}
}
