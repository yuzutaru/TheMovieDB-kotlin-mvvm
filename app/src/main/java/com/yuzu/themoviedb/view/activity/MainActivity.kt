package com.yuzu.themoviedb.view.activity

import android.os.Bundle
import com.yuzu.themoviedb.R

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class MainActivity: BaseViewActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        /*if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_content,
                    UserFragment()
                ).commit()
        }*/
    }
}