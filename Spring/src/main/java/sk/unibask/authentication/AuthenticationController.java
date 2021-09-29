package sk.unibask.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.unibask.data.repository.AccountRepository;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final VerificationCodeService verificationCodeService;
    private final AccountRepository accountRepository;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, VerificationCodeService verificationCodeService, AccountRepository accountRepository) {
        this.authenticationService = authenticationService;
        this.verificationCodeService = verificationCodeService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/status")
    @ResponseBody
    private UserDto status() {
        return authenticationService.getLoggedUser();
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody Map<String, String> body) {
        return authenticationService.login(body.get("mail"), body.get("password"));
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody Map<String, String> body) {
        return authenticationService.register(body.get("mail"), body.get("password"), body.get("username"), body.get("verificationCode"));
    }

    @PostMapping("/password-change")
    public void passwordChange(@RequestBody Map<String, String> body) {
        authenticationService.passwordChange(body.get("mail"), body.get("password"), body.get("verificationCode"));
    }

    @GetMapping("/code")
    public boolean checkVerificationCode(@RequestParam("mail") String mail, @RequestParam("codeInput") String codeInput) {
        return verificationCodeService.isVerificationCodeValid(mail, codeInput);
    }


    @PostMapping("/code")
    public String generateCode(@RequestBody Map<String, String> body) {
        return verificationCodeService.createVerificationCode(body.get("mail"));
    }
}
