package com.smartvillage.smart_village_backend.service;

import com.smartvillage.smart_village_backend.entity.Notice;
import com.smartvillage.smart_village_backend.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // CREATE
    public Notice createNotice(String title, String message) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setMessage(message);
        return noticeRepository.save(notice);
    }

    // READ (USER)
    public List<Notice> getAllNotices() {
        return noticeRepository.findAllByOrderByCreatedAtDesc();
    }

    // DELETE (ADMIN)
    public void deleteNotice(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new RuntimeException("Notice not found");
        }
        noticeRepository.deleteById(id);
    }
}
