/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.hadoop.cblock.util;

import org.apache.hadoop.cblock.meta.ContainerDescriptor;
import org.apache.hadoop.cblock.storage.IStorageClient;

import java.io.IOException;

/**
 * This class is the one that directly talks to SCM server.
 *
 * NOTE : this is only a mock class, only to allow testing volume
 * creation without actually creating containers. In real world, need to be
 * replaced with actual container look up calls.
 *
 */
public class MockStorageClient implements IStorageClient {
  private static long currentContainerId = -1;

  /**
   * Ask SCM to get a exclusive container.
   *
   * @return A container descriptor object to locate this container
   * @throws Exception
   */
  public ContainerDescriptor createContainer() throws IOException {
    currentContainerId += 1;
    ContainerLookUpService.addContainer(Long.toString(currentContainerId));
    return ContainerLookUpService.lookUp(Long.toString(currentContainerId));
  }

  /**
   * As this is only a testing class, with all "container" maintained in
   * memory, no need to really delete anything for now.
   * @param containerId
   * @throws IOException
   */
  @Override
  public void deleteContainer(String containerId) throws IOException {

  }

  /**
   * Return reference to an *existing* container with given ID.
   *
   * @param containerId
   * @return
   * @throws IOException
   */
  public ContainerDescriptor getContainer(String containerId)
      throws IOException {
    return ContainerLookUpService.lookUp(containerId);
  }

  @Override
  public long getContainerSize() throws IOException {
    // just return a constant value for now
    return 5L*1024*1024*1024; // 5GB
  }
}