package io.github.eng1_group2.registry;

import com.mojang.serialization.Codec;
import io.github.eng1_group2.utils.loader.CodecAssetLoader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A registry is an id->object map for a specific type of object.
 * <p>
 * Registries can be frozen by calling {@link Registry#freeze()}, which prevents modification once they have been initialised.
 *
 * @see Registries
 * @param <T> The type of object stored in this registry. Must implement {@link RegistryObject}
 */
public class Registry<T extends RegistryObject> implements Iterable<T> {
    private final String name;
    private final Map<String, T> contents;
    private boolean frozen = false;

    public Registry(String name) {
        this.name = name;
        this.contents = new HashMap<>();
    }

    public boolean contains(String id) {
        return this.contents.containsKey(id);
    }

    public boolean contains(T object) {
        return this.contents.containsValue(object);
    }

    public T get(String id) {
        if (!this.contents.containsKey(id)) {
            throw new IllegalArgumentException("`" + id + "` not in registry `" + this.name + "`");
        }
        return this.contents.get(id);
    }

    public T register(T object) {
        if (this.frozen) {
            throw new IllegalStateException("`" + this.name + "` is frozen");
        }
        String id = object.id();
        if (this.contents.containsKey(id)) {
            throw new IllegalArgumentException("`" + id + "` is already registered in `" + this.name + "`");
        }
        this.contents.put(id, object);
        return object;
    }

    public void freeze() {
        if (this.frozen) {
            throw new IllegalStateException("`" + this.name + "` is frozen");
        }
        this.frozen = true;
    }

    @Override
    public Iterator<T> iterator() {
        return contents.values().iterator();
    }

    public String getName() {
        return name;
    }

    public static class Dynamic<T extends RegistryObject> extends Registry<T> {
        private final Codec<T> codec;

        public Dynamic(String name, Codec<T> codec) {
            super(name);
            this.codec = codec;
        }

        public void loadFrom(CodecAssetLoader loader) {
            List<T> items = loader.findByType(this.getName(), this.codec);
            for (var item : items) {
                this.register(item);
            }
        }
    }
}
