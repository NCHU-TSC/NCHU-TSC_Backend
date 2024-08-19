package app.nchu.tsc.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.nchu.tsc.models.Redirecting;
import app.nchu.tsc.repositories.RedirectingRepository;

@Service
public class RedirectingService {
    
    @Autowired
    private RedirectingRepository redirectingRepository;

    @Autowired
    private SystemVariableService systemVariableService;

    public Redirecting generateRedirecting(String href, Long expire_seconds) {
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(
            expire_seconds == null ? Long.parseLong(systemVariableService.get("redirecting_expire_seconds")) : expire_seconds
        );

        Redirecting redirecting = Redirecting.builder()
            .href(href)
            .expires(expiresAt)
            .used(false)
            .build();

        return redirectingRepository.save(redirecting);
    }

    public boolean isExpired(Redirecting redirecting) {
        return redirecting.getExpires().isBefore(LocalDateTime.now());
    }

    public Redirecting getRedirecting(String id) {
        return redirectingRepository.findById(id).orElse(null);
    }


}
