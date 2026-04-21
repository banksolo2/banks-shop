package app.web.seunolo2.banksshop.responseMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseMessage {
    private String type;
    private String message;
    private Object object;
    private HttpStatus httpStatus;
}
