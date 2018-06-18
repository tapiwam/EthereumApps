package trader.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Bool;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple9;
import trader.bo.TradeBO;
import trader.bo.TradeStatus;
import trader.contracts.Trade;
import trader.entity.ETrade;
import trader.repo.TradeRepo;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import trader.util.AsyncUtils;

@Slf4j
@Service
public class TradeService {

    @Autowired
    private EthereumService ethereumService;

    @Autowired
    private TradeRepo tradeRepo;

    @Autowired
    private ThreadPoolTaskExecutor asynExecutor;

    @PostConstruct
    public void setup() throws Exception {

        List<ETrade> trades = new ArrayList<>();

        ETrade t1 = new ETrade(BigInteger.valueOf(1L), TradeStatus.SETTLE, "A1", "000000001", "NY", BigInteger.valueOf(1000L), BigInteger.valueOf(100L), null, "SETUP", false);
        trades.add(t1);

        t1 = new ETrade(BigInteger.valueOf(2L), TradeStatus.SETTLE, "A1", "000000001", "NY", BigInteger.valueOf(1000L), BigInteger.valueOf(100L), null, "SETUP", false);
        trades.add(t1);

        t1 = new ETrade(BigInteger.valueOf(3L), TradeStatus.LACK, "A1", "000000002", "NY", BigInteger.valueOf(1000L), BigInteger.valueOf(100L), null, "SETUP", false);
        trades.add(t1);

        t1 = new ETrade(BigInteger.valueOf(4L), TradeStatus.RELEASE, "A1", "000000002", "NY", BigInteger.valueOf(1000L), BigInteger.valueOf(100L), null, "SETUP", false);
        trades.add(t1);

        t1 = new ETrade(BigInteger.valueOf(5L), TradeStatus.PENDING, "A1", "000000001", "NY", BigInteger.valueOf(1000L), BigInteger.valueOf(100L), null, "SETUP", false);
        trades.add(t1);

        // ====

        t1 = new ETrade(BigInteger.valueOf(6L), TradeStatus.SETTLE, "A2", "000000001", "NY", BigInteger.valueOf(1000L), BigInteger.valueOf(100L), null, "SETUP", false);
        trades.add(t1);

        t1 = new ETrade(BigInteger.valueOf(7L), TradeStatus.SETTLE, "A2", "000000002", "NY", BigInteger.valueOf(2000L), BigInteger.valueOf(20L), null, "SETUP", false);
        trades.add(t1);

        t1 = new ETrade(BigInteger.valueOf(8L), TradeStatus.SETTLE, "A2", "000000003", "NY", BigInteger.valueOf(1500L), BigInteger.valueOf(150L), null, "SETUP", false);
        trades.add(t1);

        t1 = new ETrade(BigInteger.valueOf(9L), TradeStatus.PENDING, "A2", "000000001", "NY", BigInteger.valueOf(3000L), BigInteger.valueOf(300L), null, "SETUP", false);
        trades.add(t1);

        t1 = new ETrade(BigInteger.valueOf(10L), TradeStatus.PENDING, "A2", "000000003", "NY", BigInteger.valueOf(1000L), BigInteger.valueOf(100L), null, "SETUP", false);
        trades.add(t1);

        trades = tradeRepo.save(trades);

        for(ETrade t : trades){
            chainUpdateTradeAsync(t.generateTrade());
            // syncTranToChain(t.getTradId());
        }

/*
        List<Future> pending = new ArrayList<>();
        for (ETrade t : trades) {

            CompletableFuture c = CompletableFuture.supplyAsync(syncRun1(t))
                // .thenAccept(asyncSave)
            ;

//            pending.add(asynExecutor.submit(syncRun(t)));
            pending.add(c);
            logger.info("Waiting for trade sync: " + t);
        }

        while (AsyncUtils.isRunning(pending)) {
            logger.warn("** Still loading ** " + AsyncUtils.runningThreads(pending));
            Thread.sleep(5000);
        }*/

        logger.info("*************** DONE INITIALIZING TRADES **********************");

    }

    /*private Consumer<BigInteger> asyncSave(BigInteger traid){
        return new Consumer<BigInteger>() {
            @Override
            public void accept(BigInteger bigInteger) {

            }
        };
    }*/

