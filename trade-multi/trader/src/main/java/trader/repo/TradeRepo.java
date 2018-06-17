package trader.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import trader.entity.ETrade;

import java.math.BigInteger;

public interface TradeRepo extends JpaRepository<ETrade, BigInteger> {

}
