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

import com.zimbra.cs.account.*;
import org.openzal.zal.exceptions.*;
/* $if ZimbraVersion >= 8.0.0 $ */
import com.zimbra.soap.admin.type.DataSourceType;
import com.zimbra.common.account.ZAttrProvisioning;
/* $else $
import com.zimbra.cs.account.ZAttrProvisioning;
import java.util.Collections;
import com.zimbra.cs.account.DataSource;
/* $endif $ */
import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.util.AccountUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ZEAccount extends ZEEntry
{
  private final Account mAccount;

  public ZEAccount(@NotNull Object account)
  {
    super((Account)account);
    mAccount = (Account)account;
  }

  public ZEAccount(
    String accountName,
    String accountId,
    Map<String, Object> accountAttrs,
    Map emptyMap,
    ZEProvisioning provisioning
  )
  {
    this(
      new Account(
        accountName,
        accountId,
        accountAttrs,
        emptyMap,
        provisioning.toZimbra(Provisioning.class))
    );
  }

  public boolean isFeatureMobilePolicyEnabled()
  {
    return mAccount.isFeatureMobilePolicyEnabled();
  }

  public String getPrefOutOfOfficeReply()
  {
    return mAccount.getPrefOutOfOfficeReply();
  }

  public String getCOSId()
  {
    return mAccount.getCOSId();
  }

  public void addAlias(String alias) throws AlreadyExistsException
  {
    try
    {
      mAccount.addAlias(alias);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public String getSignatureId()
  {
    return mAccount.getSignatureId();
  }

  public long getDomainAdminMaxMailQuota()
  {
    return mAccount.getDomainAdminMaxMailQuota();
  }

  public boolean isIsDelegatedAdminAccount()
  {
    return mAccount.isIsDelegatedAdminAccount();
  }

  public String getMailHost()
  {
    return mAccount.getMailHost();
  }

  public List<ZEDataSource> getAllDataSources() throws NoSuchAccountException
  {
    try
    {
      return ZimbraListWrapper.wrapDataSources(mAccount.getAllDataSources());
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public boolean isIsExternalVirtualAccount()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    return mAccount.isIsExternalVirtualAccount();
    /* $else $
    return false;
    /* $endif $ */
  }

  public boolean isMobileSmartForwardRFC822Enabled()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    return mAccount.isMobileSmartForwardRFC822Enabled();
    /* $else $
    return false;
    /* $endif $ */
  }

  public void setPrefAllowAddressForDelegatedSender(Collection<String> addresses)
    throws ZimbraException
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    try
    {
      mAccount.setPrefAllowAddressForDelegatedSender(addresses.toArray(new String[addresses.size()]));
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $endif $ */
  }

  public List<ZEIdentity> getAllIdentities() throws NoSuchAccountException
  {
    try
    {
       return ZimbraListWrapper.wrapIdentities(mAccount.getAllIdentities());
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public Set<String> getMultiAttrSet(String name)
  {
    return new HashSet<String>(mAccount.getMultiAttrSet(name));
  }

  public Collection<String> getMultiAttr(String name)
  {
    return Arrays.asList(mAccount.getMultiAttr(name));
  }

  public Collection<String> getPrefAllowAddressForDelegatedSender()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    return Arrays.asList(mAccount.getPrefAllowAddressForDelegatedSender());
    /* $else $
    return Collections.emptyList();
    /* $endif $ */
  }

  public boolean isIsSystemResource()
  {
    return mAccount.isIsSystemResource();
  }

  public Collection<String> getChildAccount()
  {
    return Arrays.asList(mAccount.getChildAccount());
  }

  public String getAttr(String name, String defaultValue)
  {
    return mAccount.getAttr(name, defaultValue);
  }

  public void modifyDataSource(String dataSourceId, Map<String, Object> attrs)
    throws NoSuchAccountException
  {
    try
    {
      mAccount.modifyDataSource(dataSourceId, attrs);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public boolean isAccountExternal()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    try
    {
      return mAccount.isAccountExternal();
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $else $
    return false;
    /* $endif $ */
  }

  public ZEIdentity getIdentityByName(String name) throws NoSuchAccountException
  {
    Identity identity;
    try
    {
      identity = mAccount.getIdentityByName(name);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }

    if ( identity == null )
    {
      return null;
    }

    return new ZEIdentity(identity);
  }

  public void modify(Map<String, Object> attrs)
  {
    try
    {
      mAccount.modify(attrs);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public String getId()
  {
    return mAccount.getId();
  }

  public boolean getBooleanAttr(String name, boolean defaultValue)
  {
    return mAccount.getBooleanAttr(name, defaultValue);
  }

  public void setPrefOutOfOfficeReply(String zimbraPrefOutOfOfficeReply)
  {
    try
    {
      mAccount.setPrefOutOfOfficeReply(zimbraPrefOutOfOfficeReply);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public void setIsSystemAccount(boolean zimbraIsSystemAccount)
  {
    /* $if ZimbraVersion  >= 8.0.0 $ */
    try
    {
      mAccount.setIsSystemAccount(zimbraIsSystemAccount);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $endif $ */
  }

  public String getDomainId()
  {
    return mAccount.getDomainId();
  }

  public Date getPrefOutOfOfficeUntilDate()
  {
    return mAccount.getPrefOutOfOfficeUntilDate();
  }

  public String getDisplayName()
  {
    return mAccount.getDisplayName();
  }

  public void unsetSignatureId()
  {
    try
    {
      mAccount.unsetSignatureId();
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public List<ZEDistributionList> getDistributionLists(boolean directOnly, Map<String, String> via)
  {
    try
    {
      return ZimbraListWrapper.wrapDistributionLists(mAccount.getDistributionLists(directOnly, via));
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public boolean isPrefDeleteInviteOnReply()
  {
    return mAccount.isPrefDeleteInviteOnReply();
  }

  public Collection<String> getAliases()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    try
    {
      return Arrays.asList(mAccount.getAliases());
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $else $
    return Collections.emptyList();
    /* $endif $ */
  }

  public void setPrefOutOfOfficeUntilDate(Date zimbraPrefOutOfOfficeUntilDate)
  {
    try
    {
      mAccount.setPrefOutOfOfficeUntilDate(zimbraPrefOutOfOfficeUntilDate);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public ZEPrefExternalSendersType getPrefExternalSendersType()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    return new ZEPrefExternalSendersType(mAccount.getPrefExternalSendersType());
    /* $else $
    throw new UnsupportedOperationException();
    /* $endif $ */
  }

  @Nullable
  public ZECos getCOS() throws NoSuchDomainException
  {
    Cos cos;
    try
    {
      cos = mAccount.getCOS();
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }

    if ( cos == null )
    {
      return null;
    }

    return new ZECos(cos);
  }

  public String getAccountStatusAsString()
  {
    return mAccount.getAccountStatusAsString();
  }

  public List<ZESignature> getAllSignatures() throws NoSuchAccountException
  {
    try
    {
      return ZimbraListWrapper.wrapSignatures(mAccount.getAllSignatures());
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public boolean isMobilePolicyAllowPartialProvisioning()
  {
    return mAccount.isMobilePolicyAllowPartialProvisioning();
  }

  public void authAccount(String password, Protocol proto)
  {
    try
    {
      mAccount.authAccount(password, proto.toZimbra());
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public boolean isIsAdminAccount()
  {
    return mAccount.isIsAdminAccount();
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
    if (!super.equals(o))
    {
      return false;
    }

    ZEAccount account = (ZEAccount) o;

    if (mAccount != null ? !mAccount.equals(account.mAccount) : account.mAccount != null)
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = super.hashCode();
    result = 31 * result + (mAccount != null ? mAccount.hashCode() : 0);
    return result;
  }

  public String getPrefOutOfOfficeExternalReply()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    return mAccount.getPrefOutOfOfficeExternalReply();
    /* $else $
    throw new UnsupportedOperationException();
    /* $endif $ */
  }

  public Date getPrefOutOfOfficeFromDate()
  {
    return mAccount.getPrefOutOfOfficeFromDate();
  }

  public boolean isIsSystemAccount()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    return mAccount.isIsSystemAccount();
    /* $else $
    return false;
    /* $endif $ */
  }

  public String getUid()
  {
    return mAccount.getUid();
  }

  public ZEAccountStatus getAccountStatus()
  {
    ZAttrProvisioning.AccountStatus accountStatus = mAccount.getAccountStatus();

    if ( accountStatus == null )
    {
      return null;
    }

    return new ZEAccountStatus(accountStatus);
  }

  public Object getAttrDefault(String name)
  {
    return mAccount.getAttrDefault(name);
  }

  public Collection<String> getMailAlias()
  {
    return Arrays.asList(mAccount.getMailAlias());
  }

  public Collection<String> getPrefChildVisibleAccount()
  {
    return Arrays.asList(mAccount.getPrefChildVisibleAccount());
  }

  public ZESignature createSignature(String signatureName, Map<String, Object> attrs)
    throws NoSuchAccountException
  {
    try
    {
      return new ZESignature(mAccount.createSignature(signatureName, attrs));
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public Collection<String> getChildVisibleAccount()
  {
    return Arrays.asList(mAccount.getChildVisibleAccount());
  }

  public void setPrefOutOfOfficeExternalReply(String zimbraPrefOutOfOfficeExternalReply)
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    try
    {
      mAccount.setPrefOutOfOfficeExternalReply(zimbraPrefOutOfOfficeExternalReply);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $endif $ */
  }

  public Collection<String> getAllowFromAddress()
  {
    return Arrays.asList(mAccount.getAllowFromAddress());
  }

  public String getPrefFromDisplay()
  {
    return mAccount.getPrefFromDisplay();
  }

  public Set<String> getDistributionLists()
  {
    Set<String> distributionLists;
    try
    {
      distributionLists = mAccount.getDistributionLists();
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }

    HashSet<String> set = new HashSet<String>(distributionLists.size());
    set.addAll(distributionLists);

    return set;
  }

  public Collection<String> getMailDeliveryAddress()
  {
    return Arrays.asList(mAccount.getMailDeliveryAddress());
  }

  public boolean isMobilePolicyAllowNonProvisionableDevices()
  {
    return mAccount.isMobilePolicyAllowNonProvisionableDevices();
  }

  public boolean isFeatureMobileSyncEnabled()
  {
    return mAccount.isFeatureMobileSyncEnabled();
  }

  public void setPrefExternalSendersType(ZEPrefExternalSendersType zimbraPrefExternalSendersType)
  {
    /* $if MajorZimbraVersion >= 8$ */
    try
    {
      mAccount.setPrefExternalSendersType(
        zimbraPrefExternalSendersType.toZimbra(ZAttrProvisioning.PrefExternalSendersType.class)
      );
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $endif$ */
  }

  public void deleteAccount() throws NoSuchAccountException
  {
    try
    {
      mAccount.deleteAccount();
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public boolean isPrefOutOfOfficeReplyEnabled()
  {
    return mAccount.isPrefOutOfOfficeReplyEnabled();
  }

  public String getAccountStatus(ZEProvisioning prov)
  {
    return mAccount.getAccountStatus(
      prov.toZimbra(Provisioning.class)
    );
  }

  public ZESignature getSignatureByName(String key)
  {
    Signature signature;
    try
    {
      signature = mAccount.getSignatureByName(key);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }

    if ( signature == null )
    {
      return null;
    }

    return new ZESignature(signature);
  }

  public String getName()
  {
    return mAccount.getName();
  }

  public void setPrefOutOfOfficeReplyEnabled(boolean zimbraPrefOutOfOfficeReplyEnabled)
  {
    try
    {
      mAccount.setPrefOutOfOfficeReplyEnabled(zimbraPrefOutOfOfficeReplyEnabled);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public void modifySignature(String signatureId, Map<String, Object> attrs)
    throws NoSuchAccountException, NoSuchSignatureException
  {
    try
    {
      mAccount.modifySignature(signatureId, attrs);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public Map<String, Object> getAttrs()
  {
    return new HashMap<String, Object>(mAccount.getAttrs());
  }

  public String getPrefFromAddress()
  {
    return mAccount.getPrefFromAddress();
  }

  public Collection<String> getMobilePolicyUnapprovedInROMApplication()
  {
    /* $if MajorZimbraVersion >= 8$ */
    return Arrays.asList(mAccount.getMobilePolicyUnapprovedInROMApplication());
    /* $else$
    return Collections.emptyList();
    $endif$ */
  }

  public void setPrefOutOfOfficeFromDate(Date zimbraPrefOutOfOfficeFromDate)
  {
    try
    {
      mAccount.setPrefOutOfOfficeFromDate(zimbraPrefOutOfOfficeFromDate);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public Map<String, Object> getAttrs(boolean applyDefaults)
  {
    return new HashMap<String, Object>(mAccount.getAttrs(applyDefaults));
  }

  public ZEDataSource getDataSourceByName(String name) throws NoSuchAccountException
  {
    DataSource dataSource;
    try
    {
      dataSource = mAccount.getDataSourceByName(name);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }

    if ( dataSource == null )
    {
      return null;
    }

    return new ZEDataSource(dataSource);
  }

  public String getAttr(String name)
  {
    return mAccount.getAttr(name);
  }

  public String getDomainName()
  {
    return mAccount.getDomainName();
  }

  public Collection<String> getMobilePolicyApprovedApplicationList()
  {
    /* $if MajorZimbraVersion >= 8$ */
    return Arrays.asList(mAccount.getMobilePolicyApprovedApplicationList());
    /* $else$
    return Collections.emptyList();
    $endif$ */
  }

  public ZEDataSource createDataSource(
    ZEDataSourceType sourceType,
    String sourceName,
    Map<String, Object> attrs,
    boolean passwdAlreadyEncrypted
  )
    throws NoSuchAccountException, TooManyDataSourcesException, DataSourceExistsException
  {
    try
    {
      return new ZEDataSource(
  /* $if ZimbraVersion >= 8.0.0 $ */
      mAccount.createDataSource(
        sourceType.toZimbra(DataSourceType.class),
        sourceName,
        attrs,
        passwdAlreadyEncrypted)
  /* $else $
      mAccount.createDataSource(
      sourceType.toZimbra(DataSource.Type.class),
      sourceName,
      attrs,
      passwdAlreadyEncrypted)
  /* $endif $ */
      );
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public String getAttr(String name, boolean applyDefaults)
  {
    return mAccount.getAttr(name, applyDefaults);
  }

  public void setSignatureId(String signatureId)
  {
    try
    {
      mAccount.setSignatureId(signatureId);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public void setAllowFromAddress(Collection<String> zimbraAllowFromAddress)
  {
    try
    {
      mAccount.setAllowFromAddress(zimbraAllowFromAddress.toArray(new String[zimbraAllowFromAddress.size()]));
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public void setPrefOutOfOfficeExternalReplyEnabled(boolean prefOutOfOfficeExternalReplyEnabled)
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    try
    {
      mAccount.setPrefOutOfOfficeExternalReplyEnabled(prefOutOfOfficeExternalReplyEnabled);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $endif $ */
  }

  public boolean isPrefOutOfOfficeExternalReplyEnabled()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    return mAccount.isPrefOutOfOfficeExternalReplyEnabled();
    /* $else $
    return false;
    /* $endif $ */
  }

  public void removeAlias(String alias)
    throws NoSuchDomainException, NoSuchAliasException
  {
    try
    {
      mAccount.removeAlias(alias);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public ZEIdentity createIdentity(String identityName, Map<String, Object> attrs)
    throws NoSuchAccountException, TooManyIdentitiesException, IdentityExistsException
  {
    try
    {
      return new ZEIdentity(mAccount.createIdentity(identityName, attrs));
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public boolean isAllowAnyFromAddress()
  {
    return mAccount.isAllowAnyFromAddress();
  }

  public boolean isCalendarResource()
  {
    return mAccount.isCalendarResource();
  }

  public String getMail()
  {
    return mAccount.getMail();
  }

  public String getCn()
  {
    return mAccount.getCn();
  }

  public long getLongAttr(String name, long defaultValue)
  {
    return mAccount.getLongAttr(name, defaultValue);
  }

  public <T> T toZimbra(Class<T> cls)
  {
    return cls.cast(mAccount);
  }

  public boolean addressMatchesAccount(String address)
  {
    try
    {
      return AccountUtil.addressMatchesAccount(mAccount, address);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public long getMailQuota()
  {
    /* $if MajorZimbraVersion >= 8 $ */
    try
    {
      return AccountUtil.getEffectiveQuota(mAccount);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $else$
    return mAccount.getMailQuota();
    $endif$ */

  }

  public boolean hasAddress(String address)
  {
    try
    {
      return AccountUtil.addressMatchesAccount(mAccount, address);
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public void setAttrs(Map<String, Object> attrs)
  {
    mAccount.setAttrs(attrs);
  }

  public void setIsDelegatedAdminAccount(boolean zimbraIsDelegatedAdminAccount)
  {
    try
    {
      mAccount.setIsDelegatedAdminAccount(zimbraIsDelegatedAdminAccount);
    } catch(ServiceException se) {
      throw ExceptionWrapper.wrap(se);
    }
  }

  public String getPrefMailDefaultCharset()
  {
    return mAccount.getPrefMailDefaultCharset();
  }
}

