package sk.unibask.authentication;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.unibask.data.model.VerificationCode;
import sk.unibask.data.repository.VerificationCodeRepository;
import sk.unibask.mail.MailService;

import java.util.Date;
import java.util.Objects;

@Service
public class VerificationCodeService {
    private final VerificationCodeRepository verificationCodeRepository;
    private final RandomStringGenerator randomStringGenerator;
    private final MailService mailService;


    @Autowired
    public VerificationCodeService(VerificationCodeRepository verificationCodeRepository, MailService mailService) {
        this.randomStringGenerator = new RandomStringGenerator.Builder().withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build();
        this.verificationCodeRepository = verificationCodeRepository;
        this.mailService = mailService;
    }


    @Transactional
    public void createVerificationCode(String mail) {
        checkMail(mail);

        String generatedCode = generateVerificationCode();
        var verificationCode = new VerificationCode();
        verificationCode.setCode(generatedCode);
        verificationCode.setEmail(mail);
        verificationCode.setCreationDate(new Date());

        verificationCodeRepository.save(verificationCode);
        mailService.sendVerificationCode(mail, generatedCode);
    }

    @Transactional
    public boolean isVerificationCodeValid(String mail, String codeInput) {
        return verificationCodeRepository.findAllByEmail(mail).stream().anyMatch(verificationCode -> Objects.equals(verificationCode.getCode(), codeInput));
    }

    private String generateVerificationCode() {
        return randomStringGenerator.generate(8);
    }

    private void checkMail(String mail) {
        if (!mail.endsWith("@uniba.sk") && !mail.endsWith("@fmph.uniba.sk")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Mail musí byť v uniba.sk doméne.");
        }
    }
}
