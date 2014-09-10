/*
 * ZAL - The abstraction layer for Zimbra.
 * Copyright (C) 2014 ZeXtras S.r.l.
 *
 * This file is part of ZAL.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, version 2 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ZAL. If not, see <http://www.gnu.org/licenses/>.
 */

package org.openzal.zal.extension;

import org.openzal.zal.log.ZimbraLog;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class Extension implements Comparable<Extension>
{
  private final String         mExtensionClassName;
  private final List<File>     mLibraries;
  private final ZalClassLoader mClassLoader;
  private final ZalExtension   mZalExtension;

  public Extension(
    String extensionClassName,
    List<File> libraries
  )
    throws ClassNotFoundException
  {
    mExtensionClassName = extensionClassName;
    mLibraries = libraries;
    mClassLoader = createClassLoader();
    mZalExtension = createZalExtension();
  }

  private ZalClassLoader createClassLoader()
  {
    List<URL> urls = new ArrayList<URL>(mLibraries.size());

    for( File file : mLibraries )
    {
      try
      {
        urls.add( new URL("jar:file:"+file.getAbsolutePath()+"!/") );
      }
      catch (MalformedURLException e)
      {
        throw new RuntimeException(e);
      }
    }

    return new ZalClassLoader(urls.toArray(new URL[urls.size()]), this.getClass().getClassLoader());
  }

  private ZalExtension createZalExtension() throws ClassNotFoundException
  {
    Class zalExtensionClass = mClassLoader.loadClass(mExtensionClassName);

    Object zalExtension;
    try
    {
      zalExtension = zalExtensionClass.newInstance();
    }
    catch (Exception e)
    {
      throw new ClassNotFoundException("Unable to create extension "+mExtensionClassName,e);
    }

    if( !(zalExtension instanceof ZalExtension) ) {
      throw new ClassNotFoundException("Class "+mExtensionClassName+" does not implement ZalExtension");
    }

    ZalExtension zalExtensionCasted = (ZalExtension)zalExtension;

    String name = zalExtensionCasted.getName();
    if( name == null || name.isEmpty() ) {
      throw new ClassNotFoundException("Class "+mExtensionClassName+".getName() returns an invalid String");
    }

    String buildId = zalExtensionCasted.getBuildId();
    if( buildId == null || buildId.isEmpty() ) {
      throw new ClassNotFoundException("Class "+mExtensionClassName+".getBuildId() returns an invalid String");
    }

    if( buildId.contains("/") ) {
      throw new ClassNotFoundException("Class " + mExtensionClassName + ".getBuildId() contains '/' which is not allowed");
    }

    return zalExtensionCasted;
  }

  public String getExtensionClassName()
  {
    return mExtensionClassName;
  }

  @Override
  public int compareTo(Extension o)
  {
    return mExtensionClassName.compareTo(o.mExtensionClassName);
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    Extension extension = (Extension) o;

    return mExtensionClassName.equals(extension.mExtensionClassName);
  }

  @Override
  public int hashCode()
  {
    return mExtensionClassName.hashCode();
  }

  public void shutdown()
  {
    try
    {
      mZalExtension.shutdown();
    }
    catch (Throwable ex)
    {
      ZimbraLog.extensions.error("Shutdown error in extension "+mExtensionClassName, ex);
    }
  }

  public void start(ZalExtensionController controller, Zimbra zimbra)
  {
    try
    {
      mZalExtension.startup(controller, zimbra);
    }
    catch (Throwable ex)
    {
      ZimbraLog.extensions.error("Startup error in extension "+mExtensionClassName, ex);
    }
  }
}
