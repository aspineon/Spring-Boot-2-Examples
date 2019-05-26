package pl.codecouple.graceful.shutdown;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class TomcatGracefulShutdownConnector implements TomcatConnectorCustomizer {

    private volatile Connector connector;

    @Override
    public void customize(final Connector connector) {
        this.connector = connector;
    }

    public Optional<ThreadPoolExecutor> threadPoolExecutor() {
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if(executor instanceof ThreadPoolExecutor) {
            return Optional.of((ThreadPoolExecutor) executor);
        }
        return Optional.empty();
    }

}