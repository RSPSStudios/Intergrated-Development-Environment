package com.javatar.osrs.definitions;

@FunctionalInterface
public interface DeserializeDefinition<T extends Definition> {

    T deserialize(final int id, byte[] b);

}
