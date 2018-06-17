package trader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
// import org.web3j.protocol.ipc.WindowsIpcService;

// @Slf4j
public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args){

        try {
            String rpcUrl = "http://localhost:8102";

            HttpService httpsService = new HttpService(rpcUrl);
            Web3j web3 = Web3j.build(httpsService);


            Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();

            logger.info("Blockchain client version: " + clientVersion);

        } catch ( Exception e ){
            logger.error("Issue while connecting to blockchain");
            e.printStackTrace();
        }

    }
}
