package com.crud.app.crudapplication.mapper;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidToBytes {

    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }

}
