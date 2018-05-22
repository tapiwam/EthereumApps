package inbox.controller;

import inbox.model.JsonResponse;
import inbox.service.EthereumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = {"inbox"})
public class InboxController {

    @Autowired
    private EthereumService ethereumService;

    @GetMapping(value = "/client/version")
    public JsonResponse test(){

        JsonResponse json = null;
        try {

            // json = new JsonResponse(true, "Client version found", null, clientVersion);
            // logger.error("Found ethereum client version. @clientVersion=" + clientVersion);

        } catch (Exception e){

            // json = new JsonResponse(false, "Error fetching ethereum client version", null, null);
            // logger.error("Error fetching ethereum client version. @error=" + e.getMessage());
            e.printStackTrace();

        }

        return json;
    }

}
