package com.arameu.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.arameu.R
import com.arameu.data.ALPHABET_MAPPINGS
import com.arameu.data.LetterMapping
import com.arameu.ui.theme.AgedInk
import com.arameu.ui.theme.LighterParchment
import com.arameu.ui.theme.MutedGold
import com.arameu.ui.theme.Parchment
import com.arameu.ui.theme.Terracotta
import com.arameu.ui.theme.WarmWhite

@Composable
fun AlphabetFab(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.size(48.dp),
        shape = RoundedCornerShape(14.dp),
        containerColor = Parchment,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp),
    ) {
        Text(
            text = "\u0710",
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 20.sp,
                lineHeight = 24.sp,
            ),
            color = AgedInk,
        )
    }
}

@Composable
fun AlphabetReferenceDialog(onDismiss: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = screenHeight * 0.85f)
                .background(WarmWhite, RoundedCornerShape(16.dp))
                .border(1.dp, LighterParchment, RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Close button row
            Box(modifier = Modifier.fillMaxWidth()) {
                // Title centered
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.alphabet_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = AgedInk,
                    )
                    Text(
                        text = stringResource(R.string.alphabet_subtitle),
                        style = MaterialTheme.typography.labelLarge,
                        color = Terracotta.copy(alpha = 0.7f),
                    )
                }
                // Close button at top-end
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.TopEnd).size(32.dp),
                ) {
                    Text(
                        text = "\u00D7",
                        style = MaterialTheme.typography.labelLarge,
                        color = AgedInk.copy(alpha = 0.5f),
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Gold divider
            HorizontalDivider(
                modifier = Modifier.width(40.dp),
                thickness = 2.dp,
                color = MutedGold.copy(alpha = 0.4f),
            )

            Spacer(Modifier.height(16.dp))

            // Letter grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(ALPHABET_MAPPINGS) { mapping ->
                    LetterCell(mapping)
                }
            }
        }
    }
}

@Composable
private fun LetterCell(mapping: LetterMapping, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = mapping.hebrew,
                style = MaterialTheme.typography.displaySmall,
                color = AgedInk,
            )
            Text(
                text = "  \u2192  ",
                style = MaterialTheme.typography.bodyMedium,
                color = Terracotta.copy(alpha = 0.5f),
            )
            Text(
                text = mapping.syriac,
                style = MaterialTheme.typography.displaySmall,
                color = AgedInk,
            )
        }
        Text(
            text = mapping.name,
            style = MaterialTheme.typography.labelMedium,
            color = AgedInk.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
        )
    }
}
