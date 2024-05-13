package com.makalaster.ethereal_dialpad.main

import android.content.res.Configuration
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makalaster.ethereal_dialpad.R
import com.makalaster.ethereal_dialpad.navigation.DrawPad
import com.makalaster.ethereal_dialpad.navigation.EtherealDialpadDestination
import com.makalaster.ethereal_dialpad.navigation.FlatPad
import com.makalaster.ethereal_dialpad.navigation.SwarmPad
import com.makalaster.ethereal_dialpad.ui.theme.EtherealDialpadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    onPadClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        style = MaterialTheme.typography.headlineMedium,
                        text = stringResource(id = R.string.app_name),
                        color = Color.Black
                    )
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
            .padding(vertical = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        PadItem(FlatPad, onPadClick)

        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        PadItem(DrawPad, onPadClick)

        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        PadItem(SwarmPad, onPadClick)

        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 12.dp)
        )
//        Spacer(modifier = Modifier.height(12.dp))
//        PadItem(GridPad, onPadClick)
    }
}

@Composable
fun PadItem(
    pad: EtherealDialpadDestination,
    onPadClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onPadClick(pad.route) }
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(id = pad.nameId)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = pad.iconId),
                contentDescription = "Pad item",
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = stringResource(id = pad.descId),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Open"
            )
        }
    }
}

@Preview
@Composable
fun PadItemPreview() {
    EtherealDialpadTheme {
        PadItem(pad = FlatPad, onPadClick = {})
    }
}

@Preview(
    name = "Home Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Home Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeViewPreview() {
    EtherealDialpadTheme {
        HomeView(
            onPadClick = {}
        )
    }
}