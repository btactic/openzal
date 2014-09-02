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

package org.openzal.zal.provisioning;

import java.util.HashMap;
import java.util.Map;

public enum ZETargetType
{
  account("Account"),
  calresource("CalendarResource"),
  cos("Cos"),
  dl("DistributionList"),
  domain("Domain"),
  server("Server"),
  xmppcomponent("XMPPComponent"),
  zimlet("Zimlet"),
  config("GlobalConfig"),
  group("DynamicGroup"),
  global("GlobalGrant");

  private static Map<String, ZETargetType> TARGET_TYPES = new HashMap<String, ZETargetType>()
  {{
    put("account", account);
    put("CalendarResource", calresource);
    put("Cos", cos);
    put("DistributionList", dl);
    put("Domain", domain);
    put("Server", server);
    put("XMPPComponent", xmppcomponent);
    put("Zimlet", zimlet);
    put("GlobalConfig", config);
    put("DynamicGroup", group);
    put("GlobalGrant", global);
  }};

  private final String mType;

  ZETargetType(String type)
  {
    mType = type;
  }

  public String getCode()
  {
    return mType;
  }

  public static ZETargetType fromString(String type)
  {
    return TARGET_TYPES.get(type);
  }
}
