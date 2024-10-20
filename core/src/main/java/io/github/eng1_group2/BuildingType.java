package io.github.eng1_group2;

import com.badlogic.gdx.graphics.Color;

public class BuildingType {
    private final String name;
    private final Color color;

    public BuildingType(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
