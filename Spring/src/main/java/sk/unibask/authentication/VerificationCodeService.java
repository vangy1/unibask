package sk.unibask.authentication;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.unibask.data.model.VerificationCode;
import sk.unibask.data.repository.VerificationCodeRepository;
import sk.unibask.mail.MailService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class VerificationCodeService {
    private final VerificationCodeRepository verificationCodeRepository;

    private RandomStringGenerator randomStringGenerator;
    private final MailService mailService;


    @Autowired
    public VerificationCodeService(VerificationCodeRepository verificationCodeRepository, MailService mailService) {
        this.randomStringGenerator = new RandomStringGenerator.Builder().withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build();
        this.verificationCodeRepository = verificationCodeRepository;
        this.mailService = mailService;
    }

    public String generateVerificationCode() {
        return randomStringGenerator.generate(8);

    }

    public String createVerificationCode(String mail) {
        if (!mail.endsWith("@uniba.sk")) return null;

        String generatedCode = generateVerificationCode();

        var verificationCode = new VerificationCode();
        verificationCode.setCode(generatedCode);
        verificationCode.setEmail(mail);
        verificationCode.setCreationDate(new Date());

        verificationCodeRepository.save(verificationCode);
        mailService.sendVerificationCode(mail, generatedCode);

        return generatedCode;
    }

    public boolean isVerificationCodeValid(String mail, String codeInput) {
        List<VerificationCode> verificationCodes = verificationCodeRepository.findAllByEmail(mail);
        return verificationCodes.stream().anyMatch(verificationCode -> Objects.equals(verificationCode.getCode(), codeInput));
    }
}
