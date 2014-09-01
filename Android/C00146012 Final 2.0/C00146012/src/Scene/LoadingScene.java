package Scene;
import Manager.ResourceManager;
import Manager.SceneManager.SceneType;

import com.example.c00146012.BaseScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;


public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		setBackground(new Background(Color.WHITE));
		attachChild(new Text(400, 240, ResourceManager.font, "Loading...", vbom));
	}

	@Override
	public void onBackKeyPressed() {
		return;
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

}
