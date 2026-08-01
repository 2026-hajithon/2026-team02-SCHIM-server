package com.hajithon.schim.guestbook;

import java.util.List;
import java.util.UUID;

public interface SurfPassService {
    void recordPasses(UUID userId, List<Long> guestbookIds);
}
