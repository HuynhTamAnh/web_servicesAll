package com.example.ss10.repository;

import com.example.ss10.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByAccountIdOrderByCreatedAtDesc(UUID accountId );
}