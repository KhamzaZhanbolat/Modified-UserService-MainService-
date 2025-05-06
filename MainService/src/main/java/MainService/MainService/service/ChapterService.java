package MainService.MainService.service;

import MainService.MainService.dto.ChapterDto;

import java.util.List;

public interface ChapterService {

    ChapterDto getById(Long id);
    List<ChapterDto> getAll();
    ChapterDto create(ChapterDto chapterDto);
    ChapterDto update(Long id,ChapterDto chapterDto);
    String delete(Long id);

}
