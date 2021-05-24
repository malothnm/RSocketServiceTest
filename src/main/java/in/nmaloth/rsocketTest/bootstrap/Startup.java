package in.nmaloth.rsocketTest.bootstrap;

import in.nmaloth.rsocketServices.config.model.NodeInfo;
import in.nmaloth.rsocketServices.config.model.ServiceEvent;
import in.nmaloth.rsocketServices.service.ClientService;
import in.nmaloth.rsocketServices.service.ServerService;
import in.nmaloth.rsocketServices.service.ServiceEventsService;
import in.nmaloth.rsocketServices.service.ServiceTracker;
import in.nmaloth.rsocketTest.handler.TestClientHandler;
import in.nmaloth.rsocketTest.service.DispatcherServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;

@Slf4j
@Component
public class Startup implements CommandLineRunner {


    private final ServerService serverService;
    private final DispatcherServiceTest dispatcherService;
    private final ServiceTracker serviceTracker;
    private final ClientService clientService;
    private final NodeInfo nodeInfo;
    private final ConnectableFlux<ServiceEvent> connectableFlux;
    private final ServiceEventsService serviceEventsService;

    public Startup(ServerService serverService,
                   DispatcherServiceTest dispatcherService,
                   ServiceTracker serviceTracker,
                   ClientService clientService,
                   NodeInfo nodeInfo,
                   ConnectableFlux<ServiceEvent> connectableFlux,
                   ServiceEventsService serviceEventsService) {

        this.serverService = serverService;
        this.dispatcherService = dispatcherService;
        this.serviceTracker = serviceTracker;
        this.clientService = clientService;
        this.nodeInfo = nodeInfo;
        this.connectableFlux = connectableFlux;
        this.serviceEventsService = serviceEventsService;
    }


    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        log.info(" Updating dispatcher Service");
        serviceTracker.updateDispatcherService(dispatcherService);
        clientService.updateClientHandler(new TestClientHandler(nodeInfo,serviceTracker,dispatcherService));

        serverService.createServer();

        serviceEventsService.fluxSubscriptions(connectableFlux);


    }
}
