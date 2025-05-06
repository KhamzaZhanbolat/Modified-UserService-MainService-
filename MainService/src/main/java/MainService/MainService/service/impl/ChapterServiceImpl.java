package MainService.MainService.service.impl;

import MainService.MainService.dto.ChapterDto;
import MainService.MainService.entities.Chapter;
import MainService.MainService.entities.Course;
import MainService.MainService.exception.ResourceNotFoundException;
import MainService.MainService.mappers.ChapterMapper;
import MainService.MainService.repositories.ChapterRepository;
import MainService.MainService.repositories.CourseRepository;
import MainService.MainService.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public ChapterDto getById(Long id) {
        log.info("Get chapter: {}", id);
        Chapter chapterEntity = chapterRepository.findById(id).orElse(null);
        return chapterMapper.toDto(chapterEntity);
    }

    @Override
    public List<ChapterDto> getAll() {
        log.info("Get all chapters");
        return chapterMapper.toDtoList(chapterRepository.findAll());
    }

    @Override
    public ChapterDto create(ChapterDto chapterDto) {
        Course course = courseRepository.findById(chapterDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + chapterDto.getCourseId()));

        Chapter chapter = chapterMapper.toEntity(chapterDto);
        chapter.setCourse(course);
        chapter.setCreatedAt(LocalDateTime.now());
        log.info("Add chapter: {}", chapter);

        return chapterMapper.toDto(chapterRepository.save(chapter));
    }

    @Override
    public ChapterDto update(Long id, ChapterDto chapterDto) {
        Chapter chapterFromDb = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found with id: " + id));

        chapterFromDb.setName(chapterDto.getName());
        chapterFromDb.setDescription(chapterDto.getDescription());
        chapterFromDb.setSequenceOrder(chapterDto.getSequenceOrder());
        chapterFromDb.setUpdatedAt(LocalDateTime.now());

        log.info("Update chapter: {}", chapterFromDb);

        return chapterMapper.toDto(chapterRepository.save(chapterFromDb));
    }

    @Override
    public String delete(Long id) {
        if(chapterRepository.existsById(id)) {
            chapterRepository.deleteById(id);
            log.info("Delete chapter: {}", id);
            return "Chapter deleted successfully";
        }else{
            log.info("Delete chapter with id: {}", id);
            throw new ResourceNotFoundException("Chapter not found with id: " + id);
        }
    }
}
