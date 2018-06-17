package trader.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeBO {

    private Long tranId;
    private trader.bo.TradeStatus status;
    private String account;
    private String asset;
    private String location;
    private Long quantity;
    private Long amount;
    private Long timestamp;
    private String user;

}
