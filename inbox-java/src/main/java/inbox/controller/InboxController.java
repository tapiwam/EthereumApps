package inbox.controller;

import inbox.model.JsonResponse;
import inbox.service.EthereumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Slf4j
@RestController
@RequestMapping(value = {"inbox"})
public class InboxController {

    @Autowired
    private EthereumService ethereumService;

    @GetMapping(value = {"/", ""})
    public JsonResponse getMessage(){

        JsonResponse json = null;
        try {
            String msg = ethereumService.getInboxContract().getMessage().send();
            json = new JsonResponse(true, "Inbox message found", null, msg);
            logger.info("Inbox message found. @message=" + msg);
        } catch (Exception e){
            json = new JsonResponse(false, "Error fetching inbox message", null, null);
            logger.error("Error fetching inbox message. @error=" + e.getMessage());
            e.printStackTrace();
        }

        return json;
    }


    @GetMapping(value = "/{msg}")
    /*

     */
    public JsonResponse setMessage(@PathVariable(value = "msg", required = true) String msg){

        JsonResponse json = null;
        try {

            TransactionReceipt transactionReceipt = ethereumService.getInboxContract().sendMessage(msg).send();

            json = new JsonResponse(true, "Inbox message set", null, transactionReceipt);

            logger.info("Inbox message set. "
                    + "\n@message=" + msg
                    + "\n@status=" + transactionReceipt.getStatus()
                    + "\n@blockHash=" + transactionReceipt.getBlockHash()
                    + "\n@blockNumber=" + transactionReceipt.getBlockNumber()
                    + "\n@transactionHash=" + transactionReceipt.getTransactionHash()
                    + "\n@transactionIndex=" + transactionReceipt.getTransactionIndex()
            );
        } catch (Exception e){
            json = new JsonResponse(false, "Error setting inbox message", null, null);
            logger.error("Error fetching inbox message. @error=" + e.getMessage());
            e.printStackTrace();
        }

        return json;
    }



}
