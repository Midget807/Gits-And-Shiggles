package net.midget807.gitsnshiggles.entity.client;

import java.util.ArrayList;
import java.util.List;

public class SchizophreniaManager {
    private final List<SchizophreniaEntity> SCHIZOPHRENIA_ENTITIES = new ArrayList<>();

    public void add(SchizophreniaEntity entity) {
        SCHIZOPHRENIA_ENTITIES.add(entity);
    }

    public void tick() {
        SCHIZOPHRENIA_ENTITIES.removeIf(entity -> ++entity.age >= entity.maxAge);
    }

    public List<SchizophreniaEntity> get() {
        return SCHIZOPHRENIA_ENTITIES;
    }


    public void clear() {
        SCHIZOPHRENIA_ENTITIES.clear();
    }
}
