package com.frankuzi.applicationschecker.presentation.applications_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.frankuzi.applicationschecker.presentation.applications_list.ApplicationsListEvent
import com.frankuzi.applicationschecker.presentation.applications_list.ApplicationsListUiState
import com.frankuzi.applicationschecker.presentation.applications_list.ApplicationsListViewModel
import com.frankuzi.applicationschecker.ui.theme.ApplicationsCheckerTheme
import com.frankuzi.applicationschecker.R
import com.frankuzi.applicationschecker.domain.model.ApplicationInfo

@Composable
fun ApplicationsListRoot(
    viewModel: ApplicationsListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    ApplicationsListScreen(
        modifier = Modifier,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationsListScreen(
    modifier: Modifier = Modifier,
    state: State<ApplicationsListUiState>,
    onEvent: (ApplicationsListEvent) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = context.getString(R.string.app_bar_title)
                    )
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) {
        if (state.value.isLoading && state.value.applications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else if (state.value.applications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = context.getString(R.string.empty_list)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(it),
                contentPadding = PaddingValues(vertical = 15.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(
                    items = state.value.applications,
                    key = { applicationInfo ->
                        applicationInfo.packageName
                    }
                ) {application ->
                    ApplicationInfoButton(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        title = application.name,
                        onClick = {
                            onEvent(ApplicationsListEvent.ApplicationClickEvent(application))
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApplicationsListScreenPreview() {
    ApplicationsCheckerTheme(
        dynamicColor = false
    ) {
        ApplicationsListScreen(
            state = remember { mutableStateOf(ApplicationsListUiState(
                isLoading = false,
                applications = mutableListOf(
                    ApplicationInfo(
                        name = "App 1",
                        version = "",
                        packageName = "1",
                        checkSum = ""
                    ),
                    ApplicationInfo(
                        name = "App 2",
                        version = "",
                        packageName = "2",
                        checkSum = ""
                    ),
                    ApplicationInfo(
                        name = "App 3",
                        version = "",
                        packageName = "3",
                        checkSum = ""
                    )
                )
            )) },
            onEvent = {}
        )
    }
}