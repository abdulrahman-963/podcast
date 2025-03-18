package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.UserDTO;
import com.podcast.podcast.model.entity.UserEntity;
import com.podcast.podcast.model.search_criteria.UserSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.UserRepository;
import com.podcast.podcast.service.generic.CrudService;
import com.podcast.podcast.service.generic.DeletionBackupService;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.service.mapper.UserMapper;
import com.podcast.podcast.specification.DynamicSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements CrudService<UserEntity, UserDTO, UserSearchCriteria> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final DeletionBackupService<UserEntity> deletionBackupService;

    @Override
    public BaseRepository<UserEntity, Long> getRepository() {
        return userRepository;
    }

    @Override
    public DeletionBackupService<UserEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public GenericMapper<UserEntity, UserDTO> getMapper() {
        return userMapper;
    }

    @Override
    public DynamicSpecification<UserEntity> getSpecification(UserSearchCriteria bsc) {
        log.debug("UserSearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("username", bsc.getUsername());
            criteriaMap.put("email", bsc.getEmail());
            criteriaMap.put("role", bsc.getRole());
            criteriaMap.put("isActive", bsc.isActive());
        }

        return new DynamicSpecification<>(criteriaMap);
    }
}

