package com.smartvillage.smart_village_backend.controller;

import com.smartvillage.smart_village_backend.dto.RaiseIssueRequest;
import com.smartvillage.smart_village_backend.entity.User;
import com.smartvillage.smart_village_backend.service.IssueService;
import com.smartvillage.smart_village_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin
public class IssueController {

    private final IssueService issueService;
    private final UserService userService;

    public IssueController(IssueService issueService,
                           UserService userService) {
        this.issueService = issueService;
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> raiseIssue(
            @AuthenticationPrincipal String firebaseUid,
            @RequestPart("data") @Valid RaiseIssueRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        User user = userService.getCurrentUser(firebaseUid);
        return ResponseEntity.ok(
                issueService.raiseIssueWithImage(user, request, image)
        );
    }

    @GetMapping("/my")
    public ResponseEntity<?> myIssues(
            @AuthenticationPrincipal String firebaseUid
    ) {
        User user = userService.getCurrentUser(firebaseUid);
        return ResponseEntity.ok(issueService.getMyIssues(user));
    }
}
