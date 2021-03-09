/*
 * Copyright (c) 2020, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.javatar.loader;

import com.javatar.ParamDefinition;
import com.javatar.cs2.ScriptVarType;
import com.javatar.definition.DeserializeDefinition;
import com.javatar.input.InputStream;

public class ParamLoader implements DeserializeDefinition<ParamDefinition> {
    public ParamDefinition load(int id, byte[] data) {
        ParamDefinition def = new ParamDefinition();
        def.setId(id);
        InputStream b = new InputStream(data);

        for (; ; ) {
            int opcode = b.readUnsignedByte();

            switch (opcode) {
                case 0:
                    return def;
                case 1: {
                    int idx = b.readByte();
                    def.setType(ScriptVarType.forCharKey((char) idx));
                    break;
                }
                case 2:
                    def.setDefaultInt(b.readInt());
                    break;
                case 4:
                    def.setMembers(false);
                    break;
                case 5:
                    def.setDefaultString(b.readString());
                    break;
            }
        }
    }

    @Override
    public ParamDefinition deserialize(int id, byte[] b) {
        return load(id, b);
    }
}
