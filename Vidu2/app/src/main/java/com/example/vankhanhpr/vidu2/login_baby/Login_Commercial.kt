package com.example.vankhanhpr.vidu2.login_baby

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.login_doctor.Login_Doctor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.thefirst.*
import java.security.AccessController.getContext

class Login_Commercial : AppCompatActivity(),GestureDetector.OnGestureListener {
    var tvnameani : TextView? = null
    var tvsentani : TextView? = null
    var gesture : GestureDetector? = null
    var ivani : ImageView ? =null
    var position : Int = 1
    var next1 : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thefirst)
        //.....ánh xạ
        tvnameani = findViewById(R.id.tv_nameani) as TextView
        tvsentani = findViewById(R.id.tv_sentenceani) as TextView
        next1 = findViewById(R.id.tv_next) as TextView
        ivani = findViewById(R.id.iv_thefirst) as ImageView
        var bgr : LinearLayout = findViewById(R.id.bgr) as LinearLayout

        gesture = GestureDetector(this,this)

        next1!!.visibility = View.GONE //.. ko hiển thị
        animation(R.color.colorblue1, tvnameani!!,tvsentani!!,ivani!!)

        tv_next.setOnClickListener{//..... chuyển đến LOGIN phone hay ID
            //............ login mom
            var mom_doctor : String = getResources().getString(R.string.mom_or_doctor)
            if(mom_doctor == "mom"){
                var intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
            else if(mom_doctor == "doctor") {
                //............login doctor
                var intent = Intent(this, Login_Doctor::class.java)
                startActivity(intent)
            }
        }
    }
    //................. di chuyen ngon tay
    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if(e1!!.x - e2!!.x > 30 && position == 1) {//..... chuyển tới màn hình 2
            position++
            tvnameani!!.setText("Baby")
            ivani!!.setImageResource(R.drawable.baby)
            tvsentani!!.setText(R.string.sentani_2)

            next1!!.visibility = View.GONE //.. ko hiển thị
            animation(R.color.colorblue2, tvnameani!!,tvsentani!!,ivani!!)
        }
        else if(e1!!.x - e2!!.x > 30 && position == 2) {//..... chuyển tới màn hình 3
            position++
            tvnameani!!.setText("Share")
            //ivani!!.setImageResource(0)
            ivani!!.setImageResource(R.drawable.share)
            tvsentani!!.setText(R.string.sentani_3)

            next1!!.visibility = View.VISIBLE //..hiển thị
            animation(R.color.colorblue3, tvnameani!!,tvsentani!!,ivani!!)
        }
        else if(e2!!.x - e1!!.x > 30 && position == 3) {//..... trở về màn hình 2
            position--
            tvnameani!!.setText("Baby")
            //ivani!!.setImageResource(0)
            ivani!!.setImageResource(R.drawable.baby)
            tvsentani!!.setText(R.string.sentani_2)

            next1!!.visibility = View.GONE //..ko hiển thị
            animation(R.color.colorblue2, tvnameani!!,tvsentani!!,ivani!!)
        }
        else if(e2!!.x - e1!!.x > 30 && position == 2) {//..... trở về màn hình 1
            position--
            tvnameani!!.setText("Mom")
            //ivani!!.setImageResource(0)
            ivani!!.setImageResource(R.drawable.mom)
            tvsentani!!.setText(R.string.sentani_1)
            next1!!.visibility = View.GONE //.. ko hiển thị
            animation(R.color.colorblue1, tvnameani!!,tvsentani!!,ivani!!)
        }
        else{//............... e2-e1<=30
        }
        return false
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {//.....lắng nghe sự kiện vuốt
        return gesture!!.onTouchEvent(event)
    }
    fun animation (color : Int,tvname : TextView,tvsent : TextView,ivlogo : ImageView){
        bgr.setBackgroundColor(getResources().getColor(color))
        var animation: Animation = AnimationUtils.loadAnimation(this,R.anim.move_up)
        tvname!!.startAnimation(animation)
        tvsent!!.startAnimation(animation)
        ivlogo!!.startAnimation(animation)
    }

    //............ ko su dung
    override fun onShowPress(e: MotionEvent?) {
    }
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }
    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }
    override fun onLongPress(e: MotionEvent?) {
    }
}
