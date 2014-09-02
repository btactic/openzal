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

import com.zimbra.cs.account.Provisioning;

public class ZECacheEntry
{

  private final Provisioning.CacheEntry mCacheEntry;

  public ZECacheEntry(ZEKey.ZECacheEntryBy id, String string)
  {
    this(new Provisioning.CacheEntry(id.toZimbra(), string));
  }

  public ZECacheEntry(Object cacheEntry)
  {
    mCacheEntry = (Provisioning.CacheEntry)cacheEntry;
  }

  public <T> T toZimbra(Class<T> cls)
  {
    return cls.cast(mCacheEntry);
  }
}
