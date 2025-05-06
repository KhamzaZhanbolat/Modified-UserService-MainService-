package MainService.MainService.mappers;

import MainService.MainService.dto.CourseDto;
import MainService.MainService.entities.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toDto(Course course);

    Course toEntity(CourseDto courseDto);

    List<CourseDto> toDtoList(List<Course> courses);

}
