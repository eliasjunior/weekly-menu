package main.java.com.weeklyMenu.generator;

import java.util.UUID;

public interface IdGenerator {
    default String generateId() {
        return UUID.randomUUID().toString();
    }
}
