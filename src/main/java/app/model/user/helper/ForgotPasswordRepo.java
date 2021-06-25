package app.model.user.helper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Integer> {


    boolean existsForgotPasswordByCodeAndEmail(int code, String email);

    ForgotPassword findForgotPasswordByCodeAndEmail(int code, String email);

    @Modifying
    @Transactional
    void deleteForgotPasswordByUserID(int userID);

}
