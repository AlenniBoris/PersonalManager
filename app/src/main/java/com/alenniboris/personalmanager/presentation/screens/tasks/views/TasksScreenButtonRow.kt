package com.alenniboris.personalmanager.presentation.screens.tasks.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowActiveButtonColor
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor

@Composable
fun TasksScreenButtonRow(
    modifier: Modifier = Modifier,
    currentElement: ClickableElement,
    listOfElements: List<ClickableElement>,
    itemsLazyListState: LazyListState = rememberLazyListState(),
) {

    LaunchedEffect(key1 = currentElement) {
        val index = listOfElements.indexOfFirst { it.text == currentElement.text }
        if (index >= 0) {
            itemsLazyListState.animateScrollToItem(index)
        }
    }

    LazyRow(
        modifier = modifier,
        state = itemsLazyListState
    ) {
        items(listOfElements) { element ->

            val backgroundColor by animateColorAsState(
                if (element == currentElement)
                    buttonRowActiveButtonColor
                else
                    buttonRowBackgroundColor
            )

            Box(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .clickable {
                        element.onClick()
                    }
                    .background(color = backgroundColor)
                    .padding(appButtonRowButtonInnerPadding)
            ) {

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = element.text,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize
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

                TasksScreenButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
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

                TasksScreenButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .background(buttonRowBackgroundColor)
                        .padding(appButtonRowInnerPadding),
                    currentElement = ClickableElement(
                        text = "woidjoiqd1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1wkmdk",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "qwdkmwqdlkwd2",
                            onClick = {}
                        ), ClickableElement(
                            text = "3wqlkmd",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "4",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "5qdlkmdl",
                            onClick = {}
                        )
                    )
                )

                TasksScreenButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
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

                TasksScreenButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
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

                TasksScreenButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
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