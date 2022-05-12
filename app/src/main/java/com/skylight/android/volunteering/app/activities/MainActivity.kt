package com.skylight.android.volunteering.app.activities

import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.core.ui.base.BaseActivity
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.ActivityMainBinding


/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 23:14.
 * Simple activity which will host all the fragments i.e single activity model recommended by google dev advocates
 */
class MainActivity : BaseActivity() {
    private lateinit var navController: NavController
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.app_name)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        navController.setGraph(R.navigation.app_navigation, savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
