package trader.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigInteger;

@Slf4j
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ETrade {

    @Id
    @Column
    private BigInteger tranId;

    @Enumerated(EnumType.STRING)
    @Column
    private trader.bo.TradeStatus status;

    @Column
    private String account;

    @Column
    private String asset;

    @Column
    private String location;

    @Column
    private BigInteger quantity;

    @Column
    private BigInteger amount;

    @Column
    private BigInteger timestamp;

    @Column
    private String user;

}
