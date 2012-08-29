/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package find;

import version.Version;
import list.ListFile;

import java.util.Collection;
import java.io.File;

import  org.apache.commons.collections.CollectionUtils;
import  org.apache.commons.collections.Predicate;

public final class FindFile {
  static {
    Version.register("find");
  }
  
  public static Collection find(File dir, String name) {
    return find(ListFile.list(dir), name);
  }
  
  private static Collection find(Collection files, final String name) {    
    return CollectionUtils.select(files, new Predicate() {
      public boolean evaluate(Object o) {
        return ((File) o).getName().indexOf(name) != -1;
      }
    });
  }
  
  private FindFile() {
  }
}
