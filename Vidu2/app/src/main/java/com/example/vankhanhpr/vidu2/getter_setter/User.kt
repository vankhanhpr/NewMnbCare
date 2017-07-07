package com.example.vankhanhpr.vidu2.getter_setter

/**
 * Created by VANKHANHPR on 7/7/2017.
 */


class  User
{
    private var UserID: String? = null
    private var Customer_Name: String? = null
    private var Nick_Name: String? = null
    private var Birthday: String? = null
    private var Mobile_Phone: String? = null
    private var Email: String? = null
    private var Address: String? = null
    private var Sex: String? = null
    private var Working_Date: String? = null

    fun getUserID(): String? {
        return UserID
    }

    fun setUserID(userID: String) {
        UserID = userID
    }

    fun getCustomer_Name(): String? {
        return Customer_Name
    }

    fun setCustomer_Name(customer_Name: String) {
        Customer_Name = customer_Name
    }

    fun getNick_Name(): String? {
        return Nick_Name
    }

    fun setNick_Name(nick_Name: String) {
        Nick_Name = nick_Name
    }

    fun getBirthday(): String? {
        return Birthday
    }

    fun setBirthday(birthday: String) {
        Birthday = birthday
    }

    fun getMobile_Phone(): String? {
        return Mobile_Phone
    }

    fun setMobile_Phone(mobile_Phone: String) {
        Mobile_Phone = mobile_Phone
    }

    fun getEmail(): String? {
        return Email
    }

    fun setEmail(email: String) {
        Email = email
    }

    fun getAddress(): String? {
        return Address
    }

    fun setAddress(address: String) {
        Address = address
    }

    fun getSex(): String? {
        return Sex
    }

    fun setSex(sex: String) {
        Sex = sex
    }

    fun getWorking_Date(): String? {
        return Working_Date
    }

    fun setWorking_Date(working_Date: String) {
        Working_Date = working_Date
    }
}