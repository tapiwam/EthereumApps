package trader.controller;

import org.springframework.web.bind.annotation.*;
import trader.model.JsonResponse;
import trader.bo.TradeBO;
import trader.service.EthereumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import trader.service.TradeService;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = {"trade"})
public class TradeController {

    @Autowired
    private EthereumService ethereumService;

    @Autowired
    private TradeService tradeService;

    @GetMapping(value = {"/", ""})
    public JsonResponse getTransactions(){

        JsonResponse json = null;
        try {
            List<BigInteger> items = tradeService.chainGetTranList();

            json = new JsonResponse(true, "TranIDs", null, items);
            logger.info("Tran IDS found. @items=" + items);
        } catch (Exception e){
            json = new JsonResponse(false, "Error fetching tran ids", null, null);
            logger.error("Error fetching tran ids. @error=" + e.getMessage());
            e.printStackTrace();
        }

        return json;
    }


    @PostMapping(value = {"", "/"} )
    public JsonResponse updateTransaction(@RequestBody TradeBO trade){

        JsonResponse json = null;
        try {


            TransactionReceipt transactionReceipt = tradeService.chainUpdateTrade(trade);

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

    @GetMapping(value = {"/{tranid}"} )
    public JsonResponse getTran(
            @PathVariable("trainid") BigInteger tranid
    ){

        JsonResponse json = null;
        try {

            TradeBO trade = tradeService.chainGetTran(tranid);

            json = new JsonResponse(true, null, null, trade);

            logger.info("Trade search. @trade=" + trade);
        } catch (Exception e){
            json = new JsonResponse(false, "Error setting trade message", null, null);
            logger.error("Error fetching trader message. @error=" + e.getMessage());
            e.printStackTrace();
        }

        return json;
    }

}
