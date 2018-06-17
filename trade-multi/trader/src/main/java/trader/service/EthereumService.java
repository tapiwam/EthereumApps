package trader.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Bool;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
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

    @Getter
    private String tradeAddress = "";

    private Boolean isDeployed = null;

    @Getter
    @Value(value = "${web3.contracts.trade.address}")
    private String tradeAddressInit;

    private static final BigInteger GAS_PRICE = new BigInteger("10000");
    private static final BigInteger  GAS_LIMIT = new BigInteger("3000000");

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

    /*public void check{
        TransactionManager tx = new Transa
    }*/

    public String getClientVersion() throws Exception {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        return clientVersion;
    }

    public Trade getTradeContract() throws Exception {
        if(tradeContract == null && (isDeployed == null)){

            if(tradeAddressInit == null || tradeAddressInit.length() == 0){
                logger.warn("No Address provided in properties file");
            } else {
                tradeAddress = tradeAddressInit;
            }

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

            logger.info("POST - Loaded trader contract "
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
        logger.warn(">>>>> FOUND DEPLOYED TRADE CONTRACT : " + tradeAddress );

        isDeployed = true;
    }

    private void deployInbox() throws Exception {
        tradeContract = Trade.deploy(
                web3j, wallet,
                GAS_PRICE, GAS_LIMIT).send();

        tradeAddress = tradeContract.getContractAddress();
        logger.warn(">>>>> DEPLOYED TRADE CONTRACT : " + tradeAddress );

        isDeployed = true;
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
