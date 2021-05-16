package blog.surapong.example.logrequestresponse.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class ToDoDto {
    private Long userId;
    private Long id;
    private String title;
    private Boolean completed;
}
