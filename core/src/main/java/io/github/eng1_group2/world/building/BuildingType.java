package io.github.eng1_group2.world.building;

import com.badlogic.gdx.graphics.Color;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.utils.Vec2;

public record BuildingType(String id, String name, Color color, Vec2 size) implements RegistryObject {
    @Override
    public String getId() {
        return this.id;
    }
}
