package com.hajithon.schim.guestbook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurfPassServiceImpl implements SurfPassService{
    private static final int MAX_BATCH_SIZE = 50;

    private final GuestbookRepository guestbookRepository;
    private final GuestbookPassRepository guestbookPassRepository;

    @Override
    public void recordPasses(UUID userId, List<Long> guestbookIds) {
        if (guestbookIds == null || guestbookIds.isEmpty()) {
            return;
        }
        List<Long> ids = guestbookIds.stream()
                .distinct()
                .limit(MAX_BATCH_SIZE)
                .toList();
        List<Guestbook> validGuestbooks = guestbookRepository.findAllById(ids).stream()
                .filter(g -> g.getDeletedAt() == null)
                .filter(g -> !g.getUserId().equals(userId))
                .toList();

        for (Guestbook guestbook : validGuestbooks) {
            if (!guestbookPassRepository.existsByGuestbookIdAndUserId(guestbook.getId(), userId)) {
                guestbookPassRepository.save(GuestbookPass.create(guestbook.getId(), userId));
            }
        }
    }
}
