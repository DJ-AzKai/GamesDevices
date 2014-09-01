package Manager;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

import com.example.c00146012.GameActivity;
import com.example.c00146012.Level2Activity;

//Credit to Typodermic Fonts for the font.
public class ResourceManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    public static final ResourceManager INSTANCE = new ResourceManager();
    
    public Engine engine;
    public GameActivity activity;
    public Level2Activity activity2;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    public static ITextureRegion splash_region;
	private BitmapTextureAtlas splashTextureAtlas;
	
	public static ITextureRegion menu_background_region;
	public static ITextureRegion play_region;
	public static ITextureRegion options_region;
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	
	public static ITiledTextureRegion player_region;
	public static ITextureRegion strawBerry_region;
	public static ITextureRegion brick_region;
	public static ITextureRegion game_background;
	private BuildableBitmapTextureAtlas gameAtlas;
	
	public static Font font;
	
	//---------------------------------------------
    // AUDIO
    //---------------------------------------------
	
	public static Music menuMusic;
	public static Music gameMusic;
	//game
	public static Music absorb;
	public static Music splat;
	public static Music smash;
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    public void loadMenuResources()
    {
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }
    public VertexBufferObjectManager Vbom()
    {
    	return vbom;
    }
    public void unloadMenuTextures()
    {
        menuTextureAtlas.unload();
    }
        
    public void loadMenuTextures()
    {
        menuTextureAtlas.load();
    }
    
    public void loadGameResources()
    {
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }
    private void loadMenuFonts()
    {
        FontFactory.setAssetBasePath("gfx/Font/");
        final Texture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "plasmati.ttf", 30, true, Color.BLACK, 1, Color.WHITE);
        font.load();
    }
    public void unloadGameTextures()
    {
        // TODO
    	//set textures to null. And static variables too.
    	//.unload() for all texture atlas'
    }
    private void loadMenuGraphics()
    {
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "gfx/Menu/Background.png");
    	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "gfx/Menu/play.png");
    	options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "gfx/Menu/options.png");
    	       
    	try 
    	{
    	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.menuTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    }
    public static ITiledTextureRegion getPlayerRegion()
    {
    	return player_region;
    }
    private void loadMenuAudio()
    {
    	 try
         {
         	menuMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"mfx/MenuBackground.WAV");
         }
         catch (IllegalStateException e) 
         {
 			e.printStackTrace();			
 		} 
         
         catch (IOException e) 
 		{
 			e.printStackTrace();
 		}
     }
       

    private void loadGameGraphics()
    {
    	gameAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	strawBerry_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "gfx/Game/Strawberry.png");
    	brick_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "gfx/Game/Brick.png");
    	game_background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "gfx/Game/GameBackground.png");
    	player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameAtlas, activity, "gfx/Game/HenryRight.png", 4, 1);
    	try 
    	{
    	    this.gameAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.gameAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    }
    
    private void loadGameFonts()
    {
        
    }
    
    private void loadGameAudio()
    {
    	 try
         {
         	gameMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"mfx/GameBackground.wav"); 
         	splat = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"mfx/Splat.ogg");
         	smash = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"mfx/Smash.ogg");
         	absorb = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"mfx/absorb.wav");
         }
         catch (IllegalStateException e) 
         {
 			e.printStackTrace();			
 		} 
         
         catch (IOException e) 
 		{
 			e.printStackTrace();
 		}
    }
    public static Music[] gameSounds()
    {
    	Music[] m = new Music[4];
    	m[0] = gameMusic;
    	m[1] = splat;
    	m[2] = smash;
    	m[3] = absorb;
    	return m;
    }
    public static Music BGMusic()
    {
    	return menuMusic;
    }
    public void loadSplashScreen()
    {
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
    	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "gfx/Logo.png", 0, 0);
    	splashTextureAtlas.load();
    }
    
    public void unloadSplashScreen()
    {
    	splashTextureAtlas.unload();
    	splash_region = null;
    }
    
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourceManager getInstance()
    {
        return INSTANCE;
    }
	public static void prepareManager(Engine mEngine,
			Level2Activity l2A, Camera camera2,
			VertexBufferObjectManager vbom2) {
		
		getInstance().engine = mEngine;
        getInstance().activity2 = l2A;
        getInstance().camera = camera2;
        getInstance().vbom = vbom2;
	}
}

