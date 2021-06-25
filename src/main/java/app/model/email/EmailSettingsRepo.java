package app.model.email;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSettingsRepo extends JpaRepository<EmailSettings, Integer> {

    EmailSettings findFirstBy();

}
