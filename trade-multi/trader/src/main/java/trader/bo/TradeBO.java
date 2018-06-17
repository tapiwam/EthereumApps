package trader.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import trader.entity.ETrade;

import java.math.BigInteger;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeBO {

    private BigInteger tranId;
    private trader.bo.TradeStatus status;
    private String account;
    private String asset;
    private String location;
    private BigInteger quantity;
    private BigInteger amount;
    private BigInteger timestamp;
    private String user;

    public ETrade generateTrade(){
        ETrade trade = new ETrade(
                tranId, status, account, asset, location,
                quantity, amount, timestamp, user, false
        );

        return trade;
    }

}
