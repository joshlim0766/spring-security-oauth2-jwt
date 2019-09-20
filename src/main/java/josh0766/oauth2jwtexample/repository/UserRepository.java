package josh0766.oauth2jwtexample.repository;

import josh0766.oauth2jwtexample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    long countByUserName (String userName);

    User findByUserName (String userName);
}
