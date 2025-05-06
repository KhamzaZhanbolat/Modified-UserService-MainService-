package MainService.MainService.service;

import MainService.MainService.dto.LessonDto;

import java.util.List;

public interface LessonService {

    LessonDto getById(Long id);
    List<LessonDto> getAll();
    LessonDto create(LessonDto lessonDto);
    LessonDto update(Long id,LessonDto lessonDto);
    String delete(Long id);

}
