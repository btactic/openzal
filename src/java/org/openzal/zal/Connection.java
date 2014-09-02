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

package org.openzal.zal;

import org.openzal.zal.exceptions.ZimbraException;
import com.zimbra.cs.db.DbPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface Connection
{
  PreparedStatement prepareStatement(String sql)
    throws SQLException;

  void close()
    throws ZimbraException;

  /* $if MajorZimbraVersion >= 8 $ */
  public DbPool.DbConnection getProxiedConnection();
  /* $else$
  public DbPool.Connection getProxiedConnection();
  $endif$ */

  void commit()
    throws ZimbraException;

  void rollback() throws ZimbraException;

  void setTransactionIsolation(int transactionRepeatableRead) throws org.openzal.zal.exceptions.ZimbraException;

  void closeResults(ResultSet resultSet);

  void closeStatement(Statement statement);
}
