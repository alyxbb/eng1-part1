package io.github.eng1_group2.world;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import io.github.eng1_group2.utils.BuildException;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.building.Building;
import io.github.eng1_group2.world.building.BuildingType;
import io.github.eng1_group2.world.feature.Feature;
import io.github.eng1_group2.world.feature.FeatureType;

import java.util.ArrayList;
import java.util.List;


public class World extends InputAdapter {
    private final Vec2 gridSize = new Vec2(20, 20);
    private final List<Building> buildings;
    private final List<Feature> features;
    private final Main main;
    private final Table map;
    private final Stage stage;
    private int gridUnit;
    private int balance = 10000000;

    public World(Main main) {

        this.main = main;

        this.stage = new Stage(this.main.getViewport());

        //TODO: get textures from json
        Texture texture = main.getAssetManager().get("MiniWorldSprites/Ground/TexturedGrass.png", Texture.class);
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(new TextureRegion(texture, 16, 0, 16, 16));

        this.map = new Table();
        this.map.setDebug(UI.DEBUG, true);
        for (int i = 0; i < gridSize.y(); i++) {
            for (int j = 0; j < gridSize.x(); j++) {
                Button button = new Button(textureRegionDrawable);
                this.map.add(button);
                int gridX = j;
                int gridY = gridSize.y() - i - 1;
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        try {
                            addBuilding(main.getUi().getSelectedBuilding(), new Vec2(gridX, gridY));
                        } catch (BuildException e) {
                            main.getUi().SetWarningMessage(e.getMessage());
                        }
                    }
                });
            }
            this.map.row();
        }
        this.stage.addActor(this.map);

        this.features = new ArrayList<>();
        this.buildings = new ArrayList<>();
    }


    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        this.stage.act(delta);
        this.stage.draw();
    }

    public void resize() {
        gridUnit = Math.round(Math.min((main.getViewport().getWorldWidth()) / gridSize.x(), main.getViewport().getWorldHeight() / gridSize.y()));
        this.map.setPosition(0, 0);
        this.map.setWidth(gridUnit * gridSize.x());
        this.map.setHeight(gridUnit * gridSize.y());
        for (Cell cell : this.map.getCells()) {
            cell.width(gridUnit);
            cell.height(gridUnit);
        }
        for (Building building : this.buildings) {
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
        if (location.x() + buildingType.size().x() > gridSize.x() || location.y() + buildingType.size().y() > gridSize.y()) {
            throw new BuildException("building would extend outside grid");
        }
        Building building = new Building(buildingType, location, this.main);
        for (Building testBuilding : buildings) {
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
        this.stage.addActor(building);
        System.out.println("placed building");
        balance -= buildingType.cost();
    }

    public void addFeature(FeatureType featureType, Vec2 location, Vec2 size) {
        if (location.x() < 0 || location.y() < 0) {
            throw new IllegalArgumentException("location must be positive");
        }
        if (location.x() + size.x() > gridSize.x() || location.y() + size.y() > gridSize.y()) {
            throw new IllegalArgumentException("feature would extend outside grid");
        }
        Feature feature = new Feature(featureType, location, size, main);
        features.add(feature);
        this.stage.addActor(feature);
    }

    public Vector2 gridSquareToScreenPos(Vec2 gridPos) {
        if (gridPos.x() < 0 || gridPos.y() < 0) {
            throw new IllegalArgumentException("gridpos must be positive");
        }
        if (gridSize.x() <= gridPos.x() || gridSize.y() <= gridPos.y()) {
            throw new IllegalArgumentException("gridpos is too large");
        }
        Vector2 screenPos = new Vector2(gridPos.x() * gridUnit, gridPos.y() * gridUnit);
        return screenPos;
    }


    public Vec2 screenPosToGridSquare(Vector2 screenPos) {
        if (screenPos.x < 0 || screenPos.y < 0) {
            throw new IllegalArgumentException("screenpos must be positive");
        }
        Vec2 gridPos = new Vec2((int) screenPos.x / gridUnit, (int) screenPos.y / gridUnit);
        if (gridPos.x() >= gridSize.x() || gridPos.y() >= gridSize.y()) {
            throw new IllegalArgumentException("position is not in the grid");
        }
        return gridPos;
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
}
