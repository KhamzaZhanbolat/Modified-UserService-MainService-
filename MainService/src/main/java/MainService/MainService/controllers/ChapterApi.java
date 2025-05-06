package MainService.MainService.controllers;

import MainService.MainService.dto.ChapterDto;
import MainService.MainService.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/chapter")
@RequiredArgsConstructor
public class ChapterApi {

    private final ChapterService chapterService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<ChapterDto> getChapter(@PathVariable Long id) {
        log.info("Getting chapter: {}", id);
        return ResponseEntity.ok(chapterService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<List<ChapterDto>> getAllChapters() {
        log.info("Getting all chapters");
        return ResponseEntity.ok(chapterService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ChapterDto> createChapter(@RequestBody ChapterDto chapterDto) {
        log.info("Creating chapter: {}", chapterDto.getName());
        log.debug("Chapter: {}", chapterDto);
        return ResponseEntity.ok(chapterService.create(chapterDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ChapterDto> updateChapter(
            @PathVariable Long id,
            @RequestBody ChapterDto chapterDto) {
        log.info("Updating chapter: {}", id);
        return ResponseEntity.ok(chapterService.update(id, chapterDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<String> deleteChapter(@PathVariable Long id) {
        log.info("Deleting chapter: {}", id);
        return ResponseEntity.ok(chapterService.delete(id));
    }
}
