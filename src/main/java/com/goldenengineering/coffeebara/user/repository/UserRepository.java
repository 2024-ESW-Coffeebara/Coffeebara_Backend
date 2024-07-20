package com.goldenengineering.coffeebara.user.repository;

import com.goldenengineering.coffeebara.user.model.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {
    boolean existsByIdentifier(String identifier);
    boolean existsByIdentifierAndPassword(String identifier, String password);
}
