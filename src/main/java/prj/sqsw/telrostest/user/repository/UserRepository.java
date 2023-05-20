package prj.sqsw.telrostest.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj.sqsw.telrostest.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
