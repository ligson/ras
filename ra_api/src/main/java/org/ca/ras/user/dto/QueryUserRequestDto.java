package org.ca.ras.user.dto;

import org.ligson.fw.core.facade.base.dto.BaseQueryPageRequestDto;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by ligson on 2016/4/25.
 */
public class QueryUserRequestDto extends BaseQueryPageRequestDto {
    /***
     * id
     */
    private String id;

    /***
     * 生日
     */
    private Date birth;

    /***
     * 姓名
     */
    private String name;

    /***
     * 密码
     */
    private String password;

    /***
     * 性别
     */
    private Boolean sex;

    /***
     * 状态
     *
     * @see org.ca.common.user.enums.UserState
     */
    private Integer status;

    /***
     * 手机号
     */
    private String mobile;

    /***
     * 邮箱
     */
    private String email;

    /***
     * 注册日期
     */
    private Date registerDate;

    /***
     * 用户头像绝对http://
     */
    private String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "QueryUserRequestDto{" +
                "id=" + id +
                ", birth=" + birth +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", status=" + status +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", registerDate=" + registerDate +
                ", photo='" + photo + '\'' +
                '}';
    }
}
