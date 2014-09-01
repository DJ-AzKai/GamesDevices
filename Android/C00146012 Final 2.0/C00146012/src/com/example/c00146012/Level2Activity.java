package com.example.c00146012;

import java.io.IOException;
import java.util.Random;

import Scene.GameScene;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;

import com.badlogic.gdx.math.Vector2;

import Game.Brick;
import Game.Player;
import Game.Strawberry;
import Manager.ResourceManager;
import Manager.SceneManager;
import android.content.Intent;
import android.view.KeyEvent;

public class Level2Activity extends BaseGameActivity
{
	
	private Camera camera;
	private int CAMERA_WIDTH = 800;
	private int CAMERA_HEIGHT = 480;
	private ResourceManager resourceManager;
	private Scene scene;
	private int level;
	private int passedScore;
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
	private boolean end = false;
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
    {
    	ResourceManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
        resourceManager = ResourceManager.getInstance();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
    {
    	SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
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
	    scoreText = new Text(20, 420, ResourceManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), resourceManager.vbom);
	    scoreText.setSkewCenter(0, 0);    
	    scoreText.setText("Score: 0");
	    gameHUD.attachChild(scoreText);	    
	    camera.setHUD(gameHUD);
	}
    @Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
    	mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
            }
        }));
    	//handle intent here
    	passedScore = getIntent().getIntExtra("score", 0);
    	level = getIntent().getIntExtra("Level", 1);
    	level +=1;
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
    public Scene onLoadScene()
    {
    	scene = new Scene();
    	createPhysics();
    	
    	//create the sprites. 
    	
    	player = new Player(300, 750, resourceManager.vbom, resourceManager.camera, physicsWorld);
		scene.registerTouchArea(player);
		scene.setTouchAreaBindingOnActionDownEnabled(true);	
		for(int i = 0; i < bricks.length; i++)
		{
			
			bricks[i] = new Brick(0,(-500 *(i+1)),resourceManager.vbom, resourceManager.camera, physicsWorld);
			scene.attachChild(bricks[i]);
		}
		for(int k = 0; k < strawberries.length; k++)
		{
			strawberries[k] = new Strawberry(0,(-300 *(k+1)),resourceManager.vbom, camera, physicsWorld);
			scene.attachChild(strawberries[k]);
		}
    	
		scene.attachChild(player);  	
    	return scene;
    }
    public void onLoadComplete()
    {
    	this.mEngine.registerUpdateHandler(new IUpdateHandler() {

			public void onUpdate(float pSecondsElapsed) 
			{
				//here goes the game Logic
				if(!gameOver && !levelComplete) //so if the game is still running
				{
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
					System.exit(0);
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
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
            System.exit(0);	
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
        }
        return false; 
    }

    public EngineOptions onCreateEngineOptions()
    {
        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 480), this.camera);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }
	private void createPhysics()
	{
	    physicsWorld = new PhysicsWorld(new Vector2(0, -17), false); 
	    mEngine.registerUpdateHandler(physicsWorld);
	}
	
}
