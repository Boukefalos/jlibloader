/*
 * Copyright 2012 Adam Murdoch
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.boukefalos.jlibloader.internal;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.github.boukefalos.jlibloader.NativeException;
import com.github.boukefalos.jlibloader.NativeLibraryUnavailableException;

public class NativeLibraryLoader {
    private final Set<String> loaded = new HashSet<String>();
    private final Platform platform;
    private final NativeLibraryLocator nativeLibraryLocator;

    public NativeLibraryLoader(Platform platform, NativeLibraryLocator nativeLibraryLocator) {
        this.platform = platform;
        this.nativeLibraryLocator = nativeLibraryLocator;
    }

    public void load(String libraryGroupName, String libraryName, String libraryFileName) {
        if (loaded.contains(libraryFileName)) {
            return;
        }
        try {
            File libFile = nativeLibraryLocator.find(new LibraryDef(libraryGroupName, libraryName, libraryFileName, platform.getId()));
            if (libFile == null) {
                throw new NativeLibraryUnavailableException(String.format("Native library '%s' is not available for %s.", libraryFileName, platform));
            }
            System.load(libFile.getCanonicalPath());
        } catch (NativeException e) {
            throw e;
        } catch (Throwable t) {
        	t.printStackTrace();
            throw new NativeException(String.format("Failed to load native library '%s' for %s.", libraryFileName, platform), t);
        }
        loaded.add(libraryFileName);
    }
}