package sosteam.throwapi.domain.mail.repository.custom;

public interface AuthMailRepositoryCustom {

    String searchSendCodeByEmail(String email);

    long modifyIsSuccessByEmail(String email);
}
