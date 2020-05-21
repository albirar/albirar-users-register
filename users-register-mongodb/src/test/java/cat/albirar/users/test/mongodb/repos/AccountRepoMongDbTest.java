/*
 * This file is part of "albirar users-register-mongodb".
 * 
 * "albirar users-register-mongodb" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "albirar users-register-mongodb" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with "albirar users-register-mongodb" source code.  If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 * Copyright (C) 2020 Octavi Fornés
 */
package cat.albirar.users.test.mongodb.repos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import cat.albirar.users.repos.IAccountRepo;
import cat.albirar.users.repos.IUserRepo;
import cat.albirar.users.test.mongodb.MongodbTestUtils;
import cat.albirar.users.test.mongodb.UsersRegisterMongoDbTestConfig;
import cat.albirar.users.test.mongodb.testcontainer.MongodbTestContainerExtension;
import cat.albirar.users.test.repos.AccountRepoTest;

/**
 * Test of {@link IAccountRepo}.
 * @author Octavi Forn&eacute;s &lt;<a href="mailto:ofornes@albirar.cat">ofornes@albirar.cat</a>&gt;
 * @since 1.0.0
 */
@ContextConfiguration(classes = {UsersRegisterMongoDbTestConfig.class})
@ExtendWith(MongodbTestContainerExtension.class)
public class AccountRepoMongDbTest extends AccountRepoTest {
    @Autowired
    protected IUserRepo userRepo;
    @Autowired
    protected IAccountRepo accountRepo;
    
    @BeforeEach
    public void setupTest() {
        MongodbTestUtils.instance().setupData(userRepo, accountRepo);
    }
    @AfterEach
    public void teardownTest() {
        MongodbTestUtils.instance().teardownData(userRepo, accountRepo);
    }
}
