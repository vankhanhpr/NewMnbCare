package com.example.vankhanhpr.vidu2.getter_setter

import android.location.Address

/**
 * Created by User on 7/17/2017.
 */

class IsCustomer {
    private var is_cust_id: String? = null
    private var os_cust_nm: String? = null
    private var os_nick_nm: String? = null
    private var os_mobi_phone: String? = null
    private var os_sex_tp: String? = null
    private var os_bir_dt: String? = null
    private var os_addr: String? = null
    private var os_email: String? = null
    private var os_star_eval: Int? = null
    private var os_point_eval: Int? = null

    fun getIs_cust_id(): String? {
        return is_cust_id
    }
    fun setIs_cust_id(userID: String) {
        is_cust_id = userID
    }

    fun getOs_cust_nm(): String? {
        return os_cust_nm
    }
    fun setOs_cust_nm(customer_Name: String) {
        os_cust_nm = customer_Name
    }

    fun getOs_nick_nm(): String? {
        return os_nick_nm
    }
    fun setOs_nick_nm(nick_Name: String) {
        os_nick_nm = nick_Name
    }

    fun getOs_mobi_phone(): String? {
        return os_mobi_phone
    }
    fun setOs_mobi_phone(birthday: String) {
        os_mobi_phone = birthday
    }

    fun getOs_sex_tp(): String? {
        return os_sex_tp
    }
    fun setOs_sex_tp(mobile_Phone: String) {
        os_sex_tp = mobile_Phone
    }

    fun getOs_bir_dt(): String? {
        return os_bir_dt
    }
    fun setOs_bir_dt(email: String) {
        os_bir_dt = email
    }

    fun getOs_addr(): String? {
        return os_addr
    }
    fun setOs_addr(address: String) {
        os_addr = address
    }

    fun getOs_email(): String? {
        return os_email
    }
    fun setOs_email(sex: String) {
        os_email = sex
    }

    fun getOs_star_eval(): Int? {
        return os_star_eval
    }
    fun setOs_star_eval(working_Date: Int) {
        os_star_eval = working_Date
    }

    fun getOs_point_eval(): Int? {
        return os_point_eval
    }
    fun setOs_point_eval(working_Date: Int) {
        os_point_eval = working_Date
    }
}
