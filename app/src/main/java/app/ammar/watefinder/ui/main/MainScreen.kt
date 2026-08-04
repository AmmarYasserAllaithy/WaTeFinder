package app.ammar.watefinder.ui.main

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.ammar.watefinder.R
import app.ammar.watefinder.ui.main.components.HistoryItem
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
                    Text(stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

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
                    .padding(top = 8.dp),
                minLines = 3,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    textDirection = TextDirection.ContentOrLtr,
                ),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.onFindClicked(isTe = false) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(id = R.string.whatsapp))
                }

                Button(
                    onClick = { viewModel.onFindClicked(isTe = true) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(id = R.string.telegram))
                }
            }

            HorizontalDivider(
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 16.dp),
            )

            Text(
                text = stringResource(id = R.string.history),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
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
}
