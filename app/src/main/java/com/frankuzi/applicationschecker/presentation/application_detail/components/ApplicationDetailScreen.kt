package com.frankuzi.applicationschecker.presentation.application_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.frankuzi.applicationschecker.presentation.application_detail.ApplicationDetailUiState
import com.frankuzi.applicationschecker.ui.theme.ApplicationsCheckerTheme
import com.frankuzi.applicationschecker.presentation.application_detail.ApplicationDetailViewModel
import com.frankuzi.applicationschecker.R
import com.frankuzi.applicationschecker.presentation.application_detail.ApplicationDetailEvent
import com.frankuzi.applicationschecker.ui.theme.Green

@Composable
fun ApplicationDetailRoot(
    packageName: String,
    applicationDetailViewModel: ApplicationDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        applicationDetailViewModel.loadInfoByPackageName(packageName)
    }
    val state = applicationDetailViewModel.state.collectAsStateWithLifecycle()
    ApplicationDetailScreen(
        modifier = Modifier
            .fillMaxSize(),
        state = state,
        onEvent = {
            applicationDetailViewModel.onEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationDetailScreen(
    modifier: Modifier = Modifier,
    state: State<ApplicationDetailUiState>,
    onEvent: (ApplicationDetailEvent) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.value.applicationName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(ApplicationDetailEvent.OnBackEvent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
        ) {
            Text(
                text = context.getString(R.string.application_name, state.value.applicationName)
            )
            Text(
                text = context.getString(R.string.application_version, state.value.version)
            )
            Text(
                text = context.getString(R.string.application_package_name, state.value.packageName)
            )
            Text(
                text = context.getString(R.string.application_checksum, state.value.sum)
            )
            Button(
                modifier = Modifier
                    .align(Alignment.End),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Green,
                    contentColor = Color.White
                ),
                onClick = {
                    onEvent(ApplicationDetailEvent.OpenClickEvent)
                }
            ) {
                Text(
                    text = context.getString(R.string.open)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ApplicationDetailScreenPreview() {
    ApplicationsCheckerTheme {
        ApplicationDetailScreen(
            state = remember { mutableStateOf(ApplicationDetailUiState()) },
            onEvent = {}
        )
    }
}