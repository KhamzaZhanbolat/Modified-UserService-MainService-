package MainService.MainService.service.impl;

import MainService.MainService.dto.CourseDto;
import MainService.MainService.entities.Course;
import MainService.MainService.mappers.CourseMapper;
import MainService.MainService.repositories.CourseRepository;
import MainService.MainService.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseDto getById(Long id) {
        log.info("Get course {}", id);
        Course courseEntity = courseRepository.findById(id).orElse(null);
        return courseMapper.toDto(courseEntity);
    }

    @Override
    public List<CourseDto> getAll() {
        log.info("Find all courses");
        return courseMapper.toDtoList(courseRepository.findAll());
    }

    @Override
    public boolean create(CourseDto course) {
        if (course == null) {
            log.info("Course DTO is null");
            return false;
        }
        Course courseEntity = courseMapper.toEntity(course);
        if (courseEntity == null) {
            log.info("Mapped entity is null");
            return false;
        }
        courseEntity.setCreatedAt(LocalDateTime.now());
        course.setCreatedAt(LocalDateTime.now());

        log.info("Add course {}", course);
        courseRepository.save(courseEntity);
        return true;
    }

    @Override
    public CourseDto update(Long id, CourseDto courseDto) {
        Course courseFromDb = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        courseFromDb.setName(courseDto.getName());
        courseFromDb.setDescription(courseDto.getDescription());
        courseFromDb.setUpdatedAt(LocalDateTime.now());

        log.info("Update course: {}", courseFromDb);

        return courseMapper.toDto(courseRepository.save(courseFromDb));
    }

    @Override
    public String delete(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            log.info("Delete course {}", id);
            return "Course deleted successfully";
        } else {
            log.info("Course {} not found", id);
            return "Course not found with id: " + id;
        }
    }
}
