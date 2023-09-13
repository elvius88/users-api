package py.com.jaha.api.users;

import static py.com.jaha.api.users.constants.GlobalConstants.HOST_IP_NAME;

import java.net.InetAddress;
import java.util.Locale;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@RefreshScope
@EnableAspectJAutoProxy()
@EnableAsync
@EnableFeignClients
@Slf4j
public class UsersApplication {

    public static void main(String[] args) {

        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            System.setProperty(HOST_IP_NAME, hostAddress);
        } catch (Exception e) {
            log.error("Could not get IP for hostIp attribute");
        }
        SpringApplication.run(UsersApplication.class, args);
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Asuncion"));
        Locale.setDefault(Locale.forLanguageTag("es_PY"));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doAfterStartup() {
        log.info("Establishments API has already started");
    }

}
