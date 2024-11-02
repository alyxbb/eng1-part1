package io.github.eng1_group2.world;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.eng1_group2.Main;
import io.github.eng1_group2.UI;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.building.*;
import io.github.eng1_group2.world.feature.Feature;
import io.github.eng1_group2.world.feature.FeatureConfig;

import java.util.ArrayList;
import java.util.List;


public class World extends InputAdapter {
    private final WorldConfig config;
    private final List<AbstractBuilding> buildings;
    private final List<Feature> features;
    private final Main main;
    private final Table map;
    private final Stage stage;
    private int gridUnit;

    // TODO: move this to worldConfig
    private int balance = 10000000;

    public World(Main main, WorldConfig config) {
        this.main = main;
        this.config = config;
        this.stage = new Stage(this.main.getViewport());
        this.buildings = new ArrayList<>();
        this.features = new ArrayList<>();

        TextureRegionDrawable backgroundTexture = new TextureRegionDrawable(config.backgroundTexture().getTextureRegion(main.getAssetManager(), new Vec2(16, 16)));

        // create the map, it's a table of buttons, so we can detect where the user clicked
        this.map = new Table();
        this.map.setDebug(UI.DEBUG, true);
        this.map.setPosition(0, 0);
        this.stage.addActor(this.map);

        for (int i = 0; i < config.mapSize().y(); i++) {
            for (int j = 0; j < config.mapSize().x(); j++) {
                Button button = new Button(backgroundTexture);
                this.map.add(button);
                int gridX = j;
                int gridY = config.mapSize().y() - i - 1;
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        try {
                            addBuilding(main.getUi().getSelectedBuilding(), new Vec2(gridX, gridY));
                        } catch (BuildException e) {
                            main.getUi().setWarningMessage(e.getMessage());
                        }
                    }
                });
            }
            this.map.row();
        }

        // this needs to be at the bottom otherwise features render behind the background. ask alyx for more information
        for (FeatureConfig feature : config.features()) {
            this.addFeature(feature);
        }
    }

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        this.stage.act(delta);
        this.stage.draw();
    }

    public void resize() {

        // gridUnit is the size of each unit on the grid. we make the grid as large as it can be while making each cell square
        gridUnit = Math.round(Math.min((main.getViewport().getWorldWidth()) / config.mapSize().x(), main.getViewport().getWorldHeight() / config.mapSize().y()));

        this.map.setWidth(gridUnit * config.mapSize().x());
        this.map.setHeight(gridUnit * config.mapSize().y());
        // despite the map having its size set, for some reason it decides the table inside the table object shouldn't
        // take up all the space it's been given unless you tell the cells to have the right size
        for (Cell<?> cell : this.map.getCells()) {
            cell.width(gridUnit);
            cell.height(gridUnit);
        }
        for (AbstractBuilding building : this.buildings) {
            building.resize();
        }
        for (Feature feature : this.features) {
            feature.resize();
        }
    }

    public void addBuilding(BuildingType buildingType, Vec2 location) throws BuildException {
        if (buildingType.cost() > balance) {
            throw new BuildException("Insufficient funds");
        }
        if (location.x() < 0 || location.y() < 0) {
            throw new BuildException("location must be positive");
        }
        if (location.x() + buildingType.size().x() > config.mapSize().x() || location.y() + buildingType.size().y() > config.mapSize().y()) {
            throw new BuildException("building would extend outside grid");
        }
        IncompleteBuilding building = new IncompleteBuilding(buildingType, location, this.main);
        for (AbstractBuilding testBuilding : buildings) {
            if (testBuilding.overlaps(building)) {
                throw new BuildException("building would intersect with a building");
            }
        }
        for (Feature feature : features) {
            if (building.overlaps(feature)) {
                throw new BuildException("building would intersect with a feature");
            }
        }
        buildings.add(building);
        // register it with the timer, so it can become a building after its construction time has passed
        main.getTimer().registerBuilding(building);
        this.stage.addActor(building);
        balance -= buildingType.cost();
    }

    public void completeBuilding(IncompleteBuilding incompleteBuilding) {
        // delete the old incomplete building
        buildings.remove(incompleteBuilding);
        incompleteBuilding.remove();
        main.getTimer().unregisterBuilding(incompleteBuilding);

        //create the building with the details from incomplete building
        Building building = new Building(incompleteBuilding.getType(), new Vec2((int) incompleteBuilding.getBoundingBox().x, (int) incompleteBuilding.getBoundingBox().y), main);
        buildings.add(building);
        this.stage.addActor(building);
    }

    public void addFeature(FeatureConfig featureConfig) {
        if (featureConfig.position().x() < 0 || featureConfig.position().y() < 0) {
            throw new IllegalArgumentException("location must be positive");
        }
        if (featureConfig.position().x() + featureConfig.size().x() > config.mapSize().x() || featureConfig.position().y() + featureConfig.size().y() > config.mapSize().y()) {
            throw new IllegalArgumentException("feature would extend outside grid");
        }
        Feature feature = new Feature(featureConfig, this);
        features.add(feature);
        this.stage.addActor(feature);
    }

    public Vector2 gridSquareToScreenPos(Vec2 gridPos) {
        if (gridPos.x() < 0 || gridPos.y() < 0) {
            throw new IllegalArgumentException("gridPos must be positive");
        }
        if (config.mapSize().x() <= gridPos.x() || config.mapSize().y() <= gridPos.y()) {
            throw new IllegalArgumentException("gridPos is too large");
        }
        return new Vector2(gridPos.x() * gridUnit, gridPos.y() * gridUnit);
    }

    public Main getMain() {
        return main;
    }

    public int getGridUnit() {
        return gridUnit;
    }

    public Stage getStage() {
        return stage;
    }

    public int getBalance() {
        return balance;
    }

    public WorldConfig getConfig() {
        return config;
    }
}
