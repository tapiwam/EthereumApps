package inbox.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse { //implements Serializable {

    private Boolean status;
    private String msg;
    private String error;
    private Object data;

}
