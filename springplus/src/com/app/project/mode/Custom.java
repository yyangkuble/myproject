package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;

@TableCreate
@ApiDesc("客户表")
@Entity
public class Custom {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	@ApiDesc("用户id")
	String userId;
	@ApiDesc("客户级别   A,B,C,D") 
	String level;//增加无需传送，默认为D
	@ApiDesc("客户名称")
	String name;
	@ApiDesc("客户类别     营销，增员，两者皆是")
	String customType;
	@ApiDesc("电话")
	String tel;
	@ApiDesc("性别")
	String sex;
	@ApiDesc("年龄")
	Integer age;//无需传送，根据生日生成
	@ApiDesc("星座")
	String zodiac;//无需传送，根据生日生成
	@ApiDesc("关系")
	String relationship;
	@ApiDesc("保险观念 ")
	String hope;
	@ApiDesc("生日")
	String birthDay;
	@ApiDesc("户籍省")
	String hometownProvince;
	@ApiDesc("户籍市")
	String hometownCity;
	@ApiDesc("地址")
	String address;
	@ApiDesc("工作")
	String work;
	@ApiDesc("职位")
	String position;
	@ApiDesc("年收入")
	String yearIncome;
	@ApiDesc("保费")
	Long  whim;
	@ApiDesc("车子")
	String car;
	@ApiDesc("保险到期日期")
	String carExpireDate;
	@ApiDesc("兴趣")
	String interest;
	@ApiDesc("客户名称首字母，不需要传递，服务器生成")
	String abc;
	@ApiDesc("头像地址")
	String imgPathSmall;
	@ApiDesc("头像原图地址")
	String imgPathBig;
	@ApiDesc("身份证")
	String idCards;
	@ApiDesc("最后一次拜访时间  不需要传递")
	String visitLastTime;
	@Text
	@ApiDesc("多张使用,分割")
	String imgUrls;
	@ApiDesc("这个字段用来，判断是否是首次添加  0：第一次增加，1：已更新过，服务器自动维护，不需要前台传入")
	Integer addState;

	public Integer getAddState() {
		return addState;
	}

	public void setAddState(Integer addState) {
		this.addState = addState;
	}

	public String getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLevel() {
		return level;
	}
	
	public String getHope() {
		return hope;
	}
	public void setHope(String hope) {
		this.hope = hope;
	}
	public Long getWhim() {
		return whim;
	}
	public void setWhim(Long whim) {
		this.whim = whim;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCustomType() {
		return customType;
	}
	public void setCustomType(String customType) {
		this.customType = customType;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getHometownProvince() {
		return hometownProvince;
	}
	public void setHometownProvince(String hometownProvince) {
		this.hometownProvince = hometownProvince;
	}
	public String getHometownCity() {
		return hometownCity;
	}
	public void setHometownCity(String hometownCity) {
		this.hometownCity = hometownCity;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getYearIncome() {
		return yearIncome;
	}
	public void setYearIncome(String yearIncome) {
		this.yearIncome = yearIncome;
	}
	
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public String getCarExpireDate() {
		return carExpireDate;
	}
	public void setCarExpireDate(String carExpireDate) {
		this.carExpireDate = carExpireDate;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	
	public String getAbc() {
		return abc;
	}
	public void setAbc(String abc) {
		this.abc = abc;
	}
	public String getImgPathSmall() {
		return imgPathSmall;
	}
	public void setImgPathSmall(String imgPathSmall) {
		this.imgPathSmall = imgPathSmall;
	}
	public String getImgPathBig() {
		return imgPathBig;
	}
	public void setImgPathBig(String imgPathBig) {
		this.imgPathBig = imgPathBig;
	}
	public String getIdCards() {
		return idCards;
	}
	public void setIdCards(String idCards) {
		this.idCards = idCards;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getZodiac() {
		return zodiac;
	}
	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}
	public String getVisitLastTime() {
		return visitLastTime;
	}
	public void setVisitLastTime(String visitLastTime) {
		this.visitLastTime = visitLastTime;
	}
	
	
}
