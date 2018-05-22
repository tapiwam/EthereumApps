package inbox.service;

import inbox.contracts.Inbox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import java.math.BigInteger;

@Slf4j
@Service
public class EthereumService {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Credentials wallet;

    private Inbox inboxContract;

    @Value(value = "${web3.contracts.inbox.address}")
    private String inboxAddress;

    private static final BigInteger GAS_PRICE = new BigInteger("10000");
    private static final BigInteger  GAS_LIMIT = new BigInteger("30000000");

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
    }

    private void deployInbox() throws Exception {
        inboxContract = Inbox.deploy(
                web3j, wallet,
                GAS_PRICE, GAS_LIMIT,
                "Hello").send();
    }

}
