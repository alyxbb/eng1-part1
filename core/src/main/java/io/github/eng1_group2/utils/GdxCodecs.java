package io.github.eng1_group2.utils;

import com.badlogic.gdx.graphics.Color;
import com.mojang.serialization.Codec;

public final class GdxCodecs {
    public static final Codec<Color> COLOUR = Codec.STRING.xmap(Color::valueOf, Color::toString);

    // Prevent initialisation
    private GdxCodecs() {
    }
}
