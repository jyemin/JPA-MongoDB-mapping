/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.omm.array.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;

import java.sql.Struct;

/**
 * A {@link Codec} meant to be created each time when it is used for it encodes or decodes embeddable entity field annotated with
 * {@link Struct} individually.
 *
 * @author Nathan Xu
 * @since 1.0.0
 */
public class StructCodec<T> implements Codec<T> {

    private final EmbeddableMappingType embeddableMappingType;
    private final Class<T> clazz;

    public StructCodec(final EmbeddableMappingType embeddableMappingType, final Class<T> clazz) {
        this.embeddableMappingType = embeddableMappingType;
        this.clazz = clazz;
    }

    @Override
    public T decode(final BsonReader reader, final DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(final BsonWriter writer, final T value, final EncoderContext encoderContext) {

    }

    @Override
    public Class<T> getEncoderClass() {
        return clazz;
    }
}
