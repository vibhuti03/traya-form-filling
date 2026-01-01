package traya.hairtest.form_filling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import traya.hairtest.form_filling.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,String> {
}
