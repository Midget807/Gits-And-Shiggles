package net.midget807.gitsnshiggles.entity.client;

import java.util.ArrayList;
import java.util.List;

public class SchizophreniaManager {
    private static final List<SchizophreniaEntity> SCHIZOPHRENIA_ENTITIES = new ArrayList<>();

    public static void add(SchizophreniaEntity entity) {
        SCHIZOPHRENIA_ENTITIES.add(entity);
    }

    public static void tick() {
        SCHIZOPHRENIA_ENTITIES.removeIf(entity -> ++entity.age >= entity.maxAge);
    }

    public static List<SchizophreniaEntity> get() {
        return SCHIZOPHRENIA_ENTITIES;
    }


    public static void clear() {
        SCHIZOPHRENIA_ENTITIES.clear();
    }
}
