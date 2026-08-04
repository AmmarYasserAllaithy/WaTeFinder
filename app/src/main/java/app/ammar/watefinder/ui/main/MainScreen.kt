package app.ammar.watefinder.ui.main

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.ammar.watefinder.R
import app.ammar.watefinder.ui.main.components.HistoryItem
import app.ammar.watefinder.ui.theme.Variables
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current


    // Handle Side Effects (Navigation & Toasts)
    LaunchedEffect(viewModel.effects) {
        viewModel.effects.collect { event ->
            when (event) {
                is MainContract.Effect.OpenUrl -> {
                    val intent = Intent(Intent.ACTION_VIEW, event.url.toUri())
                        .apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                    context.startActivity(intent)
                }

                is MainContract.Effect.ShowToast -> {
                    Toast.makeText(
                        context,
                        context.getString(event.messageResId),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            contentPadding = PaddingValues(
                top = Variables.Space300 + padding.calculateTopPadding(),
                start = Variables.Space400 + padding.calculateStartPadding(LayoutDirection.Ltr),
                end = Variables.Space400 + padding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = Variables.Space400 + padding.calculateBottomPadding(),
            ),
            verticalArrangement = Arrangement.spacedBy(Variables.Space300),
        ) {
            item {
                val unifiedRoundedShape = RoundedCornerShape(Variables.Radius300)
                val containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)

                val fieldColors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    errorContainerColor = containerColor,

                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    errorTextColor = MaterialTheme.colorScheme.onSurface,

                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                )


                OutlinedTextField(
                    value = uiState.number,
                    onValueChange = {
                        viewModel.onNumberChange(it)
                    },
                    label = {
                        Text(stringResource(id = R.string.phone_label))
                    },
                    placeholder = {
                        Text(stringResource(id = R.string.phone_placeholder))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = uiState.isNumberError,
                    shape = unifiedRoundedShape,
                    colors = fieldColors,
                    supportingText = {
                        if (uiState.isNumberError) {
                            Text(text = stringResource(id = R.string.invalid_phone))
                        } else {
                            Text(text = stringResource(id = R.string.i18n_phones_hint))
                        }
                    },
                )

                OutlinedTextField(
                    value = uiState.message,
                    onValueChange = {
                        viewModel.onMessageChange(it)
                    },
                    label = {
                        Text(stringResource(id = R.string.message_hint))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Variables.Space300),
                    minLines = 3,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        textDirection = TextDirection.ContentOrLtr,
                    ),
                    shape = unifiedRoundedShape,
                    colors = fieldColors,
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Variables.Space400),
                    horizontalArrangement = Arrangement.spacedBy(Variables.Space300),
                ) {
                    Button(
                        onClick = { viewModel.onFindClicked(isTe = false) },
                        modifier = Modifier.weight(1f),
                        shape = unifiedRoundedShape,
                    ) {
                        Text(
                            text = stringResource(id = R.string.whatsapp),
                            modifier = Modifier.padding(vertical = Variables.Space100)
                        )
                    }

                    Button(
                        onClick = { viewModel.onFindClicked(isTe = true) },
                        modifier = Modifier.weight(1f),
                        shape = unifiedRoundedShape,
                    ) {
                        Text(
                            text = stringResource(id = R.string.telegram),
                            modifier = Modifier.padding(vertical = Variables.Space100)
                        )
                    }
                }

                HorizontalDivider(
                    thickness = 0.dp,
                    modifier = Modifier.padding(vertical = Variables.Space500),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                )

                Text(
                    text = stringResource(id = R.string.history),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }

            items(uiState.history, key = { it.number + it.created }) { account ->
                HistoryItem(
                    account = account,
                    onClick = { viewModel.onHistoryItemClicked(account) },
                    onDelete = { viewModel.onDeleteAccount(account) },
                )
            }
        }

    }
}
