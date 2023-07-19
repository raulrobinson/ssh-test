package co.com.telefonica.ws.controller;

import co.com.telefonica.ws.service.SshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ssh")
public class SshController {

    private final SshService sshService;

    @Autowired
    public SshController(SshService sshService) {
        this.sshService = sshService;
    }

    @GetMapping("/execute")
    public String executeCommand(@RequestParam String host,
                                 @RequestParam int port,
                                 @RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String command) {
        return sshService.executeRemoteCommand(host, port, username, password, command);
    }
}

