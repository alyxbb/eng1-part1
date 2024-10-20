package io.github.eng1_group2.world;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.eng1_group2.world.building.Building;
import io.github.eng1_group2.world.building.BuildingType;
import io.github.eng1_group2.utils.Vec2;

import java.util.HashMap;


public class World {
    private final Vec2 gridSize = new Vec2(10, 10);
    private final HashMap<Vec2, Building> buildings;

    public World() {
        this.buildings = new HashMap<>();
        this.buildings.put(new Vec2(2, 3), new Building(new BuildingType("house", Color.RED)));
        this.buildings.put(new Vec2(0, 1), new Building(new BuildingType("lecture_theatre", Color.BLUE)));
        this.buildings.put(new Vec2(0, 2), new Building(new BuildingType("cafe", Color.GREEN)));
    }

    public void renderBuildings(Camera camera) {
        int gridUnit = Math.round(Math.min(camera.viewportWidth / gridSize.getX(), camera.viewportHeight / gridSize.getY()));

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.7f, 0.7f, 0.7f, 1f);
        // draw grid lines
        for (int i = 0; i < gridSize.getX(); i++) {
            shapeRenderer.line(i * gridUnit, 0, i * gridUnit, gridSize.getY() * gridUnit);
        }
        for (int i = 0; i < gridSize.getY(); i++) {
            shapeRenderer.line(0, i * gridUnit, gridSize.getX() * gridUnit, i * gridUnit);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (var entry : this.buildings.entrySet()) {
            Vec2 coords = entry.getKey();
            Building building = entry.getValue();
            shapeRenderer.setColor(building.getType().getColor());
            shapeRenderer.rect(coords.getX() * gridUnit, coords.getY() * gridUnit, gridUnit, gridUnit);
        }
        shapeRenderer.end();
    }
}
