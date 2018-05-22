package inbox.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Slf4j
@Configuration
public class Web3jConfig {

    @Value(value = "${web3.url:http://localhost:8102}")
    private String rpcUrl;

    @Bean
    public Web3j web3j(){

        logger.warn(">>>>>>>>>> INITIALIZING ETHEREUM RPC URL: " + rpcUrl + " <<<<<<<<<<<<");

        HttpService httpsService = new HttpService(rpcUrl);
        Web3j web3j = Web3j.build(httpsService);

        logger.warn(">>>>>>>>>> BUILT ETHEREUM RPC URL: " + rpcUrl + " <<<<<<<<<<<<");
        return web3j;
    }
}
