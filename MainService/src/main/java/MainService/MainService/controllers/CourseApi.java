package MainService.MainService.controllers;

import MainService.MainService.dto.CourseDto;
import MainService.MainService.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseApi {

    private final CourseService courseService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        log.info("CourseDto for id {}", id);
        CourseDto courseDto = courseService.getById(id);
        return ResponseEntity.ok(courseDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        log.info("Courses list");
        return ResponseEntity.ok(courseService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        log.info("Create course {}", courseDto.getName());
        boolean status = courseService.create(courseDto);
        if (status) {
            log.debug("Course created {}", courseDto.getName());
            return ResponseEntity.ok(courseDto);
        }else {
            log.error("Course creation failed");
            return null;
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        log.info("Update course {}", courseDto.getName());
        return ResponseEntity.ok(courseService.update(id, courseDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        log.info("Delete course {}", id);
        return ResponseEntity.ok(courseService.delete(id));
    }
}
