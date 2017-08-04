package com.example.vankhanhpr.vidu2.fragment_main.fragment_medical.spinner

/**
 * Created by VANKHANHPR on 8/1/2017.
 */
class StateVO
{
    private var title: String? = null
    private var selected: Boolean = false

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun isSelected(): Boolean {
        return selected
    }

    fun setSelected(selected: Boolean) {
        this.selected = selected
    }
}