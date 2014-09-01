package Game;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import Manager.ResourceManager;

public class Strawberry extends Sprite{

	private Body body;
	Random rand = new Random();
	public Strawberry(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
    {
		super(pX, pY, ResourceManager.strawBerry_region, vbo);
		createPhysics(camera, physicsWorld);
    }
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{        
	    body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

	    body.setUserData("stawberry");
	    body.setFixedRotation(true);
	    
	    physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
	    {
	        @Override
	        public void onUpdate(float pSecondsElapsed)
	        {
	            super.onUpdate(pSecondsElapsed);            
	            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, 8.91f)); 
	            if(this.getBody().getPosition().y >= 800)
	            {
	            	int newX = rand.nextInt(550) + 50;
	            	setPos(newX);
	            }	            
	        }
	    });
	}
	protected void setPos(int pX)
	{
		this.setPosition(pX, -350);
	}

}
