SELECT u.*, '' AS '---', ur.* , '' AS '---', r.*
FROM preproject.users u
         INNER JOIN preproject.users_roles ur
                    ON (u.id = ur.user_id)
         INNER JOIN preproject.roles r
                    ON (r.id = ur.role_id)
ORDER BY ur.user_id, ur.role_id