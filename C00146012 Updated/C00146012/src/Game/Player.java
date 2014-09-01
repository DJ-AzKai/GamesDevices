package Game;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;



import Manager.ResourceManager;

public class Player extends AnimatedSprite {
	private Body body;
	private int score = 0;
	private int lives = 3;
	private boolean playerDead = false;
	public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
    {
		super(pX, pY, ResourceManager.player_region, vbo);
		createPhysics(camera, physicsWorld);
    }
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{        
	    body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

	    body.setUserData("player");
	    body.setFixedRotation(true);
	    
	    physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
	    {
	        @Override
	        public void onUpdate(float pSecondsElapsed)
	        {
	            super.onUpdate(pSecondsElapsed);
	            camera.onUpdate(0.1f);             
	            body.setLinearVelocity(new Vector2(5, body.getLinearVelocity().y));	            
	        }
	    });
	}
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		// read old x,y here
		this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, 750);
		setRunning();
		return true;
	}
	public void setRunning()
	{
		final long[] PLAYER_ANIMATE = new long[] { 100, 100, 100, 100 };        
	    animate(PLAYER_ANIMATE, 0, 3, true);
	}
	public int getScore()
	{
		return score;
	}
	public boolean getPlayerDead()
	{
		return playerDead;
	}
	public void onDie()
	{
		if(lives > 0)
		{
			lives--;			
		}
		else
		{
			playerDead = true;
		}
	}
}
