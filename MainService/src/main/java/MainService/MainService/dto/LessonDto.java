package MainService.MainService.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonDto {

    Long id;
    String name;
    String description;
    String content;
    int sequenceOrder;
    int chapterId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
