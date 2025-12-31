package com.smartvillage.smart_village_backend.repository;

import com.smartvillage.smart_village_backend.entity.Issue;
import com.smartvillage.smart_village_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByUserOrderByCreatedAtDesc(User user);

    long countByStatus(String status);
}
