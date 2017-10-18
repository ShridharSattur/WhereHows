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
package wherehows.dao.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wherehows.models.table.JiraTicket;
import wherehows.models.table.LdapInfo;


public class JiraViewDao extends BaseViewDao {

  private static final Logger log = LoggerFactory.getLogger(OwnerViewDao.class);

  public JiraViewDao(EntityManagerFactory factory) {
    super(factory);
  }

  private final static String GET_LDAP_INFO = "SELECT full_name, display_name, email, "
      + "department_id, department_name, manager_user_id, org_hierarchy, user_id " + "FROM dir_external_user_info";

  private final static String GET_CURRENT_USER_INFO = "SELECT full_name, display_name, email, "
      + "department_id, department_name, manager_user_id, org_hierarchy, user_id "
      + "FROM dir_external_user_info WHERE user_id = :userId";

  private final static String GET_FIRST_LEVEL_LDAP_INFO = "SELECT full_name, display_name, email, "
      + "department_id, department_name, manager_user_id, org_hierarchy, user_id "
      + "FROM dir_external_user_info WHERE manager_user_id = :managerId";

  private final static String GET_TICKETS_BY_MANAGER_ID = "SELECT u.user_id, u.full_name, "
      + "u.display_name, u.title, u.manager_user_id, u.email, u.org_hierarchy, l.hdfs_name, l.directory_path, "
      + "l.total_size_mb, l.num_of_files, l.jira_key, l.jira_status, l.jira_component "
      + "FROM dir_external_user_info u JOIN log_jira__hdfs_directory_to_owner_map l "
      + "on u.user_id = l.current_assignee_id WHERE u.org_hierarchy like %:managerId%";

  public List<LdapInfo> getLdapInfo() {
    Map<String, Object> params = new HashMap<>();

    List<LdapInfo> ldapInfo = getEntityListBy(GET_LDAP_INFO, LdapInfo.class, params);
    return ldapInfo;
  }

  public List<LdapInfo> getCurrentUserLdapInfo(String userId) {
    Map<String, Object> params = new HashMap<>();
    params.put("userId", userId);

    List<LdapInfo> currentUserInfoList = getEntityListBy(GET_CURRENT_USER_INFO, LdapInfo.class, params);
    return currentUserInfoList;
  }

  public List<LdapInfo> getFirstLevelLdapInfo(String managerId) {
    Map<String, Object> params = new HashMap<>();
    params.put("managerId", managerId);

    List<LdapInfo> firstLevelLdapInfoList = getEntityListBy(GET_FIRST_LEVEL_LDAP_INFO, LdapInfo.class, params);
    return firstLevelLdapInfoList;
  }

  public List<JiraTicket> getUserTicketsByManagerId(String managerId) {
    Map<String, Object> params = new HashMap<>();
    params.put("managerId", managerId);

    List<JiraTicket> userTicketsByManagerList = getEntityListBy(GET_TICKETS_BY_MANAGER_ID, JiraTicket.class, params);
    return userTicketsByManagerList;
  }
}
