package com.makalaster.etherealdialpad.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.ui.theme.EtherealDialpadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    viewModel: HomeViewModel = viewModel(),
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
        HomeContent(viewModel::padSelected, Modifier.padding(padding))
    }
}

@Composable
fun HomeContent(
    onPadClick: (Int) -> Unit,
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
        Spacer(modifier = Modifier.height(4.dp))
        PadItem(drawableId = R.drawable.basicpad_icon, textId = R.string.basicpad_label, onClick = onPadClick)
        Spacer(modifier = Modifier.height(4.dp))
        PadItem(drawableId = R.drawable.trailpad_icon, textId = R.string.trailpad_label, onClick = onPadClick)
        Spacer(modifier = Modifier.height(4.dp))
        PadItem(drawableId = R.drawable.pointpad_icon, textId = R.string.pointpad_label, onClick = onPadClick)
        Spacer(modifier = Modifier.height(4.dp))
        PadItem(drawableId = R.drawable.gridpad_icon, textId = R.string.gridpad_label, onClick = onPadClick)
    }
}

@Composable
fun PadItem(@DrawableRes drawableId: Int,
            @StringRes textId: Int,
            onClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable { onClick(textId) }
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = "Pad item",
            modifier = Modifier.size(48.dp)
        )
        Text(text = stringResource(id = textId))
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    EtherealDialpadTheme {
        HomeView(onSettingsClick = {})
    }
}