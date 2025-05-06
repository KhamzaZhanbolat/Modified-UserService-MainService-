package MainService.MainService.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterDto {

    Long id;
    String name;
    String description;
    int sequenceOrder;
    Long courseId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
