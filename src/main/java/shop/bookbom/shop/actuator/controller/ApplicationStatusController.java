package shop.bookbom.shop.actuator.controller;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.actuator.ApplicationStatus;


@RestController
@RequestMapping("/actuator")
@RequiredArgsConstructor
public class ApplicationStatusController {

    private final ApplicationInfoManager applicationInfoManager;
    private final ApplicationStatus applicationStatus;

    @PostMapping("/status/stop")
    @ResponseStatus(value = HttpStatus.OK)
    public void stopServer() {
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
        applicationStatus.stopService();
    }

    @PostMapping("/status/start")
    @ResponseStatus(value = HttpStatus.OK)
    public void startServer() {
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        applicationStatus.startService();
    }

}
