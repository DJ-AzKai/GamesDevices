package Scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import Manager.ResourceManager;
import Manager.SceneManager;
import Manager.SceneManager.SceneType;

import com.example.c00146012.BaseScene;

import org.andengine.engine.camera.Camera;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{

	@Override
	public void createScene() {
		
		
		createBackground();
	    createMenuChildScene();
	}

	private void createBackground()
	{
		attachChild(new Sprite(100, 0, ResourceManager.menu_background_region, vbom)
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
	public void onBackKeyPressed() {
		disposeScene();		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		 System.exit(0);
	}
	
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;

	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(50, 0);
	    
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, ResourceManager.play_region, vbom), 1.2f, 1);
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, ResourceManager.options_region, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    menuChildScene.addMenuItem(optionsMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
//	    playMenuItem.setScale(0.5f, 1.0f);
//	    optionsMenuItem.setScale(0.5f, 1.0f);
	    
	    playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY());
	    optionsMenuItem.setPosition(optionsMenuItem.getX(), optionsMenuItem.getY() + 110);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
	    switch(pMenuItem.getID())
	    {
	        case MENU_PLAY:
	            //Load Game Scene!
	        	//tell the activity to start updating
	            SceneManager.getInstance().loadGameScene(engine);
	            SceneManager.getInstance().setGameSet(true);
	            return true;
	        case MENU_OPTIONS:
	            return true;
	        default:
	            return false;
	    }
	}

}
