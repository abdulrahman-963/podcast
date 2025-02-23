package com.podcast.podcast.service.mapper;


import com.podcast.podcast.model.dto.SubscriptionDTO;
import com.podcast.podcast.model.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper extends GenericMapper<SubscriptionEntity, SubscriptionDTO> {
}
