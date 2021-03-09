package com.javatar.definition;

@FunctionalInterface
public interface DeserializeDefinition<T extends Definition> {

    T deserialize(final int id, byte[] b);

}
