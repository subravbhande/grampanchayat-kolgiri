package com.smartvillage.smart_village_backend.service;

import com.smartvillage.smart_village_backend.dto.RaiseIssueRequest;
import com.smartvillage.smart_village_backend.entity.Issue;
import com.smartvillage.smart_village_backend.entity.User;
import com.smartvillage.smart_village_backend.repository.IssueRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final CloudinaryService cloudinaryService;

    public IssueService(
            IssueRepository issueRepository,
            CloudinaryService cloudinaryService
    ) {
        this.issueRepository = issueRepository;
        this.cloudinaryService = cloudinaryService;
    }

    // ✅ RAISE ISSUE
    public Issue raiseIssueWithImage(
            User user,
            RaiseIssueRequest request,
            MultipartFile image
    ) {
        Issue issue = new Issue();
        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        issue.setCategory(request.getCategory());
        issue.setLatitude(request.getLatitude());
        issue.setLongitude(request.getLongitude());
        issue.setUser(user);
        issue.setStatus("OPEN");

        if (image != null && !image.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(image);
            issue.setImageUrl(imageUrl);
        }

        return issueRepository.save(issue);
    }

    // ✅ USER HISTORY
    public List<Issue> getMyIssues(User user) {
        return issueRepository.findByUserOrderByCreatedAtDesc(user);
    }

    // ✅ ADMIN ALL ISSUES
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    // ✅ UPDATE STATUS
    public Issue updateIssueStatus(Long id, String status) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setStatus(status);
        return issueRepository.save(issue);
    }

    // ✅ ADMIN DASHBOARD STATS (IMPORTANT)
    public Map<String, Long> getIssueStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", issueRepository.count());
        stats.put("open", issueRepository.countByStatus("OPEN"));
        stats.put("resolved", issueRepository.countByStatus("RESOLVED"));
        return stats;
    }
}
