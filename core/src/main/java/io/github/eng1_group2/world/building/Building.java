package io.github.eng1_group2.world.building;

import io.github.eng1_group2.Main;
import io.github.eng1_group2.utils.Vec2;

public class Building extends AbstractBuilding {


    public Building(BuildingType type, Vec2 origin, Main main) {
        super(
            type,
            origin,
            main,
            type.texture().getTextureRegion(main.getAssetManager(), new Vec2(type.size().x() * 16, type.size().y() * 16))
        );
    }
}
