package io.github.eng1_group2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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

    public Registries getRegistries() {
        return registries;
    }

    public Viewport getViewport() {
        return viewport;
    }

    private Viewport viewport;
    private SpriteBatch batch;
    private Texture image;
    private World world;
    private UI ui;
    private InputMultiplexer inputMultiplexer;

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
        viewport = new ScreenViewport();

        ui = new UI(this);
        world = new World(this);
        inputMultiplexer = new InputMultiplexer(world,ui.getStage());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ui.resize();
        world.resize();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        world.render();
        ui.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public UI getUi() {
        return ui;
    }
}
