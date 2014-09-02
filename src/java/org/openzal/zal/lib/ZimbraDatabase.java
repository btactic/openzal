/*
 * ZAL - An abstraction layer for Zimbra.
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

package org.openzal.zal.lib;

import com.zimbra.cs.mailbox.Mailbox;
import org.openzal.zal.Connection;
import org.openzal.zal.ZEItem;
import org.openzal.zal.ZEMailbox;
import org.openzal.zal.ZEVolume;
import org.openzal.zal.ZimbraConnectionWrapper;
import org.openzal.zal.exceptions.ExceptionWrapper;
import org.openzal.zal.exceptions.ZimbraException;
import org.openzal.zal.exceptions.UnableToObtainDBConnectionException;
import com.zimbra.cs.db.DbMailItem;
import com.zimbra.cs.db.DbPool;
import com.zimbra.cs.index.SortBy;
import com.zimbra.cs.mailbox.MailItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.zimbra.cs.db.*;

/* $if MajorZimbraVersion >= 8 $ */
/* $else$
import com.zimbra.cs.store.file.Volume;
$endif$ */

public class ZimbraDatabase
{
  public static List<ZEItem.ZEUnderlyingData> getByType(ZEMailbox mbox, byte type, SortBy sort) throws ZimbraException
  {
    List<MailItem.UnderlyingData> list;
    try
    {
      list = DbMailItem.getByType(mbox.toZimbra(Mailbox.class), ZEItem.convertType(type), sort);
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }

    if( list != null )
    {
      List<ZEItem.ZEUnderlyingData> newList = new ArrayList<ZEItem.ZEUnderlyingData>(list.size());

      for( MailItem.UnderlyingData item : list ){
        newList.add( new ZEItem.ZEUnderlyingData(item) );
      }
      return newList;
    }
    else
    {
      return null;
    }
  }

  @Deprecated
  public static Connection getConnectionLegacy()
    throws ZimbraException
  {
    try
    {
      return new ZimbraConnectionWrapper(DbPool.getConnection());
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  @Deprecated
  public static Connection legacyGetConnection()
    throws ZimbraException
  {
    try
    {
      return new ZimbraConnectionWrapper(DbPool.getConnection());
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public static Object getSynchronizer( ZEMailbox mbox )
  {
/* $if MajorZimbraVersion >= 8 $ */
    return new Object();
/* $else$
    return DbMailItem.getSynchronizer(mbox.getMailbox());
   $endif$ */
  }

  public static Object getSynchronizer()
  {
    return getMailboxSynchronizer();
  }

  public static Object getMailboxSynchronizer()
  {
  /* $if MajorZimbraVersion >= 8 $ */
    return new Object();
  /* $else$
    return DbMailbox.getSynchronizer();
   $endif$ $ */
  }

  public static int setMailboxId(PreparedStatement stmt, ZEMailbox mbox, int pos) throws SQLException
  {
    return DbMailItem.setMailboxId(stmt,
                                   mbox.toZimbra(Mailbox.class),
                                   pos);
  }

  public static Set<String> listAccountIds(Connection conn) throws ZimbraException
  {
    try
    {
      return DbMailbox.listAccountIds(conn.getProxiedConnection());
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public static final String IN_THIS_MAILBOX_AND = DbMailItem.IN_THIS_MAILBOX_AND;

  public static void closeStatement( Statement st )
    throws ZimbraException
  {
    try
    {
      DbPool.closeStatement(st);
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public static void quietCloseStatement( Statement st )
  {
    DbPool.quietCloseStatement(st);
  }

  public static void closeResults( ResultSet res )
    throws ZimbraException
  {
    try
    {
      DbPool.closeResults(res);
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public static String suitableNumberOfVariables(byte[] array)    { return DbUtil.suitableNumberOfVariables(array.length); }
  public static String suitableNumberOfVariables(short[] array)   { return DbUtil.suitableNumberOfVariables(array.length); }
  public static String suitableNumberOfVariables(int[] array)     { return DbUtil.suitableNumberOfVariables(array.length); }
  public static String suitableNumberOfVariables(Object[] array)  { return DbUtil.suitableNumberOfVariables(array.length); }
  public static String suitableNumberOfVariables(Collection<?> c) { return DbUtil.suitableNumberOfVariables(c.size()); }

  public static CurrentVolumes getCurrentVolumes(Connection conn) throws ZimbraException
  {
    DbVolume.CurrentVolumes cv;
    try
    {
      cv = DbVolume.getCurrentVolumes(conn.getProxiedConnection());
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }

    CurrentVolumes myCv = new CurrentVolumes();
    myCv.msgVolId = cv.msgVolId;
    myCv.secondaryMsgVolId = cv.secondaryMsgVolId;
    myCv.indexVolId = cv.indexVolId;

    return myCv;
  }

  public static interface ConnectionProvider
  {
    public Connection getConnection() throws UnableToObtainDBConnectionException;
  }

  public static class CurrentVolumes {
    public short msgVolId          = ZEVolume.ID_NONE;
    public short secondaryMsgVolId = ZEVolume.ID_NONE;
    public short indexVolId        = ZEVolume.ID_NONE;
  }

  public static String getItemTableName(ZEMailbox mbox)
  {
    return "mboxgroup" + mbox.getSchemaGroupId() + ".mail_item";
  }

  public static String getCalendarTableName(ZEMailbox mbox)
  {
    return "mboxgroup" + mbox.getSchemaGroupId() + ".appointment";
  }

  public static String getTombstoneTable(ZEMailbox mbox)
  {
    return "mboxgroup" + mbox.getSchemaGroupId() + ".tombstone";
  }

  public static String getRevisionTableName(ZEMailbox mbox)
  {
    return "mboxgroup" + mbox.getSchemaGroupId() + ".revision";
  }

}
