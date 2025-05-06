package MainService.MainService.controllers;

import MainService.MainService.dto.LessonDto;
import MainService.MainService.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonApi {

    private final LessonService lessonService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long id) {
        log.info("Get lesson: {}", id);
        return  ResponseEntity.ok(lessonService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<List<LessonDto>> getAllLessons() {
        log.info("Get all lessons");
        return ResponseEntity.ok(lessonService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto lesson) {
        log.info("Create lesson: {}", lesson.getName());
        return  ResponseEntity.ok(lessonService.create(lesson));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable Long id, @RequestBody LessonDto lesson) {
        log.info("Update lesson: {}", lesson.getName());
        return  ResponseEntity.ok(lessonService.update(id, lesson));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        log.info("Delete lesson: {}", id);
        return ResponseEntity.ok(lessonService.delete(id));
    }
}
