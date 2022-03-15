package com.project.account.repository;


import com.project.account.model.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<AccountRole, Long> {

    @Query(value = "SELECT * FROM account_role WHERE role_name =:roleName", nativeQuery = true)
    Optional<AccountRole> findByRoleName(@Param("roleName") String roleName);
}
