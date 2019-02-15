package com.mygdx.game.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {

	private int numFootTouches;
	private Array<Body> bodiesToRemove;
	
	public MyContactListener() {
		super();
		bodiesToRemove = new Array<Body>();
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (fa == null || fb == null) return;
		
		if (fa.getUserData() != null && fa.getUserData().equals("foot")) numFootTouches++;
		if (fb.getUserData() != null && fb.getUserData().equals("foot")) numFootTouches++;
		if (fa.getUserData() != null && fa.getUserData().equals("crystal")) bodiesToRemove.add(fa.getBody());
		if (fb.getUserData() != null && fb.getUserData().equals("crystal")) bodiesToRemove.add(fb.getBody());
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (fa == null || fb == null) return;
		
		if (fa.getUserData() != null && fa.getUserData().equals("foot")) numFootTouches--;
		if (fb.getUserData() != null && fb.getUserData().equals("foot")) numFootTouches--;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
	
	public boolean footTouched() { return numFootTouches > 0; }
	public Array<Body> getBodiesToRemove() { return bodiesToRemove; }
}
