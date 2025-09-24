package com.alenniboris.personalmanager.presentation.screens.personal.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.screens.personal.IPersonalScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDateFilterInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.deleteButtonColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionFoodItemInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionListPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionTextStartPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenContentItemVerticalPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenInfoBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenInfoTextTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenListItemPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppSingleLineDateFilter
import java.util.Calendar

@Composable
fun PersonalScreenWeightOption(
    modifier: Modifier = Modifier,
    weights: List<WeightModelUi>,
    selectedDateText: String?,
    selectedDateDayId: Int?,
    isLoading: Boolean,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppSingleLineDateFilter(
            modifier = Modifier
                .padding(personalScreenContentItemVerticalPadding)
                .fillMaxWidth()
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(appDateFilterInnerPadding),
            selectedFilterDateText = selectedDateText,
            selectedFilterDateDayTextId = selectedDateDayId,
            onPickerVisibilityChange = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateWeightsDatePickerVisibility
                )
            },
            onCancel = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateWeightsSelectedDate(
                        null
                    )
                )
            }
        )

        AppCustomButton(
            modifier = Modifier
                .padding(personalScreenContentItemVerticalPadding)
                .fillMaxWidth(),
            onClick = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateWeightsAddDialogVisibility
                )
            },
            text = stringResource(R.string.add_weight_text),
            icon = painterResource(R.drawable.add_icon)
        )

        WeightList(
            modifier = Modifier
                .padding(personalScreenContentItemVerticalPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .fillMaxWidth()
                .padding(personalScreenInfoBlockInnerPadding),
            list = weights,
            isLoading = isLoading,
            proceedIntent = proceedIntent
        )
    }
}

@Composable
private fun WeightList(
    modifier: Modifier = Modifier,
    list: List<WeightModelUi>,
    isLoading: Boolean,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    when {
        isLoading -> {
            AppProgressAnimation(
                modifier = Modifier
                    .padding(healthScreenContentItemTopPadding)
                    .fillMaxSize()
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .padding(healthScreenNutritionOptionListPadding),
            )
        }

        !isLoading && list.isEmpty() -> {
            AppEmptyScreen(
                modifier = Modifier
                    .padding(healthScreenContentItemTopPadding)
                    .fillMaxSize()
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .padding(healthScreenNutritionOptionListPadding),
            )
        }

        else -> {
            LazyColumn(
                modifier = modifier
            ) {

                item {
                    Text(
                        text = stringResource(R.string.weights_history_text),
                        style = bodyStyle.copy(
                            color = appMainTextColor,
                            fontSize = appTextSize
                        )
                    )
                }

                items(list) { item ->
                    WeightItem(
                        modifier = Modifier
                            .padding(personalScreenListItemPadding)
                            .fillMaxWidth()
                            .clip(appRoundedShape)
                            .border(
                                width = appDetailsInfoBlockBorderWidth,
                                color = appSubtleTextColor,
                                shape = appRoundedShape
                            )
                            .padding(healthScreenNutritionOptionFoodItemInnerPadding),
                        item = item,
                        proceedIntent = proceedIntent
                    )
                }
            }
        }
    }
}

@Composable
private fun WeightItem(
    modifier: Modifier = Modifier,
    item: WeightModelUi,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(
                text = item.weightText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )

            Text(
                modifier = Modifier.padding(personalScreenInfoTextTopPadding),
                text = item.fullTimeText,
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSizeSmall
                )
            )
        }

        Icon(
            modifier = Modifier
                .padding(healthScreenNutritionOptionTextStartPadding)
                .clickable {
                    proceedIntent(
                        IPersonalScreenIntent.DeleteWeight(
                            selected = item
                        )
                    )
                },
            painter = painterResource(R.drawable.delete_icon),
            tint = deleteButtonColor,
            contentDescription = stringResource(R.string.picture_description)
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
                PersonalScreenWeightOption(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(personalScreenContentPadding),
                    weights = listOf(
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 12.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 32.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    selectedDateText = "12/12/1211",
                    selectedDateDayId = null,
                    isLoading = false,
                    proceedIntent = {}
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
                PersonalScreenWeightOption(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(personalScreenContentPadding),
                    weights = listOf(
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 12.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 32.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    selectedDateText = "12/12/1211",
                    selectedDateDayId = null,
                    isLoading = false,
                    proceedIntent = {}
                )
            }
        }
    }
}