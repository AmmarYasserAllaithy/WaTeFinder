package app.ammar.watefinder.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import app.ammar.watefinder.R
import app.ammar.watefinder.domain.model.AccountModel


@Composable
fun HistoryItem(
    account: AccountModel,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary.copy(.1f))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = account.displayFormat,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )

            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.delete),
                tint = Color.Gray,
                modifier = Modifier
                    .clickable(onClick = onDelete)
                    .size(16.dp)
            )
        }

        if (account.message.isNotBlank()) {
            Text(
                text = account.message,
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDirection = TextDirection.ContentOrLtr,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}