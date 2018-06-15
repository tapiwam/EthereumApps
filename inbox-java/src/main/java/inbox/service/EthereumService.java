package inbox.service;

import inbox.contracts.Inbox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthFilter;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import rx.Observable;

import javax.annotation.PreDestroy;
import java.math.BigInteger;

@Slf4j
@Service
public class EthereumService {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Credentials wallet;

    private Inbox inboxContract;

    private Observable<Inbox.NewMessageEventResponse> messageEventResponseObservable;

    @Value(value = "${web3.contracts.inbox.address}")
    private String inboxAddress;

    private static final BigInteger GAS_PRICE = new BigInteger("10");
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

    public Inbox getInboxContract() throws Exception {
        if(inboxContract == null){
            /*
            logger.info("Loading inbox contract "
                    + "\n@address=" + inboxAddress
                    + "\n@wallet=" + wallet.getAddress()
                    + "\n@publicKey=" +wallet.getEcKeyPair().getPublicKey().toString()
            );
            inboxContract = Inbox.load(inboxAddress, web3j, wallet, GAS_PRICE, GAS_LIMIT);
            logger.info("Loaded inbox contract "
                    + "\n@address=" + inboxAddress
                    + "\n@walletAddress=" + wallet.getAddress()
                    + "\n@publicKey=" +wallet.getEcKeyPair().getPublicKey().toString()
                    + "\n@contractAddress=" + inboxContract.getContractAddress()
            );
            */


            logger.info("Loading inbox contract "
                    + "\n@address=" + inboxAddress
                    + "\n@wallet=" + wallet.getAddress()
                    + "\n@publicKey=" +wallet.getEcKeyPair().getPublicKey().toString()
            );

            if(inboxAddress == null || inboxAddress.length() == 0){
                logger.info("No inbox address passed. Creating new contract");
                deployInbox();
            } else {
                logger.info("Inbox address passed. Load existing contract");
                loadContract();
            }

            /*logger.info("No inbox address passed. Creating new contract");
            deployInbox();*/

            logger.info("Loaded inbox contract "
                    + "\n@address=" + inboxAddress
                    + "\n@walletAddress=" + wallet.getAddress()
                    + "\n@publicKey=" +wallet.getEcKeyPair().getPublicKey().toString()
                    + "\n@contractAddress=" + inboxContract.getContractAddress()
            );
        }

        return inboxContract;
    }

    private void loadContract() throws Exception {
        inboxContract = Inbox.load(inboxAddress, web3j, wallet, GAS_PRICE, GAS_LIMIT);
        inboxMessageSubscribe();
    }

    private void deployInbox() throws Exception {
        inboxContract = Inbox.deploy(
                web3j, wallet,
                GAS_PRICE, GAS_LIMIT,
                "Hello").send();

        inboxMessageSubscribe();
    }


    public void inboxMessageSubscribe() throws Exception {
        // EthFilter filter = new EthFilter();
        messageEventResponseObservable = getInboxContract()
                // .newMessageEventObservable(filter)
                .newMessageEventObservable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
                .asObservable();

        logger.warn(">>> EVENT OBSERVABLE SET : " + messageEventResponseObservable);
        startMessageListener();
    }

    public void startMessageListener(){
        logger.warn(">>> EVENT SUBSCRIPTION INIT <<<");
        messageEventResponseObservable
                .subscribe(event -> {
                    logger.info(">>> EVENT ALERT: " + event.toString());
                });
        logger.warn(">>> EVENT SUBSCRIPTION INIT DONE <<<");
    }

    @PreDestroy
    public void stopMessageListener(){
        // messageEventResponseObservable.un
    }
}
