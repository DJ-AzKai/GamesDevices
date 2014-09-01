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
	
	private Music menuMusic;
	private Music gameMusic;
	//game
	private Music absorb;
	private Music splat;
	private Music crash;
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
        // TODO (Since we did not create any textures for game scene yet)
    	//set textures to null. And static variables too.
    	//.unload() for all texture atlas'
    }
    private void loadMenuGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Menu/");
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "Background.png");
    	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
    	options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");
    	       
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
    
    private void loadMenuAudio()
    {
    	SoundFactory.setAssetBasePath("mfx/");
    	 try
         {
         	menuMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"MenuBackground.WAV");        	
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
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Game/");
    	gameAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	strawBerry_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "Strawberry.png");
    	brick_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "Brick.png");
    	game_background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "GameBackground.png");
    	player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameAtlas, activity, "HenryRight.png", 4, 1);
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
    	SoundFactory.setAssetBasePath("mfx/");
    	 try
         {
         	gameMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"GameBackground.wav"); 
         	splat = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"Splat.ogg");
         	crash = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"Smash.ogg");
         	absorb = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"absorb.wav");
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
    
    public void loadSplashScreen()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
    	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "Logo.png", 0, 0);
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

