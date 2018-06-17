package trader.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.Contract;
import rx.Observable;
import trader.contracts.Trade;

import javax.annotation.PreDestroy;
import java.math.BigInteger;

@Slf4j
@Service
public class EthereumService {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Credentials wallet;

    @Getter
    private Trade tradeContract;

    // private Observable<Inbox.NewMessageEventResponse> messageEventResponseObservable;

    @Value(value = "${web3.contracts.trade.address}")
    private String tradeAddress;

    private static final BigInteger GAS_PRICE = new BigInteger("1");
    private static final BigInteger  GAS_LIMIT = new BigInteger("300000");

    // @PostConstruct
    public void testConnection() {
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();

            logger.info("Blockchain client version: " + clientVersion);
        } catch (Exception e){
            logger.error("Issue while connecting to blockchain");
            e.printStackTrace();
        }

    }

    public String getClientVersion() throws Exception {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        return clientVersion;
    }

    public Trade getTradeContract() throws Exception {
        if(tradeContract == null){

            logger.info("Loading trader contract "
                    + "\n@address=" + tradeAddress
                    + "\n@wallet=" + wallet.getAddress()
                    + "\n@publicKey=" +wallet.getEcKeyPair().getPublicKey().toString()
            );

            if(tradeAddress == null || tradeAddress.length() == 0){
                logger.info("No trader address passed. Creating new contract");
                deployInbox();
            } else {
                logger.info("Inbox address passed. Load existing contract");
                loadContract();
            }

            /*logger.info("No trader address passed. Creating new contract");
            deployInbox();*/

            logger.info("Loaded trader contract "
                    + "\n@address=" + tradeAddress
                    + "\n@walletAddress=" + wallet.getAddress()
                    + "\n@publicKey=" +wallet.getEcKeyPair().getPublicKey().toString()
                    + "\n@contractAddress=" + tradeContract.getContractAddress()
            );
        }

        return tradeContract;
    }

    private void loadContract() throws Exception {
        tradeContract = Trade.load(tradeAddress, web3j, wallet, GAS_PRICE, GAS_LIMIT);
        // inboxMessageSubscribe();
    }

    private void deployInbox() throws Exception {
        tradeContract = Trade.deploy(
                web3j, wallet,
                GAS_PRICE, GAS_LIMIT).send();

        // inboxMessageSubscribe();
    }


    /*public void inboxMessageSubscribe() throws Exception {
        // EthFilter filter = new EthFilter();
        messageEventResponseObservable = getInboxContract()
                // .newMessageEventObservable(filter)
                .newMessageEventObservable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
                .asObservable();

        logger.warn(">>> EVENT OBSERVABLE SET : " + messageEventResponseObservable);
        startMessageListener();
    }*/

    /*public void startMessageListener(){
        logger.warn(">>> EVENT SUBSCRIPTION INIT <<<");
        messageEventResponseObservable
                .subscribe(event -> {
                    logger.info(">>> EVENT ALERT: " + event.message);
                });
        logger.warn(">>> EVENT SUBSCRIPTION INIT DONE <<<");
    }*/
}
