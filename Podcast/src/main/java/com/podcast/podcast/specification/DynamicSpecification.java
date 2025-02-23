package com.podcast.podcast.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


public class DynamicSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = -2100431743963401062L;

    private final Map<String, Object> criteriaMap = new HashMap<>();

    public DynamicSpecification(Map<String, Object> criteriaMap) {
        this.criteriaMap.putAll(criteriaMap);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;

        for (Map.Entry<String, Object> entry : criteriaMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            if (Objects.isNull(fieldValue) || (fieldValue instanceof String && ((String) fieldValue).isBlank())) {
                continue;
            }

            if (fieldName.contains("dateFrom") && fieldValue instanceof LocalDate) {
                fromDate = LocalDateTime.of((LocalDate) fieldValue, LocalTime.MIN);
            } else if (fieldName.contains("dateTo") && fieldValue instanceof LocalDate) {
                toDate = LocalDateTime.of((LocalDate) fieldValue, LocalTime.MAX);

            } else if (fieldName.equals("startDate") && fieldValue instanceof LocalDateTime) {
                LocalDateTime dateValue = (LocalDateTime) fieldValue;
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), dateValue));
            } else if (fieldName.equals("endDate") && fieldValue instanceof LocalDateTime) {
                LocalDateTime dateValue = (LocalDateTime) fieldValue;
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), dateValue));
            } else {

                Path<?> path = getPath(root, fieldName);

                if (fieldValue instanceof String) {
                    predicates.add(criteriaBuilder.like(path.as(String.class), "%" + fieldValue + "%"));
                } else {
                    predicates.add(path.in(List.of(fieldValue)));
                }
            }
        }

        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate)) {
            predicates.add(criteriaBuilder.between(root.get("createdAt"), fromDate, toDate));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Path<?> getPath(Path<?> root, String fieldName) {
        if (fieldName.contains(".")) {
            String[] parts = fieldName.split("\\.");
            Path<?> path = root;
            for (int i = 0; i < parts.length; i++) {
                if (i < parts.length - 1) {
                    if (path instanceof Root) {
                        path = ((Root<?>) path).join(parts[i], JoinType.LEFT);
                    } else if (path instanceof Join) {
                        path = ((Join<?, ?>) path).join(parts[i], JoinType.LEFT);
                    }
                } else {
                    path = path.get(parts[i]);
                }
            }
            return path;
        } else {
            return root.get(fieldName);
        }
    }

}
