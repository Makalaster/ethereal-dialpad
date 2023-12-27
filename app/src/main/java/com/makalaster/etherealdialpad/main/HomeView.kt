package com.makalaster.etherealdialpad.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.navigation.BasicPad
import com.makalaster.etherealdialpad.navigation.DrawPad
import com.makalaster.etherealdialpad.navigation.EtherealDialpadDestination
import com.makalaster.etherealdialpad.navigation.GridPad
import com.makalaster.etherealdialpad.navigation.SwarmPad
import com.makalaster.etherealdialpad.ui.theme.EtherealDialpadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    onPadClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = onSettingsClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { padding ->
        HomeContent(onPadClick, Modifier.padding(padding))
    }
}

@Composable
fun HomeContent(
    onPadClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = stringResource(id = R.string.app_desc))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(id = R.string.select_dialpad))
        Spacer(modifier = Modifier.height(12.dp))
        PadItem(BasicPad, onPadClick)
        Spacer(modifier = Modifier.height(12.dp))
        PadItem(DrawPad, onPadClick)
        Spacer(modifier = Modifier.height(12.dp))
        PadItem(SwarmPad, onPadClick)
        Spacer(modifier = Modifier.height(12.dp))
        PadItem(GridPad, onPadClick)
    }
}

@Composable
fun PadItem(
    pad: EtherealDialpadDestination,
    onPadClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable { onPadClick(pad.route) }
    ) {
        Image(
            painter = painterResource(id = pad.iconId),
            contentDescription = "Pad item",
            modifier = Modifier.size(48.dp)
        )
        Text(text = stringResource(id = pad.nameId))
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    EtherealDialpadTheme {
        HomeView(
            onPadClick = {},
            onSettingsClick = {}
        )
    }
}