package inbox.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Slf4j
@Configuration
public class Web3jConfig {

    @Value(value = "${web3.url:http://localhost:8102}")
    private String rpcUrl;

    @Value(value = "${web3.wallet.path}")
    private String walletPath;

    @Value(value = "${web3.wallet.password}")
    private String walletPassword;

    @Bean
    public Web3j web3j(){

        logger.warn(">>>>>>>>>> INITIALIZING ETHEREUM RPC URL: " + rpcUrl + " <<<<<<<<<<<<");

        HttpService httpsService = new HttpService(rpcUrl);
        Web3j web3j = Web3j.build(httpsService);

        logger.warn(">>>>>>>>>> BUILT ETHEREUM RPC URL: " + rpcUrl + " <<<<<<<<<<<<");
        return web3j;
    }

    @Bean
    public Credentials web3Credentials() throws Exception {

        logger.info( ">>>>>>>>>> Initializing wallet credentials. @walletPath=" + walletPath + " <<<<<<<<<<<<" );
        Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletPath);
        logger.info( ">>>>>>>>>> Done initializing wallet credentials. @credentials=" + credentials.toString() + " <<<<<<<<<<<<" );
        return credentials;
    }

}
