package com.weeklyMenu.vendor.helper;

import java.util.UUID;

public interface IdGenerator {
    default String generateId() {
        return UUID.randomUUID().toString();
    }
}
