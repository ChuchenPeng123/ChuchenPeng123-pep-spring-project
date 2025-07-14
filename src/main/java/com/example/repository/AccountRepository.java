//package com.example.repository;

//public interface AccountRepository {
//}
package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Account entities. Extends JpaRepository to provide standard CRUD operations.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Custom query to find an account by its username.
     * Spring Data JPA will automatically generate the implementation for this method.
     *
     * @param username the username to search for.
     * @return the Account object if found, otherwise null.
     */
    Account findAccountByUsername(String username);
}
