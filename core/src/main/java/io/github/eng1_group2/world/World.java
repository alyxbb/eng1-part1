package io.github.eng1_group2.world;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.eng1_group2.registry.Registries;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.building.Building;

import java.util.ArrayList;
import java.util.List;


public class World {
    private final Vec2 gridSize = new Vec2(10, 10);
    private final List<Building> buildings;

    public World(Registries registries) {
        this.buildings = new ArrayList<>();
        this.buildings.add(new Building(registries.getBuildingTypes().get("house"), new Vec2(3, 4)));
        this.buildings.add(new Building(registries.getBuildingTypes().get("lecture_theatre"), new Vec2(0, 1)));
        this.buildings.add(new Building(registries.getBuildingTypes().get("cafe"), new Vec2(0, 2)));
    }

    public void render(Viewport viewport) {
        int gridUnit = Math.round(Math.min((viewport.getWorldWidth()) / gridSize.x(), viewport.getWorldHeight() / gridSize.y()));

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.7f, 0.7f, 0.7f, 1f);
        // draw grid lines
        // skip the first one so we only do internal lines
        for (int i = 1; i < gridSize.x(); i++) {
            shapeRenderer.line(i * gridUnit, 0, i * gridUnit, gridSize.y() * gridUnit);
        }
        for (int i = 1; i < gridSize.y(); i++) {
            shapeRenderer.line(0, i * gridUnit, gridSize.x() * gridUnit, i * gridUnit);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (var building : this.buildings) {
            shapeRenderer.setColor(building.getType().color());
            shapeRenderer.rect(building.getOrigin().x() * gridUnit, building.getOrigin().y() * gridUnit, gridUnit * building.getType().size().x(), gridUnit * building.getType().size().y());
        }
        shapeRenderer.end();
    }
}
