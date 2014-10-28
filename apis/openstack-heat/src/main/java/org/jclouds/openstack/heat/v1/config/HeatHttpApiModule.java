/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.heat.v1.config;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.annotation.ClientError;
import org.jclouds.http.annotation.Redirection;
import org.jclouds.http.annotation.ServerError;
import org.jclouds.openstack.heat.v1.HeatApi;
import org.jclouds.openstack.heat.v1.handler.HeatErrorHandler;
import org.jclouds.openstack.v2_0.functions.PresentWhenExtensionAnnotationNamespaceEqualsAnyNamespaceInExtensionsSet;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.rest.config.HttpApiModule;
import org.jclouds.rest.functions.ImplicitOptionalConverter;

import java.net.URI;

/**
 * Configures the Heat connection.
 */

@ConfiguresRestClient
public class HeatHttpApiModule extends HttpApiModule<HeatApi> {

   public HeatHttpApiModule() {

   }


   @Override
   protected void configure() {
      bind(ImplicitOptionalConverter.class).to(PresentWhenExtensionAnnotationNamespaceEqualsAnyNamespaceInExtensionsSet.class);
      super.configure();
   }

   @Provides
   @Singleton
   public Multimap<URI, URI> aliases() {
      return ImmutableMultimap.<URI, URI>builder().build();
   }

   @Override
   protected void bindErrorHandlers() {
      bind(HttpErrorHandler.class).annotatedWith(Redirection.class).to(HeatErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ClientError.class).to(HeatErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ServerError.class).to(HeatErrorHandler.class);
   }
}