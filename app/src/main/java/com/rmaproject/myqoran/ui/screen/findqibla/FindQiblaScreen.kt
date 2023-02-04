package com.rmaproject.myqoran.ui.screen.findqibla

import android.app.Activity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.just.agentweb.AgentWeb
import com.rmaproject.myqoran.BuildConfig
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.components.MyQoranHomeAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindQiblaScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {

    val activity = LocalContext.current as Activity
    val url = BuildConfig.QIBLA_FINDER_URL_ID

    Scaffold(
        modifier = modifier,
        topBar = {
            MyQoranHomeAppBar(
                openDrawer = openDrawer,
                currentDestination = stringResource(R.string.txt_find_qibla)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AndroidView(
                factory = { context ->
                    LinearLayout(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        AgentWeb.with(activity)
                            .setAgentWebParent(this, this.layoutParams)
                            .useDefaultIndicator()
                            .createAgentWeb()
                            .ready()
                            .go(url)
                    }
                }
            )
        }
    }
}