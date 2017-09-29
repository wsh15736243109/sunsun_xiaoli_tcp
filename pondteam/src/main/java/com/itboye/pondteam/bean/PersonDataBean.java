package com.itboye.pondteam.bean;

import android.content.Intent;

import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.logincontroller.LoginController;
import com.itboye.pondteam.logincontroller.LoginedState;
import com.itboye.pondteam.logincontroller.UnLoginState;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;

import java.util.List;

/**
 * Created by itboye on 2016/12/9.
 */

public class PersonDataBean {
    private String uid;
    private String defaultAddress;
    private String birthday;
    private String frozencoin;
    private String sex;
    private String taobaoOpenid;
    private String score;
    private String lastLoginIp;
    private String password;
    private String id;
    private String username;
    private String weixinBind;
    private String idcode;
    private String phoneValidate;
    private String inviteId;
    private String login;
    private String head;
    private String qq;
    private String lastLoginTime;
    private String nickname;
    private String status;
    private String coin;
    private String regTime;
    private String idnumber;
    private String qqOpenid;
    private String emailValidate;
    private String regIp;
    private String exp;
    private String wxopenid;
    private String updateTime;
    private String email;
    private String addUid;
    private String realname;
    private String sinaOpenid;
    private String identityValidate;
    private String mobile;
    private String brokerValidate;
    private String scienerUsername;
    private String baichuanUid;
    private String walletPassword;
    private String stores_id;
    private String is_stores;

    public String getStores_id() {
        return stores_id;
    }

    public void setStores_id(String stores_id) {
        this.stores_id = stores_id;
    }

    public String getIs_stores() {
        return is_stores;
    }

    public void setIs_stores(String is_stores) {
        this.is_stores = is_stores;
    }

    public String getPaySecret() {
        return paySecret;
    }

    public void setPaySecret(String paySecret) {
        this.paySecret = paySecret;
    }

    private String paySecret;

    public String getAlibaichuanId() {
        return alibaichuanId;
    }

    public void setAlibaichuanId(String alibaichuanId) {
        this.alibaichuanId = alibaichuanId;
    }

    private String alibaichuanId;
    private String authCode;

    public String getAutoLoginCode() {
        return autoLoginCode;
    }

