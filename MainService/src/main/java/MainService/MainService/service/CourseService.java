package MainService.MainService.service;

import MainService.MainService.dto.CourseDto;

import java.util.List;

public interface CourseService {

    CourseDto getById(Long id);
    List<CourseDto> getAll();
    boolean create(CourseDto course);
    CourseDto update(Long id,CourseDto course);
    String delete(Long id);

}
