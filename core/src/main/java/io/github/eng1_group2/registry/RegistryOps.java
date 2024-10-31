package io.github.eng1_group2.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import java.util.stream.Stream;

public class RegistryOps<T, Wrapped extends DynamicOps<T>> implements DynamicOps<T> {
    private final Wrapped wrapped;
    private final Registries registries;

    public RegistryOps(Wrapped wrapped, Registries registries) {
        this.wrapped = wrapped;
        this.registries = registries;
    }

    @SuppressWarnings("unchecked")
    public <O extends RegistryObject> DataResult<O> getFromRegistry(String registryName, String id) {
        Registry<O> registry = (Registry<O>) this.registries.getByName(registryName);
        if (!registry.contains(id)) {
            return DataResult.error(() -> String.format("`%s` does not exist in registry `%s`", id, registryName));
        }
        return DataResult.success(registry.get(id));
    }

    @SuppressWarnings("unchecked")
    public <O extends RegistryObject> boolean existsInRegistry(String registryName, O o) {
        Registry<O> registry = (Registry<O>) this.registries.getByName(registryName);
        return registry.contains(o);
    }

    @Override
    public T empty() {
        return this.wrapped.empty();
    }

    @Override
    public <U> U convertTo(DynamicOps<U> outOps, T input) {
        return this.wrapped.convertTo(outOps, input);
    }

    @Override
    public DataResult<Number> getNumberValue(T input) {
        return this.wrapped.getNumberValue(input);
    }

    @Override
    public T createNumeric(Number i) {
        return this.wrapped.createNumeric(i);
    }

    @Override
    public DataResult<String> getStringValue(T input) {
        return this.wrapped.getStringValue(input);
    }

    @Override
    public T createString(String value) {
        return this.wrapped.createString(value);
    }

    @Override
    public DataResult<T> mergeToList(T list, T value) {
        return this.wrapped.mergeToList(list, value);
    }

    @Override
    public DataResult<T> mergeToMap(T map, T key, T value) {
        return this.wrapped.mergeToMap(map, key, value);
    }

    @Override
    public DataResult<Stream<Pair<T, T>>> getMapValues(T input) {
        return this.wrapped.getMapValues(input);
    }

    @Override
    public T createMap(Stream<Pair<T, T>> map) {
        return this.wrapped.createMap(map);
    }

    @Override
    public DataResult<Stream<T>> getStream(T input) {
        return this.wrapped.getStream(input);
    }

    @Override
    public T createList(Stream<T> input) {
        return this.wrapped.createList(input);
    }

    @Override
    public T remove(T input, String key) {
        return this.wrapped.remove(input, key);
    }
}
