package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowButtonInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowButtonShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowActiveButtonColor
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.mapLocationItemContentTextSize

@Composable
fun AppButtonRow(
    modifier: Modifier = Modifier,
    currentElement: ClickableElement,
    listOfElements: List<ClickableElement>,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOfElements.forEach { element ->

            val backgroundColor by animateColorAsState(
                if (element == currentElement)
                    buttonRowActiveButtonColor
                else
                    buttonRowBackgroundColor
            )

            Box(
                modifier = Modifier
                    .clip(appButtonRowButtonShape)
                    .clickable {
                        element.onClick()
                    }
                    .weight(1f)
                    .background(color = backgroundColor)
                    .padding(appButtonRowButtonInnerPadding)
            ) {

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = element.text,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = mapLocationItemContentTextSize
                    )
                )
            }
        }
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

                AppButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appButtonRowShape)
                        .fillMaxWidth()
                        .background(buttonRowBackgroundColor)
                        .padding(appButtonRowInnerPadding),
                    currentElement = ClickableElement(
                        text = "1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "2",
                            onClick = {}
                        ), ClickableElement(
                            text = "3",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "4",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "5",
                            onClick = {}
                        )
                    )
                )

                AppButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appButtonRowShape)
                        .fillMaxWidth()
                        .background(buttonRowBackgroundColor)
                        .padding(appButtonRowInnerPadding),
                    currentElement = ClickableElement(
                        text = "1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "2",
                            onClick = {}
                        )
                    )
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

                AppButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appButtonRowShape)
                        .fillMaxWidth()
                        .background(buttonRowBackgroundColor)
                        .padding(appButtonRowInnerPadding),
                    currentElement = ClickableElement(
                        text = "1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "2",
                            onClick = {}
                        ), ClickableElement(
                            text = "3",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "4",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "5",
                            onClick = {}
                        )
                    )
                )

                AppButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appButtonRowShape)
                        .fillMaxWidth()
                        .background(buttonRowBackgroundColor)
                        .padding(appButtonRowInnerPadding),
                    currentElement = ClickableElement(
                        text = "1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "2",
                            onClick = {}
                        )
                    )
                )
            }
        }
    }
}