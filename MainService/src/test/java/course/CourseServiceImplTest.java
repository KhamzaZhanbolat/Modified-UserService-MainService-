package course;

import MainService.MainService.dto.CourseDto;
import MainService.MainService.entities.Course;
import MainService.MainService.mappers.CourseMapper;
import MainService.MainService.repositories.CourseRepository;
import MainService.MainService.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    public void testGetCourseById_Found() {
        Long courseId = 1L;

        Course courseEntity = new Course();
        courseEntity.setId(courseId);
        courseEntity.setName("Test Course");

        CourseDto expectedDto = new CourseDto();
        expectedDto.setId(courseId);
        expectedDto.setName("Test Course");

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(courseEntity));
        when(courseMapper.toDto(courseEntity)).thenReturn(expectedDto);

        CourseDto result = courseService.getById(courseId);

        assertNotNull(result);
        assertEquals("Test Course", result.getName());
        verify(courseRepository).findById(courseId);
    }


    @Test
    public void testGetCourseById_NotFound() {
        Long invalidId = 999L;

        when(courseRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());

        CourseDto result = courseService.getById(invalidId);

        assertNull(result);
        verify(courseRepository).findById(invalidId);
    }

    @Test
    public void testGetAllCourses() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Course 1");

        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Course 2");

        List<Course> courseEntities = List.of(course1, course2);

        CourseDto dto1 = new CourseDto();
        dto1.setId(1L);
        dto1.setName("Course 1");

        CourseDto dto2 = new CourseDto();
        dto2.setId(2L);
        dto2.setName("Course 2");

        List<CourseDto> expectedDtos = List.of(dto1, dto2);

        when(courseRepository.findAll()).thenReturn(courseEntities);
        when(courseMapper.toDtoList(courseEntities)).thenReturn(expectedDtos);

        List<CourseDto> result = courseService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Course 1", result.get(0).getName());
        verify(courseRepository).findAll();
    }

    @Test
    public void testCreateCourse_Success() {

        CourseDto courseDto = new CourseDto();
        courseDto.setName("Java Course");
        courseDto.setDescription("Learn Java");

        Course courseEntity = new Course();
        courseEntity.setName("Java Course");
        courseEntity.setDescription("Learn Java");

        when(courseMapper.toEntity(any(CourseDto.class))).thenReturn(courseEntity);
        when(courseRepository.save(any())).thenReturn(courseEntity);

        boolean result = courseService.create(courseDto);

        assertTrue(result);
        verify(courseRepository, times(1)).save(courseEntity);
    }

    @Test
    public void testCreateCourse_NullEntity() {

        boolean result = courseService.create(null);

        assertFalse(result);
        verify(courseRepository, never()).save(any());
    }

    @Test
    public void testUpdateCourse_Success() {
        Long courseId = 1L;

        CourseDto courseDto = new CourseDto();
        courseDto.setName("Updated Name");
        courseDto.setDescription("Updated Description");

        Course existingCourse = new Course();
        existingCourse.setId(courseId);
        existingCourse.setName("Old Name");
        existingCourse.setDescription("Old Desc");

        Course updatedCourse = new Course();
        updatedCourse.setId(courseId);
        updatedCourse.setName("Updated Name");
        updatedCourse.setDescription("Updated Description");

        CourseDto expectedDto = new CourseDto();
        expectedDto.setId(courseId);
        expectedDto.setName("Updated Name");
        expectedDto.setDescription("Updated Description");

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(existingCourse));
        when(courseRepository.save(any())).thenReturn(updatedCourse);
        when(courseMapper.toDto(updatedCourse)).thenReturn(expectedDto);

        CourseDto result = courseService.update(courseId, courseDto);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(courseRepository).save(existingCourse);
    }

    @Test
    public void testUpdateCourse_NotFound() {
        Long invalidId = 999L;
        CourseDto courseDto = new CourseDto();

        when(courseRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            courseService.update(invalidId, courseDto);
        });

        assertTrue(exception.getMessage().contains("Course not found with id: " + invalidId));
    }

    @Test
    public void testDeleteCourse_Success() {
        Long courseId = 1L;

        when(courseRepository.existsById(courseId)).thenReturn(true);

        String result = courseService.delete(courseId);

        assertEquals("Course deleted successfully", result);
        verify(courseRepository).deleteById(courseId);
    }

    @Test
    public void testDeleteCourse_NotFound() {
        Long invalidId = 999L;

        when(courseRepository.existsById(invalidId)).thenReturn(false);

        String result = courseService.delete(invalidId);

        assertEquals("Course not found with id: " + invalidId, result);
        verify(courseRepository, never()).deleteById(any());
    }

}
