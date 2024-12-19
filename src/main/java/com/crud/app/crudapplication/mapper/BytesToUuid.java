package com.crud.app.crudapplication.mapper;

import java.nio.ByteBuffer;
import java.util.UUID;

public class BytesToUuid {

    public static UUID bytesToUuid(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        long mostSignificantBits = buffer.getLong();
        long leastSignificantBits = buffer.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

}