    private Supplier<BigInteger> syncRun1(ETrade t) {

        Supplier<BigInteger> s = new Supplier() {
            @Override
            public Object get() {
                try {
                    syncTranToChain(t.getTranId());
                    logger.info("Synced trade. @tran=" + t.toString());
                    return t.getTranId();
                } catch (Exception e) {
                    logger.error("Unable to sync trade. @tran=" + t.toString() + " @error=" + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };

        return s;
    }

    private Runnable syncRun(ETrade t) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    syncTranToChain(t.getTranId());
                    logger.info("Synced trade. @tran=" + t.toString());
                } catch (Exception e) {
                    logger.error("Unable to sync trade. @tran=" + t.toString() + " @error=" + e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        return r;
    }


    public void syncTranToChain(BigInteger tranid) throws Exception {
        ETrade trade = tradeRepo.findOne(tranid);

        logger.info("Syncing-to-Chain: @tranid=" + tranid);

        trade.setConfirmed(false);
        tradeRepo.save(trade);

        logger.info("Syncing-to-Chain: Trade found @trade=" + trade);

        TradeBO t1 = trade.generateTrade();
        TransactionReceipt transactionReceipt = chainUpdateTrade(trade.generateTrade());

        logger.info("Syncing-to-Chain: Sync done @transactionReceipt=" + transactionReceipt.toString());

        trade.setConfirmed(true);
        tradeRepo.save(trade);
    }

    public ETrade syncTranToDb(BigInteger tranid) throws Exception {
        logger.info("Syncing-to-DB: @tranid=" + tranid);

        Tuple9 tuple = ethereumService.getTradeContract().getTran(tranid).send();
        ETrade trade = tupleToTrade(tuple);
        trade.setConfirmed(true);

        logger.info("Syncing-to-DB: Confirmed. @trade=" + trade);

        return saveTrade(trade);
    }

    public List<BigInteger> chainGetTranListLast(Long size) throws Exception {
        List<BigInteger> items = ethereumService.getTradeContract()
                .getTransListBySize(BigInteger.valueOf(10L))
                // .getTransList()
                .send();

        return items;
    }

    public List<BigInteger> chainGetTranList() throws Exception {
        List<BigInteger> items = ethereumService.getTradeContract()
                //.getTransListBySize(BigInteger.valueOf(10L))
                .getTransList()
                .send();

        return items;
    }

    private TradeBO tupleToTradeBo(Tuple9 tuple) {
        TradeBO trade = new TradeBO(
                (BigInteger) tuple.getValue1(),         // tranid
                TradeStatus.valueOf((String) tuple.getValue2()),   // status
                (String) tuple.getValue3(),              // account
                (String) tuple.getValue4(),              // asset
                (String) tuple.getValue5(),              // location
                (BigInteger) tuple.getValue6(),          //
                (BigInteger) tuple.getValue7(),
                (BigInteger) tuple.getValue8(),
                (String) tuple.getValue9()
        );
        return trade;
    }

    private ETrade tupleToTrade(Tuple9 tuple) {
        return tupleToTradeBo(tuple).generateTrade();
    }

    public ETrade saveTrade(ETrade trade) {
        return tradeRepo.saveAndFlush(trade);
    }

    public TradeBO chainGetTran(BigInteger tranid) throws Exception {
        Tuple9 tuple = ethereumService
                .getTradeContract()
                .getTran(tranid)
                .send();

        return tupleToTradeBo(tuple);
    }

    public CompletableFuture<TransactionReceipt> chainUpdateTradeAsync(TradeBO trade) throws Exception {

        logger.info("Async trade " + trade);
        CompletableFuture<TransactionReceipt> c = ethereumService
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
                .sendAsync();

        // transactionReceipt =
        // syncTranToDb(trade.getTranId());

        c.whenComplete(new BiConsumer<TransactionReceipt, Throwable>() {
            @Override
            public void accept(TransactionReceipt transactionReceipt, Throwable throwable) {
                logger.info("Complete Async trade. " + trade);
            }
        });

        logger.info("Scheduled Async trade " + trade.getTranId());

        return c;
    }

    public TransactionReceipt chainUpdateTrade(TradeBO trade) throws Exception {

        TransactionReceipt transactionReceipt = chainUpdateTradeAsync(trade).get();

        // transactionReceipt =
        syncTranToDb(trade.getTranId());

        return transactionReceipt;
    }

    public TransactionReceipt chainUpdateTradeStatus(BigInteger tranid, TradeStatus status, String user) throws Exception {
        TransactionReceipt transactionReceipt = ethereumService
                .getTradeContract()
                .updateTranStatus(
                        tranid,
                        status.toString(),
                        user
                )
                .send();

        syncTranToDb(tranid);

        return transactionReceipt;
    }


}
