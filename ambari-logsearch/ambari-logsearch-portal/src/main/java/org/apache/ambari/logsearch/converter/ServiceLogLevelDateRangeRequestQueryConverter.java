/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.ambari.logsearch.converter;

import com.google.common.base.Splitter;
import org.apache.ambari.logsearch.common.LogType;
import org.apache.ambari.logsearch.model.request.impl.ServiceGraphRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import javax.inject.Named;

import java.util.List;

import static org.apache.ambari.logsearch.solr.SolrConstants.ServiceLogConstants.LEVEL;
import static org.apache.ambari.logsearch.solr.SolrConstants.ServiceLogConstants.LOGTIME;

@Named
public class ServiceLogLevelDateRangeRequestQueryConverter extends AbstractDateRangeFacetQueryConverter<ServiceGraphRequest>{

  @Override
  public String getDateFieldName() {
    return LOGTIME;
  }

  @Override
  public String getTypeFieldName() {
    return LEVEL;
  }

  @Override
  public SolrQuery convert(ServiceGraphRequest request) {
    SolrQuery solrQuery = super.convert(request);
    if (StringUtils.isNotEmpty(request.getLevel())) {
      List<String> levels = Splitter.on(",").splitToList(request.getLevel());
      solrQuery.addFilterQuery(String.format("%s:(%s)", LEVEL, StringUtils.join(levels, " OR ")));
    }
    return solrQuery;
  }

  @Override
  public LogType getLogType() {
    return LogType.SERVICE;
  }
}
