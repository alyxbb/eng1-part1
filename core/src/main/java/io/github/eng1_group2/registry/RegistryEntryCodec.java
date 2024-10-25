package io.github.eng1_group2.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

public class RegistryEntryCodec<T extends RegistryObject> implements Codec<T> {
    private final String registryName;

    public RegistryEntryCodec(String registryName) {
        this.registryName = registryName;
    }

    @Override
    public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
        if (ops instanceof RegistryOps<?, ?> registryOps) {
            DataResult<Pair<String, T1>> idResult = Codec.STRING.decode(ops, input);
            return idResult.flatMap((id) -> {
                DataResult<T> object = registryOps.getFromRegistry(this.registryName, id.getFirst());
                return object.map((o) -> new Pair<>(o, id.getSecond()));
            });
        } else {
            return DataResult.error(() -> "RegistryEntryCodec can only be used with RegistryOps");
        }
    }

    @Override
    public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
        if (ops instanceof RegistryOps<?, ?> registryOps) {
            if (!registryOps.existsInRegistry(this.registryName, input)) {
                return DataResult.error(() -> "`" + input.id() + "` does not exist in registry `" + this.registryName + "`");
            }
            return Codec.STRING.encode(input.id(), ops, prefix);
        } else {
            return DataResult.error(() -> "RegistryEntryCodec can only be used with RegistryOps");
        }
    }

    @Override
    public String toString() {
        return "RegistryEntryCodec[" + super.toString() + "]";
    }
}
