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

import org.openzal.zal.exceptions.ExceptionWrapper;
import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.mime.ParsedDocument;

import java.io.IOException;
import java.io.InputStream;

public class ZEParsedDocument
{
  private final ParsedDocument mParsedDocument;

  public ZEParsedDocument(
    InputStream stream,
    String filename,
    String ctype,
    long createdDate,
    String creator,
    String description
  )
    throws IOException
  {
    try
    {
      /* $if MajorZimbraVersion >= 7 $ */
      mParsedDocument = new ParsedDocument(stream, filename, ctype, createdDate, creator, description);
    /* $else$
      mParsedDocument = new ParsedDocument(stream, filename, ctype, createdDate, creator);
    /* $endif$*/
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  protected <T> T toZimbra(Class<T> cls)
  {
    return cls.cast(mParsedDocument);
  }
}
