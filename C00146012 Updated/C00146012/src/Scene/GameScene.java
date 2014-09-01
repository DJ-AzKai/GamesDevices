package Scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import Manager.ResourceManager;
import Manager.SceneManager;
import Manager.SceneManager.SceneType;

import com.example.c00146012.BaseScene;
public class GameScene extends BaseScene {


	@Override
	public void createScene() {
		createBackground();	
		playAudio();
	}
	private void playAudio()
	{
		ResourceManager.menuMusic.stop();
        ResourceManager.gameMusic.play();
        ResourceManager.gameMusic.setLooping(true); 
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

}
