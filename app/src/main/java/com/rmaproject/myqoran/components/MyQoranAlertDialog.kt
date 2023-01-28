package com.rmaproject.myqoran.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyQoranAlertDialog(
    icon: ImageVector?,
    title: String,
    description: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            if (icon != null) Icon(icon, contentDescription = null)
        },
        title = {
            Text(text = title)
        },
        confirmButton = {
            TextButton(onClick = { onConfirmClick() }) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissClick() }) {
                Text(text = dismissButtonText)
            }
        },
        text = {
            Text(text = description)
        },
        onDismissRequest = { onDismissClick() },
    )
}

@Composable
fun MyQoranAlertDialog(
    icon: ImageVector?,
    title: String,
    actionItemList: List<ActionItem>,
    modifier: Modifier = Modifier,
    confirmButtonText: String = "",
    dismissButtonText: String = "",
    onDismissClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            if (icon != null) Icon(icon, contentDescription = null)
        },
        title = {
            Text(text = title)
        },
        confirmButton = {
            if (confirmButtonText.isNotEmpty())
                TextButton(onClick = { onConfirmClick() }) {
                    Text(text = confirmButtonText)
                }
        },
        dismissButton = {
            if (dismissButtonText.isNotEmpty())
                TextButton(onClick = { onDismissClick() }) {
                    Text(text = dismissButtonText)
                }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                actionItemList.forEach { actionItem ->
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { actionItem.onClick() }
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = actionItem.text, 
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        },
        onDismissRequest = { onDismissClick() },
    )
}

data class ActionItem(
    val text: String,
    val onClick: () -> Unit
)

@Preview
@Composable
fun MyQoranAlertDialogPreview() {
    val list = listOf(
        ActionItem(
            "Indonesia",
        ) {},
        ActionItem(
            "English",
        ) {}
    )
    MyQoranAlertDialog(
        icon = Icons.Default.Language,
        title = "Change Language",
        actionItemList = list,
        confirmButtonText = "Ok",
        dismissButtonText = "Cancel",
        onDismissClick = {},
        onConfirmClick = {}
    )
}