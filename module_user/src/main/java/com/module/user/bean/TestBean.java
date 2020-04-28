package com.module.user.bean;

import java.util.List;

/**
 * Created by wangwei on 2019/5/15.
 */

public class TestBean {


        public static class MatchmakerEvaluationBean {
            /**
             * workerId : 2021823
             * workerName : 廖彪
             * avatar : http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssaZrBI6SfBYWGSzan81jNapLLOOA5kJfWXuWky0u12jfAtHvLShgx5zE/0
             * evaluationContent : 1.身材高挑高颜值，书香门第，独立成熟，性格直率。
             2.活泼可爱设计师妹
             */

            private int workerId;
            private String workerName;
            private String avatar;
            private String evaluationContent;

            public int getWorkerId() {
                return workerId;
            }

            public void setWorkerId(int workerId) {
                this.workerId = workerId;
            }

            public String getWorkerName() {
                return workerName;
            }

            public void setWorkerName(String workerName) {
                this.workerName = workerName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getEvaluationContent() {
                return evaluationContent;
            }

            public void setEvaluationContent(String evaluationContent) {
                this.evaluationContent = evaluationContent;
            }
        }

        public static class OfflineShopRelationBean {
            /**
             * shopId : 58
             * shopName : 深圳会展中心店E
             * deptId : 269
             * deptName : 深圳会展中心店
             * shopShortName : 深圳E
             * shopShortName2 : 深圳
             * deptArea : 1
             * selectCityArea : 4
             * shopIndex : 7
             * shopArea : 南区
             * receiptPrefix : SZE
             * ministerId : 2018599
             * seniorDirector : 4091
             * areaDirector : 6223
             * seniorMinister : 2010664
             * hnDirector : 2018
             * deptAddress : 深圳市福田区福华三路与金田路交汇处星河发展中心9层1、8、9、10、11单元
             * deptPics : http://photo.zastatic.com/photo/activity/1504236537739.jpg
             * businessHours : 10:30~21:00
             * latitude : 22.5385122128482
             * longitude : 114.06814092642088
             */

            private int shopId;
            private String shopName;
            private int deptId;
            private String deptName;
            private String shopShortName;
            private String shopShortName2;
            private int deptArea;
            private int selectCityArea;
            private int shopIndex;
            private String shopArea;
            private String receiptPrefix;
            private int ministerId;
            private int seniorDirector;
            private int areaDirector;
            private int seniorMinister;
            private int hnDirector;
            private String deptAddress;
            private String deptPics;
            private String businessHours;
            private String latitude;
            private String longitude;

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public int getDeptId() {
                return deptId;
            }

            public void setDeptId(int deptId) {
                this.deptId = deptId;
            }

            public String getDeptName() {
                return deptName;
            }

            public void setDeptName(String deptName) {
                this.deptName = deptName;
            }

            public String getShopShortName() {
                return shopShortName;
            }

            public void setShopShortName(String shopShortName) {
                this.shopShortName = shopShortName;
            }

            public String getShopShortName2() {
                return shopShortName2;
            }

            public void setShopShortName2(String shopShortName2) {
                this.shopShortName2 = shopShortName2;
            }

            public int getDeptArea() {
                return deptArea;
            }

            public void setDeptArea(int deptArea) {
                this.deptArea = deptArea;
            }

            public int getSelectCityArea() {
                return selectCityArea;
            }

            public void setSelectCityArea(int selectCityArea) {
                this.selectCityArea = selectCityArea;
            }

            public int getShopIndex() {
                return shopIndex;
            }

            public void setShopIndex(int shopIndex) {
                this.shopIndex = shopIndex;
            }

            public String getShopArea() {
                return shopArea;
            }

            public void setShopArea(String shopArea) {
                this.shopArea = shopArea;
            }

            public String getReceiptPrefix() {
                return receiptPrefix;
            }

            public void setReceiptPrefix(String receiptPrefix) {
                this.receiptPrefix = receiptPrefix;
            }

            public int getMinisterId() {
                return ministerId;
            }

            public void setMinisterId(int ministerId) {
                this.ministerId = ministerId;
            }

            public int getSeniorDirector() {
                return seniorDirector;
            }

            public void setSeniorDirector(int seniorDirector) {
                this.seniorDirector = seniorDirector;
            }

            public int getAreaDirector() {
                return areaDirector;
            }

            public void setAreaDirector(int areaDirector) {
                this.areaDirector = areaDirector;
            }

            public int getSeniorMinister() {
                return seniorMinister;
            }

            public void setSeniorMinister(int seniorMinister) {
                this.seniorMinister = seniorMinister;
            }

            public int getHnDirector() {
                return hnDirector;
            }

            public void setHnDirector(int hnDirector) {
                this.hnDirector = hnDirector;
            }

            public String getDeptAddress() {
                return deptAddress;
            }

            public void setDeptAddress(String deptAddress) {
                this.deptAddress = deptAddress;
            }

            public String getDeptPics() {
                return deptPics;
            }

            public void setDeptPics(String deptPics) {
                this.deptPics = deptPics;
            }

            public String getBusinessHours() {
                return businessHours;
            }

            public void setBusinessHours(String businessHours) {
                this.businessHours = businessHours;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }

        public static class MemberPhotoBean {
            /**
             * autoId : null
             * uin : null
             * memberId : null
             * updateChannel : null
             * type : null
             * workerId : null
             * workerName : null
             * showFileName : null
             * smallFileName : https://static.jiebao.zhenai.com/p/8186187125/201905-dce3c6fd_e6c4_4bed_8468_29fe5ac89453..jpg?imageView2/0/w/800/h/800
             * realFileName : null
             * middleFileName : null
             * channel : null
             * remark : null
             * effective : null
             * createTime : null
             * updateTime : null
             * width : null
             * height : null
             * size : null
             * checkStatus : null
             * checkItemsId : null
             * miniCheckStatus : null
             * rejectReason : null
             * defaultFlag : false
             */

