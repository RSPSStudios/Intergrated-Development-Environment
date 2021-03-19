package com.javatar.saver;

import com.javatar.definition.SerializableDefinition;
import com.javatar.osrs.definitions.impl.InventoryDefinition;
import com.javatar.output.OutputStream;


public class InventorySaver implements SerializableDefinition<InventoryDefinition> {
    @Override
    public byte[] serialize(InventoryDefinition def) {
        OutputStream out = new OutputStream();

        if (def.getSize() != 0) {
            out.writeByte(2);
            out.writeShort(def.getSize());
        }

        out.writeByte(0);
        return out.flip();
    }
}
