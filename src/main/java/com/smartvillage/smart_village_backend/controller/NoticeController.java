package com.smartvillage.smart_village_backend.controller;

import com.smartvillage.smart_village_backend.entity.Notice;
import com.smartvillage.smart_village_backend.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/notices")
@CrossOrigin
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }
}
