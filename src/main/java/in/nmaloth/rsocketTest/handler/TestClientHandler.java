package in.nmaloth.rsocketTest.handler;

import in.nmaloth.rsocketServices.config.NodeConfig;
import in.nmaloth.rsocketServices.config.model.NodeInfo;
import in.nmaloth.rsocketServices.handler.ClientHandler;
import in.nmaloth.rsocketServices.model.proto.ServerRegistrationOuterClass;
import in.nmaloth.rsocketTest.constants.RouteConstants;
import in.nmaloth.rsocketTest.service.DispatcherServiceTest;
import in.nmaloth.rsocketServices.service.DispatcherService;
import in.nmaloth.rsocketServices.service.ServiceTracker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class TestClientHandler extends ClientHandler {



    public TestClientHandler(NodeInfo nodeInfo, ServiceTracker serviceTracker, DispatcherService dispatcherService) {
        super(nodeInfo, serviceTracker, dispatcherService);

    }


    @MessageMapping(RouteConstants.CONNECTOR)
    public Flux<String> getTestFLux(RSocketRequester rSocketRequester,
                                    Mono<ServerRegistrationOuterClass.ServerRegistration> registrationMono){

        registrationMono
                .doOnNext(registration -> log.info(STREAMING_REQUEST_FOR_SERVICE,registration.getServiceName(),
                        registration.getServiceInstance()))
                .subscribe();

            return ((DispatcherServiceTest)super.dispatcherService()).requestForStreamForOutgoingProcessor(rSocketRequester);


    }

    @MessageMapping(RouteConstants.DISTRIBUTOR)
    public Flux<String> getTestFLuxDistributor(RSocketRequester rSocketRequester,
                                    Mono<ServerRegistrationOuterClass.ServerRegistration> registrationMono){

        registrationMono
                .doOnNext(registration -> log.info(STREAMING_REQUEST_FOR_SERVICE,registration.getServiceName(),
                        registration.getServiceInstance()))
                .subscribe();

        return ((DispatcherServiceTest)super.dispatcherService()).requestForStreamForOutgoingProcessor(rSocketRequester);

    }
}
