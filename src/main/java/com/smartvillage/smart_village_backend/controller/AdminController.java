package com.smartvillage.smart_village_backend.controller;

import com.smartvillage.smart_village_backend.dto.CreateNoticeRequest;
import com.smartvillage.smart_village_backend.dto.UpdateIssueStatusRequest;
import com.smartvillage.smart_village_backend.service.IssueService;
import com.smartvillage.smart_village_backend.service.NoticeService;
import com.smartvillage.smart_village_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final IssueService issueService;
    private final NoticeService noticeService;
    private final UserService userService;

    public AdminController(
            IssueService issueService,
            NoticeService noticeService,
            UserService userService
    ) {
        this.issueService = issueService;
        this.noticeService = noticeService;
        this.userService = userService;
    }


    @GetMapping("/issues/stats")
    public ResponseEntity<?> issueStats(
            @AuthenticationPrincipal String firebaseUid
    ) {
        userService.assertAdmin(firebaseUid);
        return ResponseEntity.ok(issueService.getIssueStats());
    }


    @GetMapping("/issues")
    public ResponseEntity<?> allIssues(
            @AuthenticationPrincipal String firebaseUid
    ) {
        userService.assertAdmin(firebaseUid);
        return ResponseEntity.ok(issueService.getAllIssues());
    }


    @PutMapping("/issues/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateIssueStatusRequest request,
            @AuthenticationPrincipal String firebaseUid
    ) {
        userService.assertAdmin(firebaseUid);
        return ResponseEntity.ok(
                issueService.updateIssueStatus(id, request.getStatus())
        );
    }


    @PostMapping("/notices")
    public ResponseEntity<?> publishNotice(
            @RequestBody CreateNoticeRequest request,
            @AuthenticationPrincipal String firebaseUid
    ) {
        userService.assertAdmin(firebaseUid);
        return ResponseEntity.ok(
                noticeService.createNotice(
                        request.getTitle(),
                        request.getMessage()
                )
        );
    }


    @DeleteMapping("/notices/{id}")
    public ResponseEntity<?> deleteNotice(
            @PathVariable Long id,
            @AuthenticationPrincipal String firebaseUid
    ) {
        userService.assertAdmin(firebaseUid);
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }

}
