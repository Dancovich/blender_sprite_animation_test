package br.com.dancovich.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class BlenderSpriteAnimation extends ApplicationAdapter {

    OrthographicCamera camera;
    ModelBatch modelBatch;
    AssetManager assetManager;

    ModelInstance critterModelInstance;
    AnimationController critterAnimationController;
    Environment lighting;

    static final float WORLD_HEIGHT = 5f;

    @Override
    public void create() {
        modelBatch = new ModelBatch();
        assetManager = new AssetManager();

        assetManager.load("critter.g3dj", Model.class);
        assetManager.finishLoading();

        critterModelInstance = new ModelInstance((Model) assetManager.get("critter.g3dj"));
        critterModelInstance.getMaterial("body_material").set(new BlendingAttribute(true, 1f));
        critterModelInstance.getMaterial("hat_material").set(new BlendingAttribute(true, 1f));

        critterAnimationController = new AnimationController(critterModelInstance);
        critterAnimationController.setAnimation("idle", -1);

        lighting = new Environment();
        lighting.add(new DirectionalLight().set(1f, 1f, 1f, 0f, 0f, -1f));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        final float worldWidth = (width / (float) height) * WORLD_HEIGHT;
        camera = new OrthographicCamera(worldWidth, WORLD_HEIGHT);
        camera.near = 1f;
        camera.far = 10f;
        camera.position.set(0f, 1.5f, 5f);
        camera.lookAt(0f, 1.5f, 0f);
        camera.update();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        critterAnimationController.update(Gdx.graphics.getDeltaTime());

        modelBatch.begin(camera);
        modelBatch.render(critterModelInstance, lighting);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        modelBatch.dispose();
    }
}
