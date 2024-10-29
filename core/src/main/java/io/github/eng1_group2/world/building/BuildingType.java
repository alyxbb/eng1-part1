package io.github.eng1_group2.world.building;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.registry.TexturedRegistryObject;
import io.github.eng1_group2.utils.Vec2;

public record BuildingType(String id, String name, String texturePath, Vec2 size,
                           Vec2 textureOrigin, int cost,
                           float buildTime) implements RegistryObject, TexturedRegistryObject.Simple {
    public static final String REGISTRY_NAME = "building_type";

    public static final Codec<BuildingType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(BuildingType::id),
        Codec.STRING.fieldOf("name").forGetter(BuildingType::name),
        Codec.STRING.fieldOf("texture").forGetter(BuildingType::texturePath),
        Vec2.CODEC.fieldOf("size").forGetter(BuildingType::size),
        Vec2.CODEC.fieldOf("textureOrigin").forGetter(BuildingType::textureOrigin),
        Codec.INT.fieldOf("cost").forGetter(BuildingType::cost),
        Codec.FLOAT.fieldOf("buildTime").forGetter(BuildingType::buildTime)
    ).apply(instance, BuildingType::new));
}
