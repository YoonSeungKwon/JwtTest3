package yoon.test.jwtTest3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yoon.test.jwtTest3.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByUsername(String username);

}
