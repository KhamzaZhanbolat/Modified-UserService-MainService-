package MainService.MainService.service.impl;

import MainService.MainService.dto.LessonDto;
import MainService.MainService.entities.Chapter;
import MainService.MainService.entities.Lesson;
import MainService.MainService.mappers.LessonMapper;
import MainService.MainService.repositories.ChapterRepository;
import MainService.MainService.repositories.LessonRepository;
import MainService.MainService.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final LessonMapper lessonMapper;

    @Override
    public LessonDto getById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        if(lesson != null) {
            log.info("Get lesson: {}", id);
            return lessonMapper.toDto(lesson);
        }
        return null;
    }

    @Override
    public List<LessonDto> getAll() {
        log.info("Get all lessons");
        return lessonMapper.toDtoList(lessonRepository.findAll());
    }

    @Override
    public LessonDto create(LessonDto lessonDto) {
        Chapter chapter = chapterRepository.findById((long) lessonDto.getChapterId()).orElse(null);

        Lesson lesson = lessonMapper.toEntity(lessonDto);
        lesson.setCreatedAt(LocalDateTime.now());
        lesson.setChapter(chapter);

        log.info("Add lesson: {}", lesson);
        return lessonMapper.toDto(lessonRepository.save(lesson));
    }

    @Override
    public LessonDto update(Long id, LessonDto lessonDto) {
        Lesson lessonFromDb = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + id));

        lessonFromDb.setName(lessonDto.getName());
        lessonFromDb.setDescription(lessonDto.getDescription());
        lessonFromDb.setContent(lessonDto.getContent());
        lessonFromDb.setSequenceOrder(lessonDto.getSequenceOrder());
        lessonFromDb.setUpdatedAt(LocalDateTime.now());

        log.info("Update lesson: {}", lessonFromDb);

        return lessonMapper.toDto(lessonRepository.save(lessonFromDb));
    }


    @Override
    public String delete(Long id) {
        if(lessonRepository.existsById(id)) {
            lessonRepository.deleteById(id);
            log.info("Delete lesson: {}", id);
            return "Lesson deleted";
        }else{
            return "Lesson not found";
        }
    }
}
