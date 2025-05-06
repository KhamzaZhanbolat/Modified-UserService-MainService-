package MainService.MainService.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDto {

    Long id;
    String name;
    String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
