package lesson;

import MainService.MainService.dto.LessonDto;
import MainService.MainService.entities.Chapter;
import MainService.MainService.entities.Lesson;
import MainService.MainService.mappers.LessonMapper;
import MainService.MainService.repositories.ChapterRepository;
import MainService.MainService.repositories.LessonRepository;
import MainService.MainService.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonServiceImpl lessonServiceImpl;

    @Mock
    private ChapterRepository chapterRepository;

    @Test
    public void testGetLessonById_Found() {
        Long lessonId = 1L;

        Lesson lessonEntity = new Lesson();
        lessonEntity.setId(lessonId);
        lessonEntity.setName("Intro to Java");

        LessonDto expectedDto = new LessonDto();
        expectedDto.setId(lessonId);
        expectedDto.setName("Intro to Java");

        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lessonEntity));
        when(lessonMapper.toDto(lessonEntity)).thenReturn(expectedDto);

        LessonDto result = lessonServiceImpl.getById(lessonId);

        assertNotNull(result);
        assertEquals("Intro to Java", result.getName());
    }

    @Test
    public void testGetAllLessons() {
        Lesson l1 = new Lesson(); l1.setId(1L); l1.setName("Lesson 1");
        Lesson l2 = new Lesson(); l2.setId(2L); l2.setName("Lesson 2");

        LessonDto d1 = new LessonDto(); d1.setId(1L); d1.setName("Lesson 1");
        LessonDto d2 = new LessonDto(); d2.setId(2L); d2.setName("Lesson 2");

        when(lessonRepository.findAll()).thenReturn(List.of(l1, l2));
        when(lessonMapper.toDtoList(List.of(l1, l2))).thenReturn(List.of(d1, d2));

        List<LessonDto> result = lessonServiceImpl.getAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testCreateLesson() {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setName("New Lesson");
        lessonDto.setChapterId(1);

        Chapter chapter = new Chapter();
        chapter.setId(1L);

        Lesson lessonEntity = new Lesson();
        lessonEntity.setName("New Lesson");

        Lesson savedLesson = new Lesson();
        savedLesson.setId(1L);
        savedLesson.setName("New Lesson");

        LessonDto expectedDto = new LessonDto();
        expectedDto.setId(1L);
        expectedDto.setName("New Lesson");

        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(lessonMapper.toEntity(any(LessonDto.class))).thenReturn(lessonEntity);
        when(lessonRepository.save(any())).thenReturn(savedLesson);
        when(lessonMapper.toDto(savedLesson)).thenReturn(expectedDto);

        LessonDto result = lessonServiceImpl.create(lessonDto);

        assertNotNull(result);
        assertEquals("New Lesson", result.getName());
    }

    @Test
    public void testUpdateLesson() {
        Long lessonId = 1L;

        LessonDto updateDto = new LessonDto();
        updateDto.setName("Updated Lesson");
        updateDto.setContent("Updated content");
        updateDto.setSequenceOrder(2);

        Lesson existing = new Lesson();
        existing.setId(lessonId);
        existing.setName("Old");

        Lesson updated = new Lesson();
        updated.setId(lessonId);
        updated.setName("Updated Lesson");
        updated.setContent("Updated content");
        updated.setSequenceOrder(2);

        LessonDto expectedDto = new LessonDto();
        expectedDto.setId(lessonId);
        expectedDto.setName("Updated Lesson");

        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(existing));
        when(lessonRepository.save(existing)).thenReturn(updated);
        when(lessonMapper.toDto(updated)).thenReturn(expectedDto);

        LessonDto result = lessonServiceImpl.update(lessonId, updateDto);

        assertEquals("Updated Lesson", result.getName());
    }

    @Test
    public void testDeleteLesson_Success() {
        Long id = 1L;

        when(lessonRepository.existsById(id)).thenReturn(true);

        String result = lessonServiceImpl.delete(id);

        assertEquals("Lesson deleted", result);
        verify(lessonRepository).deleteById(id);
    }

    @Test
    public void testDeleteLesson_NotFound() {
        Long id = 999L;

        when(lessonRepository.existsById(id)).thenReturn(false);

        String result = lessonServiceImpl.delete(id);

        assertEquals("Lesson not found", result);
        verify(lessonRepository, never()).deleteById(any());
    }
}
