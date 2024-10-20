package io.github.eng1_group2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.eng1_group2.world.World;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private static final int VIEWPORT_WIDTH = 500;
    private static final int VIEWPORT_HEIGHT = 500;
    private Camera camera;
    private SpriteBatch batch;
    private Texture image;
    private World world;

    @Override
    public void create() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        world = new World();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT * (height / width));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    /*@Override
    public void resize(int width, int height) {
        camera.viewportWidth = VIEWPORT_WIDTH;
        camera.viewportHeight = VIEWPORT_HEIGHT * (height / width);
        camera.update();
    }*/

    @Override
    public void render() {
        camera.update();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        world.renderBuildings(camera);
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
