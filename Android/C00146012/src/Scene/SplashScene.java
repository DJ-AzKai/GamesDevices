package Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

import Manager.ResourceManager;
import Manager.SceneManager.SceneType;

import com.example.c00146012.BaseScene;

public class SplashScene extends BaseScene
{
	private Sprite splash;
	@Override
	public void createScene()
	{
		setBackground(new Background(Color.CYAN));
		splash = new Sprite(100, 0, ResourceManager.splash_region, vbom)
		{
		    @Override
		    protected void preDraw(GLState pGLState, Camera pCamera) 
		    {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};
		      
		splash.setScale(1.5f);
		splash.setPosition(100, 150);
		attachChild(splash);
	}
	
	@Override
	public void onBackKeyPressed()
	{
	
	}
	
	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_SPLASH;
	}
	
	@Override
	public void disposeScene()
	{
		splash.detachSelf();
	    splash.dispose();
	    this.detachSelf();
	    this.dispose();
	}
}
