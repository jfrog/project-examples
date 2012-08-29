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
package size;

import version.Version;
import java.util.Collection;
import java.util.Iterator;
import java.io.File;

public final class FileSize {
  static {
    Version.register("size");
  }

  public static long totalSize(File dir) {
    return totalSize(list.ListFile.list(dir));
  }
  
  public static long totalSize(Collection files) {
    long total = 0;
    for (Iterator it = files.iterator(); it.hasNext();) {
      File f = (File) it.next();
      total += f.length();
    }
    return total;
  }  
  
  private FileSize() {
  }
}
