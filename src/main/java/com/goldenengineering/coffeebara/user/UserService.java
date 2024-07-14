package com.goldenengineering.coffeebara.user;

import com.goldenengineering.coffeebara.common.enums.Role;
import com.goldenengineering.coffeebara.user.dto.request.CreateUserRequest;
import com.goldenengineering.coffeebara.user.dto.response.CreateUserResponse;
import com.goldenengineering.coffeebara.user.model.UserJpaEntity;
import com.goldenengineering.coffeebara.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        log.info("UserService createUser");

        UserJpaEntity user = UserJpaEntity.builder()
                .identifier(createUserRequest.id())
                .password(createUserRequest.password())
                .userName(createUserRequest.name())
                .latitude(createUserRequest.latitude())
                .longitude(createUserRequest.longitude())
                .role(Role.MANAGER)
                .build();

        Long userId = userRepository.save(user).getUserId();

        return CreateUserResponse.builder()
                .userId(userId)
                .build();
    }
}
