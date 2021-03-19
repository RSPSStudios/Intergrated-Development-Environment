package com.javatar.saver;

import com.javatar.definition.SerializableDefinition;
import com.javatar.osrs.definitions.impl.ModelDefinition;
import com.javatar.osrs.definitions.impl.models.ModelPatch;
import com.javatar.output.OutputStream;

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 * This model saver only patches the model if the face colors or face textures change.
 */

public class ModelSaver implements SerializableDefinition<ModelDefinition> {
    @Override
    public byte[] serialize(ModelDefinition def) {
        OutputStream out = new OutputStream();
        out.writeBytes(def.rawModelData);

        for (ModelPatch patch : def.colorModelPatches) {
            out.setOffset(patch.getOffset());
            out.writeShort(def.faceColors[patch.getIndex()]);
        }

        for (ModelPatch patch : def.textureModelPatches) {
            out.setOffset(patch.getOffset());
            out.writeShort(def.faceTextures[patch.getIndex()]);
        }

        return out.getArray();
    }
}
