package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.utils.AppLanguage
import com.alenniboris.personalmanager.presentation.uikit.utils.AppTheme
import com.alenniboris.personalmanager.presentation.uikit.utils.currentLanguageMode
import com.alenniboris.personalmanager.presentation.uikit.utils.currentThemeMode
import com.alenniboris.personalmanager.presentation.uikit.utils.setLanguage
import com.alenniboris.personalmanager.presentation.uikit.utils.setTheme
import com.alenniboris.personalmanager.presentation.uikit.utils.toUiString

@Composable
fun AppSettingsDialog(
    onDismiss: () -> Unit
) {

    val currentTheme by currentThemeMode.collectAsStateWithLifecycle()
    val allThemes by remember { mutableStateOf(AppTheme.entries.toList()) }
    val isDarkTheme = isSystemInDarkTheme()
    val currentLanguage by currentLanguageMode.collectAsStateWithLifecycle()
    val allLanguages by remember { mutableStateOf(AppLanguage.entries.toList()) }

    AlertDialog(
        dismissButton = {},
        confirmButton = {},
        onDismissRequest = {
            onDismiss()
        },
        containerColor = appColor,
        shape = appRoundedShape,
        text = {
            DialogUi(
                currentTheme = currentTheme.theme,
                allThemes = allThemes,
                isDarkTheme = isDarkTheme,
                currentLanguage = currentLanguage.language,
                allLanguages = allLanguages
            )
        }
    )
}

@Composable
private fun DialogUi(
    currentTheme: AppTheme,
    allThemes: List<AppTheme>,
    isDarkTheme: Boolean,
    currentLanguage: AppLanguage,
    allLanguages: List<AppLanguage>
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(R.string.settings_header),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        AppLazyButtonRow(
            modifier = Modifier
                .padding(appDialogItemPadding)
                .clip(appRoundedShape)
                .fillMaxWidth()
                .background(buttonRowBackgroundColor)
                .padding(appButtonRowInnerPadding),
            currentElement = ClickableElement(
                text = stringResource(currentTheme.toUiString()),
                onClick = {}
            ),
            listOfElements = allThemes.map {
                ClickableElement(
                    text = stringResource(it.toUiString()),
                    onClick = {
                        context.setTheme(
                            theme = it,
                            isThemeDark = isDarkTheme
                        )
                    }
                )
            }
        )

        AppButtonRow(
            modifier = Modifier
                .padding(appDialogItemPadding)
                .clip(appRoundedShape)
                .fillMaxWidth()
                .background(buttonRowBackgroundColor)
                .padding(appButtonRowInnerPadding),
            currentElement = ClickableElement(
                text = stringResource(currentLanguage.toUiString()),
                onClick = {}
            ),
            listOfElements = allLanguages.map {
                ClickableElement(
                    text = stringResource(it.toUiString()),
                    onClick = {
                        context.setLanguage(
                            language = it
                        )
                    }
                )
            }
        )
    }
}

@Composable
@Preview
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                DialogUi(
                    currentTheme = AppTheme.LIGHT,
                    allThemes = AppTheme.entries.toList(),
                    isDarkTheme = false,
                    currentLanguage = AppLanguage.English,
                    allLanguages = AppLanguage.entries.toList()
                )
            }
        }
    }
}

@Composable
@Preview
private fun DarkTheme() {
    PersonalManagerTheme(
        darkTheme = true
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                DialogUi(
                    currentTheme = AppTheme.LIGHT,
                    allThemes = AppTheme.entries.toList(),
                    isDarkTheme = false,
                    currentLanguage = AppLanguage.English,
                    allLanguages = AppLanguage.entries.toList()
                )
            }
        }
    }
}