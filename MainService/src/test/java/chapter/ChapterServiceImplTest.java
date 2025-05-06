package chapter;

import MainService.MainService.dto.ChapterDto;
import MainService.MainService.entities.Chapter;
import MainService.MainService.entities.Course;
import MainService.MainService.exception.ResourceNotFoundException;
import MainService.MainService.mappers.ChapterMapper;
import MainService.MainService.repositories.ChapterRepository;
import MainService.MainService.repositories.CourseRepository;
import MainService.MainService.service.impl.ChapterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChapterServiceImplTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @InjectMocks
    private ChapterServiceImpl chapterService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void testGetChapterById_Found(){
        Long chapterId = 1L;

        Chapter chapterEntity = new Chapter();
        chapterEntity.setId(chapterId);
        chapterEntity.setName("Test Chapter");

        ChapterDto expectedDto = new ChapterDto();
        expectedDto.setId(chapterId);
        expectedDto.setName("Test Chapter");

        when(chapterRepository.findById(chapterId)).thenReturn(java.util.Optional.of(chapterEntity));
        when(chapterMapper.toDto(chapterEntity)).thenReturn(expectedDto);

        ChapterDto result = chapterService.getById(chapterId);

        assertNotNull(result);
        assertEquals("Test Chapter", result.getName());
        verify(chapterRepository).findById(chapterId);
    }

    @Test
    public void testGetChapterById_NotFound() {
        Long invalidId = 999L;

        when(chapterRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());

        ChapterDto result = chapterService.getById(invalidId);

        assertNull(result);
        verify(chapterRepository).findById(invalidId);
    }

    @Test
    public void testGetAllChapters() {
        Chapter chapter1 = new Chapter();
        chapter1.setId(1L);
        chapter1.setName("Chapter 1");

        Chapter chapter2 = new Chapter();
        chapter2.setId(2L);
        chapter2.setName("Chapter 2");

        List<Chapter> chapterEntities = List.of(chapter1, chapter2);

        ChapterDto dto1 = new ChapterDto();
        dto1.setId(1L);
        dto1.setName("Chapter 1");

        ChapterDto dto2 = new ChapterDto();
        dto2.setId(2L);
        dto2.setName("Chapter 2");

        List<ChapterDto> expectedDtos = List.of(dto1, dto2);

        when(chapterRepository.findAll()).thenReturn(chapterEntities);
        when(chapterMapper.toDtoList(chapterEntities)).thenReturn(expectedDtos);

        List<ChapterDto> result = chapterService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Chapter 1", result.get(0).getName());
        verify(chapterRepository).findAll();
    }

    @Test
    public void testCreateChapter_Success() {
        Long courseId = 1L;

        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setName("Chapter 1");
        chapterDto.setDescription("Chapter 1111");
        chapterDto.setCourseId(courseId);

        Course mockCourse = new Course();
        mockCourse.setId(courseId);
        mockCourse.setName("Java Course");

        Chapter chapterEntity = new Chapter();
        chapterEntity.setName("Chapter 1");
        chapterEntity.setDescription("Chapter 1111");
        chapterEntity.setCourse(mockCourse);

        ChapterDto expectedDto = new ChapterDto();
        expectedDto.setId(1L);
        expectedDto.setName("Chapter 1");
        expectedDto.setDescription("Chapter 1111");

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(mockCourse));
        when(chapterMapper.toEntity(any(ChapterDto.class))).thenReturn(chapterEntity);
        when(chapterRepository.save(any())).thenReturn(chapterEntity);
        when(chapterMapper.toDto(chapterEntity)).thenReturn(expectedDto);

        ChapterDto result = chapterService.create(chapterDto);

        assertNotNull(result);
        assertEquals("Chapter 1", result.getName());
        verify(courseRepository).findById(courseId);
        verify(chapterRepository).save(chapterEntity);
    }

    @Test
    public void testUpdateChapter_Success() {
        Long chapterId = 1L;

        ChapterDto updateDto = new ChapterDto();
        updateDto.setName("Updated Name");
        updateDto.setDescription("Updated Desc");
        updateDto.setSequenceOrder(2);

        Chapter existingChapter = new Chapter();
        existingChapter.setId(chapterId);
        existingChapter.setName("Old Name");
        existingChapter.setDescription("Old Desc");
        existingChapter.setSequenceOrder(1);

        Chapter updatedChapter = new Chapter();
        updatedChapter.setId(chapterId);
        updatedChapter.setName("Updated Name");
        updatedChapter.setDescription("Updated Desc");
        updatedChapter.setSequenceOrder(2);

        ChapterDto expectedDto = new ChapterDto();
        expectedDto.setId(chapterId);
        expectedDto.setName("Updated Name");
        expectedDto.setDescription("Updated Desc");
        expectedDto.setSequenceOrder(2);

        when(chapterRepository.findById(chapterId)).thenReturn(java.util.Optional.of(existingChapter));
        when(chapterRepository.save(existingChapter)).thenReturn(updatedChapter);
        when(chapterMapper.toDto(updatedChapter)).thenReturn(expectedDto);

        ChapterDto result = chapterService.update(chapterId, updateDto);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals(2, result.getSequenceOrder());
        verify(chapterRepository).findById(chapterId);
        verify(chapterRepository).save(existingChapter);
    }

    @Test
    public void testDeleteChapter_Success() {
        Long chapterId = 1L;

        when(chapterRepository.existsById(chapterId)).thenReturn(true);

        String result = chapterService.delete(chapterId);

        assertEquals("Chapter deleted successfully", result);
        verify(chapterRepository).deleteById(chapterId);
    }

    @Test
    public void testDeleteChapter_NotFound() {
        Long chapterId = 999L;

        when(chapterRepository.existsById(chapterId)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> chapterService.delete(chapterId)
        );

        assertEquals("Chapter not found with id: " + chapterId, exception.getMessage());
        verify(chapterRepository, never()).deleteById(any());
    }
}