            private Object autoId;
            private Object uin;
            private Object memberId;
            private Object updateChannel;
            private Object type;
            private Object workerId;
            private Object workerName;
            private Object showFileName;
            private String smallFileName;
            private Object realFileName;
            private Object middleFileName;
            private Object channel;
            private Object remark;
            private Object effective;
            private Object createTime;
            private Object updateTime;
            private Object width;
            private Object height;
            private Object size;
            private Object checkStatus;
            private Object checkItemsId;
            private Object miniCheckStatus;
            private Object rejectReason;
            private boolean defaultFlag;

            public Object getAutoId() {
                return autoId;
            }

            public void setAutoId(Object autoId) {
                this.autoId = autoId;
            }

            public Object getUin() {
                return uin;
            }

            public void setUin(Object uin) {
                this.uin = uin;
            }

            public Object getMemberId() {
                return memberId;
            }

            public void setMemberId(Object memberId) {
                this.memberId = memberId;
            }

            public Object getUpdateChannel() {
                return updateChannel;
            }

            public void setUpdateChannel(Object updateChannel) {
                this.updateChannel = updateChannel;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public Object getWorkerId() {
                return workerId;
            }

            public void setWorkerId(Object workerId) {
                this.workerId = workerId;
            }

            public Object getWorkerName() {
                return workerName;
            }

            public void setWorkerName(Object workerName) {
                this.workerName = workerName;
            }

            public Object getShowFileName() {
                return showFileName;
            }

            public void setShowFileName(Object showFileName) {
                this.showFileName = showFileName;
            }

            public String getSmallFileName() {
                return smallFileName;
            }

            public void setSmallFileName(String smallFileName) {
                this.smallFileName = smallFileName;
            }

            public Object getRealFileName() {
                return realFileName;
            }

            public void setRealFileName(Object realFileName) {
                this.realFileName = realFileName;
            }

            public Object getMiddleFileName() {
                return middleFileName;
            }

            public void setMiddleFileName(Object middleFileName) {
                this.middleFileName = middleFileName;
            }

            public Object getChannel() {
                return channel;
            }

            public void setChannel(Object channel) {
                this.channel = channel;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public Object getEffective() {
                return effective;
            }

            public void setEffective(Object effective) {
                this.effective = effective;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public Object getWidth() {
                return width;
            }

            public void setWidth(Object width) {
                this.width = width;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }

            public Object getSize() {
                return size;
            }

            public void setSize(Object size) {
                this.size = size;
            }

            public Object getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(Object checkStatus) {
                this.checkStatus = checkStatus;
            }

            public Object getCheckItemsId() {
                return checkItemsId;
            }

            public void setCheckItemsId(Object checkItemsId) {
                this.checkItemsId = checkItemsId;
            }

            public Object getMiniCheckStatus() {
                return miniCheckStatus;
            }

            public void setMiniCheckStatus(Object miniCheckStatus) {
                this.miniCheckStatus = miniCheckStatus;
            }

            public Object getRejectReason() {
                return rejectReason;
            }

            public void setRejectReason(Object rejectReason) {
                this.rejectReason = rejectReason;
            }

            public boolean isDefaultFlag() {
                return defaultFlag;
            }

            public void setDefaultFlag(boolean defaultFlag) {
                this.defaultFlag = defaultFlag;
            }
        }

        public static class MyHeartTaDTOBean {
            public static class ObjectTagListBean {
                private int tagId;
                private String tagName;
                private String tagTypeDesc;

                public int getTagId() {
                    return tagId;
                }

                public void setTagId(int tagId) {
                    this.tagId = tagId;
                }



                public String getTagName() {
                    return tagName;
                }

                public void setTagName(String tagName) {
                    this.tagName = tagName;
                }
                public String getTagTypeDesc() {
                    return tagTypeDesc;
                }

                public void setTagTypeDesc(String tagTypeDesc) {
                    this.tagTypeDesc = tagTypeDesc;
                }
            }
            private String objectContent;
            private List<ObjectTagListBean> objectTagList;

            public String getObjectContent() {
                return objectContent;
            }

            public void setObjectContent(String objectContent) {
                this.objectContent = objectContent;
            }

            public List<ObjectTagListBean> getObjectTagList() {
                return objectTagList;
            }

            public void setObjectTagList(List<ObjectTagListBean> objectTagList) {
                this.objectTagList = objectTagList;
            }

        }

        public static class VoiceBean {
            /**
             * voice : https://static.jiebao.zhenai.com/p/8186187125/201904-3e833efc_677d_49aa_a46d_1ef133b3ab49.mp3
             * duration : 12
             * checkStatus : null
             * rejectReason : null
             */

            private String voice;
            private int duration;
            private Object checkStatus;
            private Object rejectReason;

            public String getVoice() {
                return voice;
            }

            public void setVoice(String voice) {
                this.voice = voice;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public Object getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(Object checkStatus) {
                this.checkStatus = checkStatus;
            }

            public Object getRejectReason() {
                return rejectReason;
            }

            public void setRejectReason(Object rejectReason) {
                this.rejectReason = rejectReason;
            }
        }

        public static class ChildrenInfoListBean {
            /**
             * childrenBelong : 1
             * childrenSex : 0
             * childrenAge : 1
             */

            private int childrenBelong;
            private int childrenSex;
            private int childrenAge;

            public int getChildrenBelong() {
                return childrenBelong;
            }

            public void setChildrenBelong(int childrenBelong) {
                this.childrenBelong = childrenBelong;
            }

            public int getChildrenSex() {
                return childrenSex;
            }

            public void setChildrenSex(int childrenSex) {
                this.childrenSex = childrenSex;
            }

            public int getChildrenAge() {
                return childrenAge;
            }

            public void setChildrenAge(int childrenAge) {
                this.childrenAge = childrenAge;
            }
        }

        public static class HouseInfoDescListBean {
            /**
             * houseCityDesc : 广东深圳
             * mortgageDesc : 全款
             * houseCity : 10101201
             * mortgage : 0
             */

            private String houseCityDesc;
            private String mortgageDesc;
            private int houseCity;
            private int mortgage;

            public String getHouseCityDesc() {
                return houseCityDesc;
            }

            public void setHouseCityDesc(String houseCityDesc) {
                this.houseCityDesc = houseCityDesc;
            }

            public String getMortgageDesc() {
                return mortgageDesc;
            }

            public void setMortgageDesc(String mortgageDesc) {
                this.mortgageDesc = mortgageDesc;
            }

            public int getHouseCity() {
                return houseCity;
            }

            public void setHouseCity(int houseCity) {
                this.houseCity = houseCity;
            }

            public int getMortgage() {
                return mortgage;
            }

            public void setMortgage(int mortgage) {
                this.mortgage = mortgage;
            }
        }

        public static class MemberPhotosBean {
            /**
             * autoId : 13659
             * uin : 1214213250
             * memberId : 1214213250
             * updateChannel : 0
             * type : 8
             * workerId : 2021823
             * workerName : 廖彪
             * showFileName : http://static.jiebao.zhenai.com/p/8186187125/201905-a82cd43f_cb6a_42e0_9444_6d1849d9d17c..jpg?imageView2/0/w/800/h/800
             * smallFileName : http://static.jiebao.zhenai.com/p/8186187125/201905-a82cd43f_cb6a_42e0_9444_6d1849d9d17c..jpg?imageView2/0/w/800/h/800
             * realFileName : wx8e33741932cd0081.o6zAJs4LiV2RITH2DZV897wvQIpo.CERvEvM3OUAg11d2849ccfbd004b1ff6cdb3357eb8d4.jpg
             * middleFileName : http://static.jiebao.zhenai.com/p/8186187125/201905-a82cd43f_cb6a_42e0_9444_6d1849d9d17c..jpg?imageView2/0/w/800/h/800
             * channel : 4
             * remark :
             * effective : 1
             * createTime : 2019-05-15 14:12:50
             * updateTime : 2019-05-15 14:15:54
             * width : 0
             * height : 0
             * size : 0
             * checkStatus : 1
             * checkItemsId : null
             * miniCheckStatus : 1
             * rejectReason :
             * defaultFlag : false
             */

            private int autoId;
            private int uin;
            private int memberId;
            private int updateChannel;
            private int type;
            private int workerId;
            private String workerName;
            private String showFileName;
            private String smallFileName;
            private String realFileName;
            private String middleFileName;
            private int channel;
            private String remark;
            private int effective;
            private String createTime;
            private String updateTime;
            private int width;
            private int height;
            private int size;
            private int checkStatus;
            private Object checkItemsId;
            private int miniCheckStatus;
            private String rejectReason;
            private boolean defaultFlag;

            public int getAutoId() {
                return autoId;
            }

            public void setAutoId(int autoId) {
                this.autoId = autoId;
            }

            public int getUin() {
                return uin;
            }

            public void setUin(int uin) {
                this.uin = uin;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getUpdateChannel() {
                return updateChannel;
            }

            public void setUpdateChannel(int updateChannel) {
                this.updateChannel = updateChannel;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getWorkerId() {
                return workerId;
            }

            public void setWorkerId(int workerId) {
                this.workerId = workerId;
            }

            public String getWorkerName() {
                return workerName;
            }

            public void setWorkerName(String workerName) {
                this.workerName = workerName;
            }

            public String getShowFileName() {
                return showFileName;
            }

            public void setShowFileName(String showFileName) {
                this.showFileName = showFileName;
            }

            public String getSmallFileName() {
                return smallFileName;
            }

            public void setSmallFileName(String smallFileName) {
                this.smallFileName = smallFileName;
            }

            public String getRealFileName() {
                return realFileName;
            }

            public void setRealFileName(String realFileName) {
                this.realFileName = realFileName;
            }

            public String getMiddleFileName() {
                return middleFileName;
            }

            public void setMiddleFileName(String middleFileName) {
                this.middleFileName = middleFileName;
            }

            public int getChannel() {
                return channel;
            }

            public void setChannel(int channel) {
                this.channel = channel;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getEffective() {
                return effective;
            }

            public void setEffective(int effective) {
                this.effective = effective;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(int checkStatus) {
                this.checkStatus = checkStatus;
            }

            public Object getCheckItemsId() {
                return checkItemsId;
            }

            public void setCheckItemsId(Object checkItemsId) {
                this.checkItemsId = checkItemsId;
            }

            public int getMiniCheckStatus() {
                return miniCheckStatus;
            }

            public void setMiniCheckStatus(int miniCheckStatus) {
                this.miniCheckStatus = miniCheckStatus;
            }

            public String getRejectReason() {
                return rejectReason;
            }

            public void setRejectReason(String rejectReason) {
                this.rejectReason = rejectReason;
            }

            public boolean isDefaultFlag() {
                return defaultFlag;
            }

            public void setDefaultFlag(boolean defaultFlag) {
                this.defaultFlag = defaultFlag;
            }
        }

        public static class FamilyListBean {
            /**
             * relationshipDesc : 母亲
             * companyOrJobDesc : IT工程师
             * territoryDesc : 江苏徐州
             */

            private String relationshipDesc;
            private String companyOrJobDesc;
            private String territoryDesc;

            public String getRelationshipDesc() {
                return relationshipDesc;
            }

            public void setRelationshipDesc(String relationshipDesc) {
                this.relationshipDesc = relationshipDesc;
            }

            public String getCompanyOrJobDesc() {
                return companyOrJobDesc;
            }

            public void setCompanyOrJobDesc(String companyOrJobDesc) {
                this.companyOrJobDesc = companyOrJobDesc;
            }

            public String getTerritoryDesc() {
                return territoryDesc;
            }

            public void setTerritoryDesc(String territoryDesc) {
                this.territoryDesc = territoryDesc;
            }
        }

        /**
         * uin : 1214213250
         * memberId : 1214213250
         * nickName : Grace
         * trueName : 谢女士
         * updateChannel : 0
         * workProvince : 0
         * workCity : 10101201
         * sex : 1
         * education : 5
         * job : null
         * income : 8
         * quoteIncome : 0
         * hownTown : 0
         * marriage : 10
         * marriageMark : null
         * children : 1
         * childrenMark : null
         * house : 1
         * houseMark : null
         * car : 1
         * carMark : null
         * pwork : 0
         * pworkMark : null
         * baseInfo : null
         * singleReason : null
         * urgentMotive : null
         * mateCondition : null
         * visitStoreReason : null
         * matchAdvice : null
         * remark : null
         * animals : 1
         * constellation : 7
         * belief : -1
         * family : -1
         * isSmoking : -1
         * isDrinking : -1
         * birthday : 1996-09-30 00:00:00
         * height : 168
         * lastUpdateTime : 2019-05-15 14:19:56
         * lastUpdateWorker : 2021823
         * passiveLevel :
         * idCardNo : 44162319960930062X
         * validatedFlag : 0
         * addressArea : 10101201
         * occupation : 706
         * addressDetail : null
         * hometownCode : 10101201
         * homemarkCode : 10103000
         * normalEducation : 5
         * incomeAmount : 25000
         * defaultPhoto : 10999233296816712.png
         * channelType : 0
         * createTime : 2018-07-29 18:37:34
         * registerTime : 2018-07-12 21:25:02
         * lastLoginTime : 2019-02-10 12:19:39
         * loginCount : 139
         * lastModifyIP : 183.62.134.100
         * lastModifyWorkerId : 2021823
         * lastModifyWorkerName : 廖彪
         * childrenCount : 3
         * childrenInfo : [{"childrenAge":1,"childrenBelong":1,"childrenSex":0},{"childrenAge":2,"childrenBelong":0,"childrenSex":1},{"childrenAge":3,"childrenBelong":1,"childrenSex":0}]
         * houseCount : 3
         * houseInfo : [{"houseCity":10101201,"mortgage":0},{"houseCity":10118016,"mortgage":-1},{"houseCity":10112001,"mortgage":1}]
         * school : -1
         * returnee : -1
         * companyNature : 7
         * personality : 1,3
         * interest : 2,7,12
         * emotionalDef :
         * convenienceTime : 2,3,4,5,6,7
         * memberAssets : 0
         * serviceEvaluation :
         * workCityDesc : 广东深圳
         * workerCityDesc : 广东深圳
         * educationDesc : 大学本科
         * homeTownDesc : 广东深圳
         * homeTownPeoPleDesc : 广东深圳人
         * marriageDesc : 未婚先育
         * occupationDesc : 外贸专员
         * incomeDesc : 25000元
         * age : 22
         * constellationDesc : 天秤座
         * animalsDesc : 鼠
         * houseDesc : 有
         * carDesc : 已购车
         * childrenDesc : null
         * childrenInfoList : [{"childrenBelong":1,"childrenSex":0,"childrenAge":1},{"childrenBelong":0,"childrenSex":1,"childrenAge":2},{"childrenBelong":1,"childrenSex":0,"childrenAge":3}]
         * houseInfoDescList : [{"houseCityDesc":"广东深圳","mortgageDesc":"全款","houseCity":10101201,"mortgage":0},{"houseCityDesc":"江苏淮安","mortgageDesc":"","houseCity":10118016,"mortgage":-1},{"houseCityDesc":"河北石家庄","mortgageDesc":"按揭","houseCity":10112001,"mortgage":1}]
         * schoolDesc :
         * companyNatureDesc : 私营企业
         * personalityDesc : 活泼开放,理智冷静
         * interestDesc : 旅游,舞蹈,厨艺
         * emotionalDefDesc :
         * addressAreaDesc : 广东深圳
         * homemarkCodeDesc : 上海
         * convenienceTimeDesc : 周二,周三,周四,周五,周六,周日
         * incomeNew : 0
         * educationNew : 0
         * birthdayNew : 0
         * nextAppointmentTime : null
         * recommendReason : null
         * matchmakerEvaluation : {"workerId":2021823,"workerName":"廖彪","avatar":"http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssaZrBI6SfBYWGSzan81jNapLLOOA5kJfWXuWky0u12jfAtHvLShgx5zE/0","evaluationContent":"1.身材高挑高颜值，书香门第，独立成熟，性格直率。\n2.活泼可爱设计师妹"}
         * offlineShopRelation : {"shopId":58,"shopName":"深圳会展中心店E","deptId":269,"deptName":"深圳会展中心店","shopShortName":"深圳E","shopShortName2":"深圳","deptArea":1,"selectCityArea":4,"shopIndex":7,"shopArea":"南区","receiptPrefix":"SZE","ministerId":2018599,"seniorDirector":4091,"areaDirector":6223,"seniorMinister":2010664,"hnDirector":2018,"deptAddress":"深圳市福田区福华三路与金田路交汇处星河发展中心9层1、8、9、10、11单元","deptPics":"http://photo.zastatic.com/photo/activity/1504236537739.jpg","businessHours":"10:30~21:00","latitude":"22.5385122128482","longitude":"114.06814092642088"}
         * memberPhoto : {"autoId":null,"uin":null,"memberId":null,"updateChannel":null,"type":null,"workerId":null,"workerName":null,"showFileName":null,"smallFileName":"https://static.jiebao.zhenai.com/p/8186187125/201905-dce3c6fd_e6c4_4bed_8468_29fe5ac89453..jpg?imageView2/0/w/800/h/800","realFileName":null,"middleFileName":null,"channel":null,"remark":null,"effective":null,"createTime":null,"updateTime":null,"width":null,"height":null,"size":null,"checkStatus":null,"checkItemsId":null,"miniCheckStatus":null,"rejectReason":null,"defaultFlag":false}
         * introduceContent : 多的是南辕北辙，少的是殊途同归，我将于茫茫人海中寻找我唯一之灵魂伴侣，得之，我幸，不得，我命.
         非诚勿扰!
         * memberPhotos : [{"autoId":13659,"uin":1214213250,"memberId":1214213250,"updateChannel":0,"type":8,"workerId":2021823,"workerName":"廖彪","showFileName":"http://static.jiebao.zhenai.com/p/8186187125/201905-a82cd43f_cb6a_42e0_9444_6d1849d9d17c..jpg?imageView2/0/w/800/h/800","smallFileName":"http://static.jiebao.zhenai.com/p/8186187125/201905-a82cd43f_cb6a_42e0_9444_6d1849d9d17c..jpg?imageView2/0/w/800/h/800","realFileName":"wx8e33741932cd0081.o6zAJs4LiV2RITH2DZV897wvQIpo.CERvEvM3OUAg11d2849ccfbd004b1ff6cdb3357eb8d4.jpg","middleFileName":"http://static.jiebao.zhenai.com/p/8186187125/201905-a82cd43f_cb6a_42e0_9444_6d1849d9d17c..jpg?imageView2/0/w/800/h/800","channel":4,"remark":"","effective":1,"createTime":"2019-05-15 14:12:50","updateTime":"2019-05-15 14:15:54","width":0,"height":0,"size":0,"checkStatus":1,"checkItemsId":null,"miniCheckStatus":1,"rejectReason":"","defaultFlag":false},{"autoId":13658,"uin":1214213250,"memberId":1214213250,"updateChannel":0,"type":8,"workerId":2021823,"workerName":"廖彪","showFileName":"http://static.jiebao.zhenai.com/p/8186187125/201905-bbf4f653_2450_4024_aea8_2eae05932b8c..jpg?imageView2/0/w/800/h/800","smallFileName":"http://static.jiebao.zhenai.com/p/8186187125/201905-bbf4f653_2450_4024_aea8_2eae05932b8c..jpg?imageView2/0/w/800/h/800","realFileName":"wx8e33741932cd0081.o6zAJs4LiV2RITH2DZV897wvQIpo.wAKjcXnZGrXo93a29f329ff40bc67cd036c08ef14eb9.jpg","middleFileName":"http://static.jiebao.zhenai.com/p/8186187125/201905-bbf4f653_2450_4024_aea8_2eae05932b8c..jpg?imageView2/0/w/800/h/800","channel":4,"remark":"","effective":1,"createTime":"2019-05-15 14:12:47","updateTime":"2019-05-15 14:15:54","width":0,"height":0,"size":0,"checkStatus":1,"checkItemsId":null,"miniCheckStatus":1,"rejectReason":"","defaultFlag":false},{"autoId":13657,"uin":1214213250,"memberId":1214213250,"updateChannel":0,"type":16,"workerId":2021823,"workerName":"廖彪","showFileName":"http://static.jiebao.zhenai.com/p/8186187125/201905-dce3c6fd_e6c4_4bed_8468_29fe5ac89453..jpg?imageView2/0/w/800/h/800","smallFileName":"http://static.jiebao.zhenai.com/p/8186187125/201905-dce3c6fd_e6c4_4bed_8468_29fe5ac89453..jpg?imageView2/0/w/800/h/800","realFileName":"wx8e33741932cd0081.o6zAJs4LiV2RITH2DZV897wvQIpo.2qVyFvJLOD6Za233aa74be0d3f6894b2d16351d3cd6f.jpg","middleFileName":"http://static.jiebao.zhenai.com/p/8186187125/201905-dce3c6fd_e6c4_4bed_8468_29fe5ac89453..jpg?imageView2/0/w/800/h/800","channel":4,"remark":"","effective":1,"createTime":"2019-05-15 14:09:14","updateTime":"2019-05-15 14:10:02","width":0,"height":0,"size":0,"checkStatus":1,"checkItemsId":null,"miniCheckStatus":1,"rejectReason":"","defaultFlag":false}]
         * myHeartTaDTO : {"objectTags":null,"objectContent":"滴哦whats也和自摸rooms我是恶心我婆婆破功花花世界哈萨克A旧数据HAA和","createTime":null,"checkStatus":null,"rejectReason":null,"objectTagList":[{"tagId":41,"tagSex":null,"tagType":null,"tagName":"兴趣广泛","createTime":null,"updateTime":null,"tagTypeDesc":"兴趣"},{"tagId":46,"tagSex":null,"tagType":null,"tagName":"喜欢篮球","createTime":null,"updateTime":null,"tagTypeDesc":"兴趣"},{"tagId":47,"tagSex":null,"tagType":null,"tagName":"酷爱户外运动","createTime":null,"updateTime":null,"tagTypeDesc":"兴趣"},{"tagId":50,"tagSex":null,"tagType":null,"tagName":"会弹吉他","createTime":null,"updateTime":null,"tagTypeDesc":"兴趣"},{"tagId":51,"tagSex":null,"tagType":null,"tagName":"会做饭","createTime":null,"updateTime":null,"tagTypeDesc":"兴趣"}],"checkItemsId":null}
         * idealLife : 你orz破婆婆swim婆婆婆婆going，打theoryJS容易外婆哦poor你呢？，你oops我哥哥弟妹破一定
         * idealLifeTagList : null
         * voice : {"voice":"https://static.jiebao.zhenai.com/p/8186187125/201904-3e833efc_677d_49aa_a46d_1ef133b3ab49.mp3","duration":12,"checkStatus":null,"rejectReason":null}
         * checkStatus : 1
         * familyList : [{"relationshipDesc":"母亲","companyOrJobDesc":"IT工程师","territoryDesc":"江苏徐州"},{"relationshipDesc":"兄弟","companyOrJobDesc":"通信/电子","territoryDesc":"江苏南通"},{"relationshipDesc":"姐妹","companyOrJobDesc":"车间主任","territoryDesc":"江苏常州"},{"relationshipDesc":"奶奶","companyOrJobDesc":"客服专员","territoryDesc":"江苏南通"},{"relationshipDesc":"爷爷","companyOrJobDesc":"通信/电子","territoryDesc":"江苏宿迁泗阳"}]
         * dddate : 2018-08-17 15:18:10
         */

        private int uin;
        private int memberId;
        private String nickName;
        private String trueName;
        private int updateChannel;
        private int workProvince;
        private int workCity;
        private int sex;
        private int education;
        private Object job;
        private int income;
        private int quoteIncome;
        private int hownTown;
        private int marriage;
        private Object marriageMark;
        private int children;
        private Object childrenMark;
        private int house;
        private Object houseMark;
        private int car;
        private Object carMark;
        private int pwork;
        private Object pworkMark;
        private Object baseInfo;
        private Object singleReason;
        private Object urgentMotive;
        private Object mateCondition;
        private Object visitStoreReason;
        private Object matchAdvice;
        private Object remark;
        private int animals;
        private int constellation;
        private int belief;
        private int family;
        private int isSmoking;
        private int isDrinking;
        private String birthday;
        private int height;
        private String lastUpdateTime;
        private int lastUpdateWorker;
        private String passiveLevel;
        private String idCardNo;
        private int validatedFlag;
        private int addressArea;
        private int occupation;
        private Object addressDetail;
        private int hometownCode;
        private int homemarkCode;
        private int normalEducation;
        private int incomeAmount;
        private String defaultPhoto;
        private int channelType;
        private String createTime;
        private String registerTime;
        private String lastLoginTime;
        private int loginCount;
        private String lastModifyIP;
        private int lastModifyWorkerId;
        private String lastModifyWorkerName;
        private int childrenCount;
        private String childrenInfo;
        private int houseCount;
        private String houseInfo;
        private int school;
        private int returnee;
        private int companyNature;
        private String personality;
        private String interest;
        private String emotionalDef;
        private String convenienceTime;
        private int memberAssets;
        private String serviceEvaluation;
        private String workCityDesc;
        private String workerCityDesc;
        private String educationDesc;
        private String homeTownDesc;
        private String homeTownPeoPleDesc;
        private String marriageDesc;
        private String occupationDesc;
        private String incomeDesc;
        private int age;
        private String constellationDesc;
        private String animalsDesc;
        private String houseDesc;
        private String carDesc;
        private Object childrenDesc;
        private String schoolDesc;
        private String companyNatureDesc;
        private String personalityDesc;
        private String interestDesc;
        private String emotionalDefDesc;
        private String addressAreaDesc;
        private String homemarkCodeDesc;
        private String convenienceTimeDesc;
        private int incomeNew;
        private int educationNew;
        private int birthdayNew;
        private Object nextAppointmentTime;
        private Object recommendReason;
        private MatchmakerEvaluationBean matchmakerEvaluation;
        private OfflineShopRelationBean offlineShopRelation;
        private MemberPhotoBean memberPhoto;
        private String introduceContent;
        private MyHeartTaDTOBean myHeartTaDTO;
        private String idealLife;
        private Object idealLifeTagList;
        private VoiceBean voice;
        private int checkStatus;
        private String dddate;
        private List<ChildrenInfoListBean> childrenInfoList;
        private List<HouseInfoDescListBean> houseInfoDescList;
        private List<MemberPhotosBean> memberPhotos;
        private List<FamilyListBean> familyList;

        public int getUin() {
            return uin;
        }

        public void setUin(int uin) {
            this.uin = uin;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getUpdateChannel() {
            return updateChannel;
        }

        public void setUpdateChannel(int updateChannel) {
            this.updateChannel = updateChannel;
        }

        public int getWorkProvince() {
            return workProvince;
        }

        public void setWorkProvince(int workProvince) {
            this.workProvince = workProvince;
        }

        public int getWorkCity() {
            return workCity;
        }

        public void setWorkCity(int workCity) {
            this.workCity = workCity;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getEducation() {
            return education;
        }

        public void setEducation(int education) {
            this.education = education;
        }

        public Object getJob() {
            return job;
        }

        public void setJob(Object job) {
            this.job = job;
        }

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public int getQuoteIncome() {
            return quoteIncome;
        }

        public void setQuoteIncome(int quoteIncome) {
            this.quoteIncome = quoteIncome;
        }

        public int getHownTown() {
            return hownTown;
        }

        public void setHownTown(int hownTown) {
            this.hownTown = hownTown;
        }

        public int getMarriage() {
            return marriage;
        }

        public void setMarriage(int marriage) {
            this.marriage = marriage;
        }

        public Object getMarriageMark() {
            return marriageMark;
        }

        public void setMarriageMark(Object marriageMark) {
            this.marriageMark = marriageMark;
        }

        public int getChildren() {
            return children;
        }

        public void setChildren(int children) {
            this.children = children;
        }

        public Object getChildrenMark() {
            return childrenMark;
        }

        public void setChildrenMark(Object childrenMark) {
            this.childrenMark = childrenMark;
        }

        public int getHouse() {
            return house;
        }

        public void setHouse(int house) {
            this.house = house;
        }

        public Object getHouseMark() {
            return houseMark;
        }

        public void setHouseMark(Object houseMark) {
            this.houseMark = houseMark;
        }

        public int getCar() {
            return car;
        }

        public void setCar(int car) {
            this.car = car;
        }

        public Object getCarMark() {
            return carMark;
        }

        public void setCarMark(Object carMark) {
            this.carMark = carMark;
        }

        public int getPwork() {
            return pwork;
        }

        public void setPwork(int pwork) {
            this.pwork = pwork;
        }

        public Object getPworkMark() {
            return pworkMark;
        }

        public void setPworkMark(Object pworkMark) {
            this.pworkMark = pworkMark;
        }

        public Object getBaseInfo() {
            return baseInfo;
        }

        public void setBaseInfo(Object baseInfo) {
            this.baseInfo = baseInfo;
        }

        public Object getSingleReason() {
            return singleReason;
        }

        public void setSingleReason(Object singleReason) {
            this.singleReason = singleReason;
        }

        public Object getUrgentMotive() {
            return urgentMotive;
        }

        public void setUrgentMotive(Object urgentMotive) {
            this.urgentMotive = urgentMotive;
        }

        public Object getMateCondition() {
            return mateCondition;
        }

        public void setMateCondition(Object mateCondition) {
            this.mateCondition = mateCondition;
        }

        public Object getVisitStoreReason() {
            return visitStoreReason;
        }

        public void setVisitStoreReason(Object visitStoreReason) {
            this.visitStoreReason = visitStoreReason;
        }

        public Object getMatchAdvice() {
            return matchAdvice;
        }

        public void setMatchAdvice(Object matchAdvice) {
            this.matchAdvice = matchAdvice;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getAnimals() {
            return animals;
        }

        public void setAnimals(int animals) {
            this.animals = animals;
        }

        public int getConstellation() {
            return constellation;
        }

        public void setConstellation(int constellation) {
            this.constellation = constellation;
        }

        public int getBelief() {
            return belief;
        }

        public void setBelief(int belief) {
            this.belief = belief;
        }

        public int getFamily() {
            return family;
        }

        public void setFamily(int family) {
            this.family = family;
        }

        public int getIsSmoking() {
            return isSmoking;
        }

        public void setIsSmoking(int isSmoking) {
            this.isSmoking = isSmoking;
        }

        public int getIsDrinking() {
            return isDrinking;
        }

        public void setIsDrinking(int isDrinking) {
            this.isDrinking = isDrinking;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public int getLastUpdateWorker() {
            return lastUpdateWorker;
        }

        public void setLastUpdateWorker(int lastUpdateWorker) {
            this.lastUpdateWorker = lastUpdateWorker;
        }

        public String getPassiveLevel() {
            return passiveLevel;
        }

        public void setPassiveLevel(String passiveLevel) {
            this.passiveLevel = passiveLevel;
        }

        public String getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public int getValidatedFlag() {
            return validatedFlag;
        }

        public void setValidatedFlag(int validatedFlag) {
            this.validatedFlag = validatedFlag;
        }

        public int getAddressArea() {
            return addressArea;
        }

        public void setAddressArea(int addressArea) {
            this.addressArea = addressArea;
        }

        public int getOccupation() {
            return occupation;
        }

        public void setOccupation(int occupation) {
            this.occupation = occupation;
        }

        public Object getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(Object addressDetail) {
            this.addressDetail = addressDetail;
        }

        public int getHometownCode() {
            return hometownCode;
        }

        public void setHometownCode(int hometownCode) {
            this.hometownCode = hometownCode;
        }

        public int getHomemarkCode() {
            return homemarkCode;
        }

        public void setHomemarkCode(int homemarkCode) {
            this.homemarkCode = homemarkCode;
        }

        public int getNormalEducation() {
            return normalEducation;
        }

        public void setNormalEducation(int normalEducation) {
            this.normalEducation = normalEducation;
        }

        public int getIncomeAmount() {
            return incomeAmount;
        }

        public void setIncomeAmount(int incomeAmount) {
            this.incomeAmount = incomeAmount;
        }

        public String getDefaultPhoto() {
            return defaultPhoto;
        }

        public void setDefaultPhoto(String defaultPhoto) {
            this.defaultPhoto = defaultPhoto;
        }

        public int getChannelType() {
            return channelType;
        }

        public void setChannelType(int channelType) {
            this.channelType = channelType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getLoginCount() {
            return loginCount;
        }

        public void setLoginCount(int loginCount) {
            this.loginCount = loginCount;
        }

        public String getLastModifyIP() {
            return lastModifyIP;
        }

        public void setLastModifyIP(String lastModifyIP) {
            this.lastModifyIP = lastModifyIP;
        }

        public int getLastModifyWorkerId() {
            return lastModifyWorkerId;
        }

        public void setLastModifyWorkerId(int lastModifyWorkerId) {
            this.lastModifyWorkerId = lastModifyWorkerId;
        }

        public String getLastModifyWorkerName() {
            return lastModifyWorkerName;
        }

        public void setLastModifyWorkerName(String lastModifyWorkerName) {
            this.lastModifyWorkerName = lastModifyWorkerName;
        }

        public int getChildrenCount() {
            return childrenCount;
        }

        public void setChildrenCount(int childrenCount) {
            this.childrenCount = childrenCount;
        }

        public String getChildrenInfo() {
            return childrenInfo;
        }

        public void setChildrenInfo(String childrenInfo) {
            this.childrenInfo = childrenInfo;
        }

        public int getHouseCount() {
            return houseCount;
        }

        public void setHouseCount(int houseCount) {
            this.houseCount = houseCount;
        }

        public String getHouseInfo() {
            return houseInfo;
        }

        public void setHouseInfo(String houseInfo) {
            this.houseInfo = houseInfo;
        }

        public int getSchool() {
            return school;
        }

        public void setSchool(int school) {
            this.school = school;
        }

        public int getReturnee() {
            return returnee;
        }

        public void setReturnee(int returnee) {
            this.returnee = returnee;
        }

        public int getCompanyNature() {
            return companyNature;
        }

        public void setCompanyNature(int companyNature) {
            this.companyNature = companyNature;
        }

        public String getPersonality() {
            return personality;
        }

        public void setPersonality(String personality) {
            this.personality = personality;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getEmotionalDef() {
            return emotionalDef;
        }

        public void setEmotionalDef(String emotionalDef) {
            this.emotionalDef = emotionalDef;
        }

        public String getConvenienceTime() {
            return convenienceTime;
        }

        public void setConvenienceTime(String convenienceTime) {
            this.convenienceTime = convenienceTime;
        }

        public int getMemberAssets() {
            return memberAssets;
        }

        public void setMemberAssets(int memberAssets) {
            this.memberAssets = memberAssets;
        }

        public String getServiceEvaluation() {
            return serviceEvaluation;
        }

        public void setServiceEvaluation(String serviceEvaluation) {
            this.serviceEvaluation = serviceEvaluation;
        }

        public String getWorkCityDesc() {
            return workCityDesc;
        }

        public void setWorkCityDesc(String workCityDesc) {
            this.workCityDesc = workCityDesc;
        }

        public String getWorkerCityDesc() {
            return workerCityDesc;
        }

        public void setWorkerCityDesc(String workerCityDesc) {
            this.workerCityDesc = workerCityDesc;
        }

        public String getEducationDesc() {
            return educationDesc;
        }

        public void setEducationDesc(String educationDesc) {
            this.educationDesc = educationDesc;
        }

        public String getHomeTownDesc() {
            return homeTownDesc;
        }

        public void setHomeTownDesc(String homeTownDesc) {
            this.homeTownDesc = homeTownDesc;
        }

        public String getHomeTownPeoPleDesc() {
            return homeTownPeoPleDesc;
        }

        public void setHomeTownPeoPleDesc(String homeTownPeoPleDesc) {
            this.homeTownPeoPleDesc = homeTownPeoPleDesc;
        }

        public String getMarriageDesc() {
            return marriageDesc;
        }

        public void setMarriageDesc(String marriageDesc) {
            this.marriageDesc = marriageDesc;
        }

        public String getOccupationDesc() {
            return occupationDesc;
        }

        public void setOccupationDesc(String occupationDesc) {
            this.occupationDesc = occupationDesc;
        }

        public String getIncomeDesc() {
            return incomeDesc;
        }

        public void setIncomeDesc(String incomeDesc) {
            this.incomeDesc = incomeDesc;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getConstellationDesc() {
            return constellationDesc;
        }

        public void setConstellationDesc(String constellationDesc) {
            this.constellationDesc = constellationDesc;
        }

        public String getAnimalsDesc() {
            return animalsDesc;
        }

        public void setAnimalsDesc(String animalsDesc) {
            this.animalsDesc = animalsDesc;
        }

        public String getHouseDesc() {
            return houseDesc;
        }

        public void setHouseDesc(String houseDesc) {
            this.houseDesc = houseDesc;
        }

        public String getCarDesc() {
            return carDesc;
        }

        public void setCarDesc(String carDesc) {
            this.carDesc = carDesc;
        }

        public Object getChildrenDesc() {
            return childrenDesc;
        }

        public void setChildrenDesc(Object childrenDesc) {
            this.childrenDesc = childrenDesc;
        }

        public String getSchoolDesc() {
            return schoolDesc;
        }

        public void setSchoolDesc(String schoolDesc) {
            this.schoolDesc = schoolDesc;
        }

        public String getCompanyNatureDesc() {
            return companyNatureDesc;
        }

        public void setCompanyNatureDesc(String companyNatureDesc) {
            this.companyNatureDesc = companyNatureDesc;
        }

        public String getPersonalityDesc() {
            return personalityDesc;
        }

        public void setPersonalityDesc(String personalityDesc) {
            this.personalityDesc = personalityDesc;
        }

        public String getInterestDesc() {
            return interestDesc;
        }

        public void setInterestDesc(String interestDesc) {
            this.interestDesc = interestDesc;
        }

        public String getEmotionalDefDesc() {
            return emotionalDefDesc;
        }

        public void setEmotionalDefDesc(String emotionalDefDesc) {
            this.emotionalDefDesc = emotionalDefDesc;
        }

        public String getAddressAreaDesc() {
            return addressAreaDesc;
        }

        public void setAddressAreaDesc(String addressAreaDesc) {
            this.addressAreaDesc = addressAreaDesc;
        }

        public String getHomemarkCodeDesc() {
            return homemarkCodeDesc;
        }

        public void setHomemarkCodeDesc(String homemarkCodeDesc) {
            this.homemarkCodeDesc = homemarkCodeDesc;
        }

        public String getConvenienceTimeDesc() {
            return convenienceTimeDesc;
        }

        public void setConvenienceTimeDesc(String convenienceTimeDesc) {
            this.convenienceTimeDesc = convenienceTimeDesc;
        }

        public int getIncomeNew() {
            return incomeNew;
        }

        public void setIncomeNew(int incomeNew) {
            this.incomeNew = incomeNew;
        }

        public int getEducationNew() {
            return educationNew;
        }

        public void setEducationNew(int educationNew) {
            this.educationNew = educationNew;
        }

        public int getBirthdayNew() {
            return birthdayNew;
        }

        public void setBirthdayNew(int birthdayNew) {
            this.birthdayNew = birthdayNew;
        }

        public Object getNextAppointmentTime() {
            return nextAppointmentTime;
        }

        public void setNextAppointmentTime(Object nextAppointmentTime) {
            this.nextAppointmentTime = nextAppointmentTime;
        }

        public Object getRecommendReason() {
            return recommendReason;
        }

        public void setRecommendReason(Object recommendReason) {
            this.recommendReason = recommendReason;
        }

        public MatchmakerEvaluationBean getMatchmakerEvaluation() {
            return matchmakerEvaluation;
        }

        public void setMatchmakerEvaluation(MatchmakerEvaluationBean matchmakerEvaluation) {
            this.matchmakerEvaluation = matchmakerEvaluation;
        }

        public OfflineShopRelationBean getOfflineShopRelation() {
            return offlineShopRelation;
        }

        public void setOfflineShopRelation(OfflineShopRelationBean offlineShopRelation) {
            this.offlineShopRelation = offlineShopRelation;
        }

        public MemberPhotoBean getMemberPhoto() {
            return memberPhoto;
        }

        public void setMemberPhoto(MemberPhotoBean memberPhoto) {
            this.memberPhoto = memberPhoto;
        }

        public String getIntroduceContent() {
            return introduceContent;
        }

        public void setIntroduceContent(String introduceContent) {
            this.introduceContent = introduceContent;
        }

        public MyHeartTaDTOBean getMyHeartTaDTO() {
            return myHeartTaDTO;
        }

        public void setMyHeartTaDTO(MyHeartTaDTOBean myHeartTaDTO) {
            this.myHeartTaDTO = myHeartTaDTO;
        }

        public String getIdealLife() {
            return idealLife;
        }

        public void setIdealLife(String idealLife) {
            this.idealLife = idealLife;
        }

        public Object getIdealLifeTagList() {
            return idealLifeTagList;
        }

        public void setIdealLifeTagList(Object idealLifeTagList) {
            this.idealLifeTagList = idealLifeTagList;
        }

        public VoiceBean getVoice() {
            return voice;
        }

        public void setVoice(VoiceBean voice) {
            this.voice = voice;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getDddate() {
            return dddate;
        }

        public void setDddate(String dddate) {
            this.dddate = dddate;
        }

        public List<ChildrenInfoListBean> getChildrenInfoList() {
            return childrenInfoList;
        }

        public void setChildrenInfoList(List<ChildrenInfoListBean> childrenInfoList) {
            this.childrenInfoList = childrenInfoList;
        }

        public List<HouseInfoDescListBean> getHouseInfoDescList() {
            return houseInfoDescList;
        }

        public void setHouseInfoDescList(List<HouseInfoDescListBean> houseInfoDescList) {
            this.houseInfoDescList = houseInfoDescList;
        }

        public List<MemberPhotosBean> getMemberPhotos() {
            return memberPhotos;
        }

        public void setMemberPhotos(List<MemberPhotosBean> memberPhotos) {
            this.memberPhotos = memberPhotos;
        }

        public List<FamilyListBean> getFamilyList() {
            return familyList;
        }

        public void setFamilyList(List<FamilyListBean> familyList) {
            this.familyList = familyList;
        }

}
