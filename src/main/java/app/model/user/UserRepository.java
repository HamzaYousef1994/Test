package app.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUserName(String name);

    User findUserByEmail(String email);

    User findFirstBy();

}
