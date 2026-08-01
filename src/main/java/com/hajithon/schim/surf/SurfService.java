package com.hajithon.schim.surf;

import com.hajithon.schim.surf.dto.SurfPage;

import java.util.UUID;

public interface SurfService {
    SurfPage getFeed(UUID userId, String cursor, int limit, boolean revisit);
}
