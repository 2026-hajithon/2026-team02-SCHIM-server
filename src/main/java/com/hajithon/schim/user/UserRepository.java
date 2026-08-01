package com.hajithon.schim.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

// 여기서 JPA 의존. 헥사고날, 클린 아키텍처까지는 고려하지 않음.
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByAnonymousToken(UUID anonymousToken);
}
