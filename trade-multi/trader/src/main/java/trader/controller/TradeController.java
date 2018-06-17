package trader.controller;

import org.springframework.web.bind.annotation.*;
import trader.model.JsonResponse;
import trader.bo.TradeBO;
import trader.service.EthereumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = {"trade"})
public class TradeController {

    @Autowired
    private EthereumService ethereumService;

    @GetMapping(value = {"/", ""})
    public JsonResponse getTransactions(){

        JsonResponse json = null;
        try {
            List<BigInteger> msg = ethereumService.getTradeContract()
                    //.getTransListBySize(BigInteger.valueOf(10L))
                    .getTransList()
                    .send();

            json = new JsonResponse(true, "TranIDs", null, msg);
            logger.info("Inbox message found. @message=" + msg);
        } catch (Exception e){
            json = new JsonResponse(false, "Error fetching trader message", null, null);
            logger.error("Error fetching trader message. @error=" + e.getMessage());
            e.printStackTrace();
        }

        return json;
    }


    @PostMapping(value = {"", "/"} )
    public JsonResponse updateTransaction(@RequestBody TradeBO trade){

        JsonResponse json = null;
        try {

            TransactionReceipt transactionReceipt = ethereumService
                    .getTradeContract()
                    .updateTran(
                            // BigInteger.valueOf(trade.getTranId())
                            trade.getTranId()
                            , trade.getStatus().toString()
                            , trade.getAccount()
                            , trade.getAsset()
                            , trade.getLocation()
                            // , BigInteger.valueOf(trade.getQuantity())
                            , trade.getQuantity()
                            // , BigInteger.valueOf(trade.getAmount())
                            , trade.getAmount()
                            , trade.getUser())
                    .send();

            json = new JsonResponse(true, "Trade message set", null, transactionReceipt);

            logger.info("Trade message set. "
                    + "\n@trade=" + trade
                    + "\n@status=" + transactionReceipt.getStatus()
                    + "\n@blockHash=" + transactionReceipt.getBlockHash()
                    + "\n@blockNumber=" + transactionReceipt.getBlockNumber()
                    + "\n@transactionHash=" + transactionReceipt.getTransactionHash()
                    + "\n@transactionIndex=" + transactionReceipt.getTransactionIndex()
            );
        } catch (Exception e){
            json = new JsonResponse(false, "Error setting trade message", null, null);
            logger.error("Error fetching trader message. @error=" + e.getMessage());
            e.printStackTrace();
        }

        return json;
    }


    @GetMapping(value = {"/status/{tranid}/{status}"} )
    public JsonResponse updateTransactionStatus(
            @PathVariable("trainid") BigInteger tranid,
            @PathVariable("status") String status
    ){

        JsonResponse json = null;
        try {

            TransactionReceipt transactionReceipt = ethereumService
                    .getTradeContract()
                    .updateTranStatus(
                            tranid,
                            status,
                            "REST"
                    )
                    .send();

            json = new JsonResponse(true, "Trade message set", null, transactionReceipt);

            logger.info("Trade Status updated. "
                    + "\n@tranid=" + tranid
                    + "\n@tran_status=" + status
                    + "\n@status=" + transactionReceipt.getStatus()
                    + "\n@blockHash=" + transactionReceipt.getBlockHash()
                    + "\n@blockNumber=" + transactionReceipt.getBlockNumber()
                    + "\n@transactionHash=" + transactionReceipt.getTransactionHash()
                    + "\n@transactionIndex=" + transactionReceipt.getTransactionIndex()
            );
        } catch (Exception e){
            json = new JsonResponse(false, "Error setting trade message", null, null);
            logger.error("Error fetching trader message. @error=" + e.getMessage());
            e.printStackTrace();
        }

        return json;
    }

}
