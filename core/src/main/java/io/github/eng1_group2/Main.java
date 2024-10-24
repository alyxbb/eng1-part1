package io.github.eng1_group2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.eng1_group2.registry.Registries;
import io.github.eng1_group2.utils.CodecAssetLoader;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.World;
import io.github.eng1_group2.world.building.BuildingType;


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
    private InputMultiplexer inputMultiplexer;


    @Override
    public void create() {
        this.assetManager = new AssetManager();
        CodecAssetLoader loader = new CodecAssetLoader();
        loader.prepare(this.assetManager);

        // Initialise and load all dynamic objects (building types, events, etc.)
        this.registries = new Registries();
        this.registries.loadAllFrom(loader);
        this.registries.freezeAll();

        for (BuildingType buildingType:this.registries.getBuildingTypes()){
            assetManager.load(buildingType.texturePath(), Texture.class);
        }
        assetManager.load("MiniWorldSprites/Ground/TexturedGrass.png",Texture.class);
        while (!assetManager.update()) {
            System.out.println("Loading assets...");
        }

        viewport = new ScreenViewport();

        ui = new UI(this);

        world = new World(this);
        var buildingTypes = getRegistries().getBuildingTypes();

        world.addBuilding(buildingTypes.get("house"), new Vec2(3, 4));
        world.addBuilding(buildingTypes.get("lecture_theatre"), new Vec2(0, 1));
        world.addBuilding(buildingTypes.get("cafe"), new Vec2(0, 3));

        inputMultiplexer = new InputMultiplexer(world.getStage(), ui.getStage());
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
}
