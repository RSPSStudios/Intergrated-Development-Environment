package com.javatar.definition;

@FunctionalInterface
public interface SerializableDefinition<T extends Definition> {

    byte[] serialize(T def);

}
