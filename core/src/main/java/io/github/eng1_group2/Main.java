package io.github.eng1_group2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.eng1_group2.registry.Registries;
import io.github.eng1_group2.world.World;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private Registries registries;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture image;
    private World world;
    private UI ui;

    @Override
    public void create() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // Initialise and load all dynamic objects (building types, events, etc.)
        this.registries = new Registries();
        this.registries.loadAll();
        this.registries.freezeAll();

        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        world = new World(this.registries);
        viewport = new ScreenViewport();
        ui = new UI(viewport);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ui.resize();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        world.render(viewport);
        ui.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