    public void setAutoLoginCode(String autoLoginCode) {
        this.autoLoginCode = autoLoginCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public List<ReListInfo> getRolesInfo() {
        return rolesInfo;
    }

    public void setRolesInfo(List<ReListInfo> rolesInfo) {
        this.rolesInfo = rolesInfo;
    }

    private String autoLoginCode;

    private List<ReListInfo> rolesInfo;

    public List<KilInfo> getSkillsInfo() {
        return skillsInfo;
    }

    public void setSkillsInfo(List<KilInfo> skillsInfo) {
        this.skillsInfo = skillsInfo;
    }

    private List<KilInfo> skillsInfo;

    public class ReListInfo {
        String groupId;

        public String getIsAuth() {
            return isAuth;
        }

        public void setIsAuth(String isAuth) {
            this.isAuth = isAuth;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        String isAuth;
    }

    public class KilInfo {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
    }

    public String getWalletPassword() {
        return walletPassword;
    }

    public void setWalletPassword(String walletPassword) {
        this.walletPassword = walletPassword;
    }

    public String getBaichuanUid() {
        return baichuanUid;
    }

    public void setBaichuanUid(String baichuanUid) {
        this.baichuanUid = baichuanUid;
    }

    public String getBrokerValidate() {
        return brokerValidate;
    }

    public void setBrokerValidate(String brokerValidate) {
        this.brokerValidate = brokerValidate;
    }

    public String getScienerUsername() {
        return scienerUsername;
    }

    public void setScienerUsername(String scienerUsername) {
        this.scienerUsername = scienerUsername;
    }

    // private String identity_validate;//'实名身份认证【0未认证,1已认证,2待审核】'
    //
    // public String getIdentity_validate() {
    // return identity_validate;
    // }
    // public void setIdentity_validate(String identity_validate) {
    // this.identity_validate = identity_validate;
    // }
    private String aqCount;

    public String getAqCount() {
        return aqCount;
    }

    public void setAqCount(String aqCount) {
        this.aqCount = aqCount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFrozencoin() {
        return frozencoin;
    }

    public void setFrozencoin(String frozencoin) {
        this.frozencoin = frozencoin;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTaobaoOpenid() {
        return taobaoOpenid;
    }

    public void setTaobaoOpenid(String taobaoOpenid) {
        this.taobaoOpenid = taobaoOpenid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWeixinBind() {
        return weixinBind;
    }

    public void setWeixinBind(String weixinBind) {
        this.weixinBind = weixinBind;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getPhoneValidate() {
        return phoneValidate;
    }

    public void setPhoneValidate(String phoneValidate) {
        this.phoneValidate = phoneValidate;
    }

    public String getInviteId() {
        return inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public String getEmailValidate() {
        return emailValidate;
    }

    public void setEmailValidate(String emailValidate) {
        this.emailValidate = emailValidate;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getWxopenid() {
        return wxopenid;
    }

    public void setWxopenid(String wxopenid) {
        this.wxopenid = wxopenid;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddUid() {
        return addUid;
    }

    public void setAddUid(String addUid) {
        this.addUid = addUid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSinaOpenid() {
        return sinaOpenid;
    }

    public void setSinaOpenid(String sinaOpenid) {
        this.sinaOpenid = sinaOpenid;
    }

    public String getIdentityValidate() {
        return identityValidate;
    }

    public void setIdentityValidate(String identityValidate) {
        this.identityValidate = identityValidate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public void setPersonData(PersonDataBean personDataBean) {
        SPUtils.put(MyApplication.getInstance(), null,Const.IS_LOGINED, personDataBean!=null);
        if (personDataBean!=null) {
            if (personDataBean.getId()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.UID, personDataBean.getId());
            }
//            if (personDataBean.getRoles_info()!=null){
//                SPUtils.put(MyApplication.getInstance(), null,
//                        Const.RELE, personDataBean.getRoles_info().get(0).getGroup_name());
//            }
//            if (personDataBean.getRoles_info()!=null){
//                SPUtils.put(MyApplication.getInstance(), null,
//                        Const.LEIXING, personDataBean.getRoles_info().get(0).getAccount_id());
//            }
            if (personDataBean.getEmail()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.EMAIL, personDataBean.getEmail());
            }
            if (personDataBean.getSex()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.USERNAME, personDataBean.getSex());
            }
            if (personDataBean.getUsername()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.USERNAME, personDataBean.getUsername());
            }
            if (personDataBean.getPassword()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.PASSWORD, personDataBean.getPassword());
            }
            if (personDataBean.getMobile()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.MOBILE, personDataBean.getMobile());
            }
            if (personDataBean.getHead()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.HEAD, personDataBean.getHead());
            }
            if (personDataBean.getNickname()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.NICK, personDataBean.getNickname());
            }
            if (personDataBean.getAutoLoginCode()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.S_ID, personDataBean.getAutoLoginCode());
            }
            if (personDataBean.getSex()!=null){
                SPUtils.put(MyApplication.getInstance(), null,
                        Const.SEX, personDataBean.getSex());
            }
//            if (personDataBean.getSign()!=null){
//                SPUtils.put(MyApplication.getInstance(), null,
//                        Const.SIGN, personDataBean.getSign());
//            }
            Intent intent1 = new Intent("login");// 登陆的广播
            MyApplication.getInstance().sendBroadcast(intent1);

            LoginController.setLoginState(new LoginedState());
        } else {
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.UID, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.RELE, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.EMAIL, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.USERNAME, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.PASSWORD, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.MOBILE, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.HEAD, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.NICK, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.S_ID, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.SEX, "");
            SPUtils.put(MyApplication.getInstance(), null,
                    Const.SIGN, "");

            Intent intent1 = new Intent("exit");// 退出登陆的广播
            MyApplication.getInstance().sendBroadcast(intent1);

            LoginController.setLoginState(new UnLoginState());
        }
    }
    @Override
    public String toString() {
        return "PersonDataBean [uid=" + uid + ", defaultAddress="
                + defaultAddress + ", birthday=" + birthday + ", frozencoin="
                + frozencoin + ", sex=" + sex + ", taobaoOpenid="
                + taobaoOpenid + ", score=" + score + ", lastLoginIp="
                + lastLoginIp + ", password=" + password + ", id=" + id
                + ", username=" + username + ", weixinBind=" + weixinBind
                + ", idcode=" + idcode + ", phoneValidate=" + phoneValidate
                + ", inviteId=" + inviteId + ", login=" + login + ", head="
                + head + ", qq=" + qq + ", lastLoginTime=" + lastLoginTime
                + ", nickname=" + nickname + ", status=" + status + ", coin="
                + coin + ", regTime=" + regTime + ", idnumber=" + idnumber
                + ", qqOpenid=" + qqOpenid + ", emailValidate=" + emailValidate
                + ", regIp=" + regIp + ", exp=" + exp + ", wxopenid="
                + wxopenid + ", updateTime=" + updateTime + ", email=" + email
                + ", addUid=" + addUid + ", realname=" + realname
                + ", sinaOpenid=" + sinaOpenid + ", identityValidate="
                + identityValidate + ", mobile=" + mobile + ", aqCount="
                + aqCount + ",alibaichuanId=" + alibaichuanId + ",authCode="
                + authCode + ",autoLoginCode="
                + autoLoginCode + ",rolesInfo="
                + rolesInfo + ",skillsInfo=" + skillsInfo + ",paySecret="
                + paySecret + "]";
    }

}
