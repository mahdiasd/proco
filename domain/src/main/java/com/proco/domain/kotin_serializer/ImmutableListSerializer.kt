package com.proco.domain.kotin_serializer

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializer(forClass = ImmutableList::class)
class ImmutableListSerializer<T>(private val elementSerializer: KSerializer<T>) : KSerializer<ImmutableList<T>> {
    override val descriptor: SerialDescriptor =
        ListSerializer(elementSerializer).descriptor

    override fun serialize(encoder: Encoder, value: ImmutableList<T>) {
        ListSerializer(elementSerializer).serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): ImmutableList<T> {
        val list = ListSerializer(elementSerializer).deserialize(decoder)
        return list.toImmutableList()
    }
}