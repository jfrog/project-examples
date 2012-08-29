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
package console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Arrays;
import java.lang.reflect.Method;


public final class Main {
    private static final Collection QUIT_COMMANDS = 
        Arrays.asList(new String[] {"quit", "q", "exit"});
    private static final Collection HELP_COMMANDS = 
        Arrays.asList(new String[] {"help", "h", "?"});

    public static void main(String[] a) throws Exception {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      while (true) {
        System.out.print("> ");
        String command = in.readLine().trim();
        if (QUIT_COMMANDS.contains(command)) {
          return;
        }
        if (HELP_COMMANDS.contains(command)) {
          help();
          continue;
        }
        String[] split = command.split(" ");
        if (split.length == 0) {
          error(command);
          continue;
        }
        
        try {
          String[] args = new String[split.length - 1];
          System.arraycopy(split, 1, args, 0, args.length);
          Class cl = Class.forName(split[0] + ".Main");
          Method m = cl.getMethod("main", new Class[] {String[].class});
          m.invoke(null, new Object[] {args});
        } catch (Exception ex) {
          error(command);
          continue;
        }
      }
    }
    
    private static void help() {
      System.out.println("available commands:");
      System.out.println("\tquit: quit the console");
      System.out.println("\thelp: displays this message");
      System.out.println("\tlist -dir <dir>: list files in given directory");
      System.out.println("\tfind -dir <dir> -name <name>: list files with given name in given dir");
      System.out.println("\tsizewhere -dir <dir> -name <name>: "
           + "compute total size of files with given name in given dir");
      System.out.println("\thelp: displays this message");
    }
            
    private static void error(String command) {
      System.out.println("unknown command " + command);
      System.out.println("type ? for help");
    }

    private Main() {
    }
}
