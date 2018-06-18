package trader.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Future;

@Slf4j
public class AsyncUtils {

    public static Boolean isRunning(List<Future> pending){
        Boolean check = false;
        for(Future f : pending){
            if(!f.isDone()){
                logger.warn("Still running. " + f.toString());
                check = true;
                break;
            }
        }

        return check;
    }

    public static Long runningThreads(List<Future> pending){
        return pending.stream().filter(Future::isDone).count();
    }



}
