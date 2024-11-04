package io.github.eng1_group2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.eng1_group2.registry.Registries;
import io.github.eng1_group2.utils.loader.CodecAssetLoader;
import io.github.eng1_group2.world.World;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private AssetManager assetManager;
    private Registries registries;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture image;
    private World world;
    private UI ui;
    private Timer timer;

    @Override
    public void create() {
        this.assetManager = new AssetManager();
        this.registries = new Registries();

        CodecAssetLoader loader = new CodecAssetLoader(this.registries);
        loader.prepare(this.assetManager);

        // Initialise and load all dynamic objects (building types, events, etc.)
        this.registries.getDynamic().loadAllFrom(loader);
        this.registries.getDynamic().loadAllTextures(this.assetManager);
        this.registries.getDynamic().freezeAll();

        String worldConfig = System.getProperty("unisim.world_config", "default");
        this.viewport = new ScreenViewport();
        this.ui = new UI(this);
        this.world = new World(this, this.registries.getDynamic().getWorldConfigs().get(worldConfig));
        this.timer = new Timer(world);

        Gdx.input.setInputProcessor(new InputMultiplexer(world.getStage(), ui.getStage()));
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
        timer.update();
        world.render();
        ui.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public Registries getRegistries() {
        return registries;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public World getWorld() {
        return world;
    }

    public UI getUi() {
        return ui;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Timer getTimer() {
        return timer;
    }
}
