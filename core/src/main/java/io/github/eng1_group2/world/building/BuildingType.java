package io.github.eng1_group2.world.building;

import com.badlogic.gdx.graphics.Color;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.utils.GdxCodecs;
import io.github.eng1_group2.utils.Vec2;

public record BuildingType(String id, String name, Color colour, Vec2 size) implements RegistryObject {
    public static final Codec<BuildingType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(BuildingType::id),
        Codec.STRING.fieldOf("name").forGetter(BuildingType::name),
        GdxCodecs.COLOUR.fieldOf("colour").forGetter(BuildingType::colour),
        Vec2.CODEC.fieldOf("size").forGetter(BuildingType::size)
    ).apply(instance, BuildingType::new));

    @Override
    public String getId() {
        return this.id;
    }
}
