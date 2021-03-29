package com.example.mydesignapplication


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.mydesignapplication.data.bean.EmployerAccountBean
import com.example.mydesignapplication.ui.candidate.CandidateFragment
import com.example.mydesignapplication.ui.mine.MineFragment
import com.example.mydesignapplication.ui.post.PostFragment
import com.example.mydesignapplication.publicclass.ViewPagerAdapter
import com.example.mydesignapplication.ui.releasejob.releasejob1.ReleaseJob1Activity
import com.example.mydesignapplication.utils.StatusBarUtils

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var candidateLayout: LinearLayout
    private lateinit var candidateImg: ImageView
    private lateinit var candidateTextView: TextView
    private lateinit var postLayout: LinearLayout
    private lateinit var postImg: ImageView
    private lateinit var postTextView: TextView
    private lateinit var messageLayout: LinearLayout
    private lateinit var messageImg: ImageView
    private lateinit var messageTextView: TextView
    private lateinit var mineLayout: LinearLayout
    private lateinit var mineImg: ImageView
    private lateinit var mineTextView: TextView
    private lateinit var releaseLayout: LinearLayout

    private val fragmentList = ArrayList<Fragment>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.setLightBar(this, Color.TRANSPARENT)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        viewPager = f(R.id.main_activity_viewpager)
        candidateLayout = f(R.id.main_activity_candidate_layout)
        candidateImg = f(R.id.main_activity_candidate_img)
        candidateTextView = f(R.id.main_activity_candidate_text_view)
        postLayout = f(R.id.main_activity_post_layout)
        postImg = f(R.id.main_activity_post_img)
        postTextView = f(R.id.main_activity_post_text_view)
        messageLayout = f(R.id.main_activity_message_layout)
        messageImg = f(R.id.main_activity_message_img)
        messageTextView = f(R.id.main_activity_message_text_view)
        mineLayout = f(R.id.main_activity_mine_layout)
        mineImg = f(R.id.main_activity_mine_img)
        mineTextView = f(R.id.main_activity_mine_text_view)
        releaseLayout = f(R.id.main_activity_release_layout)
        initViewPager()
        initNavigationLayout()
    }

    private fun initViewPager() {
        fragmentList.apply {
            add(CandidateFragment())
            add(PostFragment())
            add(CandidateFragment())
            add(MineFragment())
        }
        viewPagerAdapter = ViewPagerAdapter(fragmentList, this)
        viewPager.adapter = viewPagerAdapter
        viewPager.isUserInputEnabled = false;
        viewPager.currentItem = 0
        initCurrentIndex(0)
    }

    private fun initNavigationLayout() {
        candidateLayout.setOnClickListener {
            viewPager.currentItem = 0
            initCurrentIndex(0)
        }
        postLayout.setOnClickListener {
            viewPager.currentItem = 1
            initCurrentIndex(1)
        }
        messageLayout.setOnClickListener {
            viewPager.currentItem = 2
            initCurrentIndex(2)
        }
        mineLayout.setOnClickListener {
            viewPager.currentItem = 3
            initCurrentIndex(3)
        }
        releaseLayout.setOnClickListener {
            val intent = Intent(this, ReleaseJob1Activity::class.java)
            startActivity(intent)
        }

    }

    private fun initCurrentIndex(index: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            when (index) {
                0 -> {
                    setCandidateSelect()
                    setPostUnSelect()
                    setMessageUnSelect()
                    setMineUnSelect()
                }
                1 -> {
                    setCandidateUnSelect()
                    setPostSelect()
                    setMessageUnSelect()
                    setMineUnSelect()
                }
                2 -> {
                    setCandidateUnSelect()
                    setPostUnSelect()
                    setMessageSelect()
                    setMineUnSelect()
                }
                3 -> {
                    setCandidateUnSelect()
                    setPostUnSelect()
                    setMessageUnSelect()
                    setMineSelect()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setCandidateSelect() {
        candidateImg.setImageDrawable(getDrawable(R.drawable.ic_candidate_select))
        candidateTextView.setTextColor(getColor(R.color.navigation_select))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setCandidateUnSelect() {
        candidateImg.setImageDrawable(getDrawable(R.drawable.ic_candidate_unselect))
        candidateTextView.setTextColor(getColor(R.color.navigation_unselect))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setPostSelect() {
        postImg.setImageDrawable(getDrawable(R.drawable.ic_post_select))
        postTextView.setTextColor(getColor(R.color.navigation_select))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setPostUnSelect() {
        postImg.setImageDrawable(getDrawable(R.drawable.ic_post_unselect))
        postTextView.setTextColor(getColor(R.color.navigation_unselect))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setMessageSelect() {
        messageImg.setImageDrawable(getDrawable(R.drawable.ic_message_select))
        messageTextView.setTextColor(getColor(R.color.navigation_select))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setMessageUnSelect() {
        messageImg.setImageDrawable(getDrawable(R.drawable.ic_message_unselect))
        messageTextView.setTextColor(getColor(R.color.navigation_unselect))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setMineSelect() {
        mineImg.setImageDrawable(getDrawable(R.drawable.ic_mine_select))
        mineTextView.setTextColor(getColor(R.color.navigation_select))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setMineUnSelect() {
        mineImg.setImageDrawable(getDrawable(R.drawable.ic_mine_unselect))
        mineTextView.setTextColor(getColor(R.color.navigation_unselect))
    }


    private fun <T : View> f(id: Int): T {
        return findViewById(id)
    }

    companion object {

        const val PARAM_EMPLOYER_ACCOUNT_BEAN = "employer_account_bean"
        var EMPLOYER_ACCOUNT_BEAN: EmployerAccountBean? = null

        fun actionStart(context: Context, employerAccountBean: EmployerAccountBean) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(PARAM_EMPLOYER_ACCOUNT_BEAN, employerAccountBean)
            EMPLOYER_ACCOUNT_BEAN = employerAccountBean
            context.startActivity(intent)
        }
    }
}