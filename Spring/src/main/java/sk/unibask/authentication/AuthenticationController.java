package sk.unibask.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sk.unibask.data.repository.AccountRepository;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final VerificationCodeService verificationCodeService;
    private final AccountRepository accountRepository;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserService userService, VerificationCodeService verificationCodeService, AccountRepository accountRepository) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.verificationCodeService = verificationCodeService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/status")
    @ResponseBody
    @Transactional
    public UserDto status() {
        return userService.getUser(authenticationService.getLoggedAccount());
    }

    @PostMapping("/login")
    @Transactional
    public UserDto login(@RequestBody Map<String, String> body) {
        return userService.getUser(authenticationService.login(body.get("mail"), body.get("password")));
    }

    @PostMapping("/register")
    @Transactional
    public UserDto register(@RequestBody Map<String, String> body) {
        return userService.getUser(authenticationService.register(body.get("mail"), body.get("password"), body.get("username"), body.get("verificationCode")));
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
