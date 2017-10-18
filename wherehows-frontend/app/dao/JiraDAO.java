/**
 * Copyright 2015 LinkedIn Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package dao;

import java.util.List;
import wherehows.models.table.LdapInfo;


public class JiraDAO extends AbstractMySQLOpenSourceDAO {
  private final static String GET_CURRENT_USER_INFO = "SELECT full_name, display_name, email, "
      + "department_id, department_name, manager_user_id, org_hierarchy, user_id "
      + "FROM dir_external_user_info WHERE user_id = ?";

  private final static String GET_FIRST_LEVEL_LDAP_INFO = "SELECT full_name, display_name, email, "
      + "department_id, department_name, manager_user_id, org_hierarchy, user_id "
      + "FROM dir_external_user_info WHERE manager_user_id = ?";

  public static List<LdapInfo> getCurrentUserLdapInfo(String userId) {
    return getJdbcTemplate().query(GET_CURRENT_USER_INFO, new LdapInfoRowMapper(), userId);
  }

  public static List<LdapInfo> getFirstLevelLdapInfo(String managerId) {
    return getJdbcTemplate().query(GET_FIRST_LEVEL_LDAP_INFO, new LdapInfoRowMapper(), managerId);
  }
}
