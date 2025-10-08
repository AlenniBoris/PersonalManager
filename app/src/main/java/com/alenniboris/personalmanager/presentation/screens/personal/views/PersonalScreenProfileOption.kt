package com.alenniboris.personalmanager.presentation.screens.personal.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.model.user.FitnessGoal
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.heart.HeartRateModelUi
import com.alenniboris.personalmanager.presentation.model.user.UserModelUi
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.screens.personal.IPersonalScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockRightPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenActivitiesIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenContentItemVerticalPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenHeartRateIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenInfoBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenInfoTextTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenProfileOptionEditIconInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenProfileOptionIconSize
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenProfileOptionTextStartPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenWeightIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appInfoBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppDetailsInfoBlock
import java.util.Calendar

@Composable
fun PersonalScreenProfileOption(
    modifier: Modifier = Modifier,
    user: UserModelUi,
    todayWeight: WeightModelUi?,
    todayHeartRate: HeartRateModelUi?,
    todayActivitiesNumber: Int,
    isDataLoading: Boolean,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        HeaderSection(
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
            user = user,
            proceedIntent = proceedIntent
        )

        PersonalInfoSection(
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
            user = user
        )

        DailyGoalsSection(
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
            user = user
        )

        TodayStatsSection(
            modifier = Modifier
                .padding(personalScreenContentItemVerticalPadding)
                .horizontalScroll(rememberScrollState()),
            todayWeight = todayWeight,
            todayHeartRate = todayHeartRate,
            todayActivitiesNumber = todayActivitiesNumber,
            isDataLoading = isDataLoading,
        )
    }
}

@Composable
private fun TodayStatsSection(
    modifier: Modifier = Modifier,
    todayWeight: WeightModelUi?,
    todayHeartRate: HeartRateModelUi?,
    todayActivitiesNumber: Int,
    isDataLoading: Boolean,
) {

    Row(
        modifier = modifier
    ) {

        AppDetailsInfoBlock(
            modifier = Modifier
                .padding(appDetailsInfoBlockRightPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(appInfoBlockInnerPadding),
            sectionIcon = painterResource(R.drawable.weight_scale),
            iconTint = personalScreenWeightIconColor,
            sectionHeader = stringResource(R.string.weight_option),
            sectionValue = todayWeight?.weightText?.let {
                it + " " + stringResource(R.string.kg_text)
            } ?: stringResource(R.string.no_text_placeholder),
            isLoading = isDataLoading
        )

        AppDetailsInfoBlock(
            modifier = Modifier
                .padding(appDetailsInfoBlockRightPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(appInfoBlockInnerPadding),
            sectionIcon = painterResource(R.drawable.heart_rate_icon),
            iconTint = personalScreenHeartRateIconColor,
            sectionHeader = stringResource(R.string.heart_rate_section),
            sectionValue = todayHeartRate?.heartRateText?.let {
                it + " " + stringResource(R.string.bpm_text)
            } ?: stringResource(R.string.no_text_placeholder),
            isLoading = isDataLoading
        )

        AppDetailsInfoBlock(
            modifier = Modifier
                .padding(appDetailsInfoBlockRightPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(appInfoBlockInnerPadding),
            sectionIcon = painterResource(R.drawable.activity_icon),
            iconTint = personalScreenActivitiesIconColor,
            sectionHeader = stringResource(R.string.activity_option),
            sectionValue = todayActivitiesNumber.toString(),
            isLoading = isDataLoading
        )
    }
}

@Composable
private fun DailyGoalsSection(
    modifier: Modifier = Modifier,
    user: UserModelUi
) {

    Column(
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.daily_goals_icon),
                tint = appMainTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(personalScreenProfileOptionTextStartPadding),
                text = stringResource(R.string.daily_goals_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Row(
            modifier = Modifier
                .padding(personalScreenInfoTextTopPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = stringResource(R.string.calories_section),
                    style = bodyStyle.copy(
                        color = appSubtleTextColor,
                        fontSize = appTextSizeSmall
                    )
                )

                Text(
                    text = user.caloriesIntakeText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Column(
                modifier = Modifier.padding(personalScreenProfileOptionTextStartPadding)
            ) {

                Text(
                    text = stringResource(R.string.proteins_section_text),
                    style = bodyStyle.copy(
                        color = appSubtleTextColor,
                        fontSize = appTextSizeSmall
                    )
                )

                Text(
                    text = user.neededProteinsText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(personalScreenInfoTextTopPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = stringResource(R.string.carbs_section_text),
                    style = bodyStyle.copy(
                        color = appSubtleTextColor,
                        fontSize = appTextSizeSmall
                    )
                )

                Text(
                    text = user.neededCarbohydratesText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Column(
                modifier = Modifier.padding(personalScreenProfileOptionTextStartPadding)
            ) {

                Text(
                    text = stringResource(R.string.fats_section_text),
                    style = bodyStyle.copy(
                        color = appSubtleTextColor,
                        fontSize = appTextSizeSmall
                    )
                )

                Text(
                    text = user.neededFatsText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
private fun PersonalInfoSection(
    modifier: Modifier = Modifier,
    user: UserModelUi
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(R.string.personal_info_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        Row(
            modifier = Modifier.padding(personalScreenInfoTextTopPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.email_icon),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(personalScreenProfileOptionTextStartPadding),
                text = user.email,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Row(
            modifier = Modifier.padding(personalScreenInfoTextTopPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.phone_icon),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(personalScreenProfileOptionTextStartPadding),
                text = user.phoneNumber,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Row(
            modifier = Modifier.padding(personalScreenInfoTextTopPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.today_tasks_option),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(personalScreenProfileOptionTextStartPadding),
                text = user.ageText + stringResource(R.string.years_old_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Row(
            modifier = Modifier.padding(personalScreenInfoTextTopPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.health_button_icon),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(personalScreenProfileOptionTextStartPadding),
                text = user.heightText + stringResource(R.string.cm_text) + "/"
                        + user.weightText + stringResource(R.string.kg_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Row(
            modifier = Modifier.padding(personalScreenInfoTextTopPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.daily_goals_icon),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(personalScreenProfileOptionTextStartPadding),
                text = stringResource(user.fitnessGoal.toUiString()),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Row(
            modifier = Modifier.padding(personalScreenInfoTextTopPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.location_icon),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(personalScreenProfileOptionTextStartPadding),
                text = user.address,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    user: UserModelUi,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            modifier = Modifier
                .size(personalScreenProfileOptionIconSize)
                .clip(CircleShape)
                .background(
                    color = appMainTextColor
                )
                .padding(personalScreenInfoBlockInnerPadding),
            painter = painterResource(R.drawable.person_icon),
            tint = appColor,
            contentDescription = stringResource(R.string.picture_description)
        )

        Column(
            modifier = Modifier
                .padding(personalScreenProfileOptionTextStartPadding)
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = user.name,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = appTextSize
                )
            )

            Text(
                modifier = Modifier.padding(personalScreenInfoTextTopPadding),
                text = user.email,
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Icon(
            modifier = Modifier
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(personalScreenProfileOptionEditIconInnerPadding)
                .clickable {
                    proceedIntent(
                        IPersonalScreenIntent.ChangeUserUpdateDialogVisibility
                    )
                },
            painter = painterResource(R.drawable.edit_icon),
            tint = appMainTextColor,
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
                    .background(appColor)
                    .padding(horizontal = 15.dp)
            ) {
                PersonalScreenProfileOption(
                    modifier = Modifier
                        .padding(personalScreenContentItemVerticalPadding)
                        .fillMaxSize()
                        .padding(personalScreenContentPadding),
                    user = UserModelUi(
                        name = "hdsjbnj",
                        email = "dcsmlksdccmlkcdsm",
                        phoneNumber = "1231232",
                        age = 12,
                        height = 124.5,
                        fitnessGoal = FitnessGoal.Support,
                        address = "lja, adskm 12"
                    ),
                    todayWeight = WeightModelUi(
                        domainModel = WeightModelDomain(
                            id = "1",
                            userId = "1",
                            weight = 11.0,
                            markingDate = Calendar.getInstance().time,
                            markingTime = Calendar.getInstance().time
                        )
                    ),
                    todayHeartRate = HeartRateModelUi(
                        domainModel = HeartRateModelDomain(
                            id = "1",
                            userId = "1",
                            heartRate = 215,
                            markingDate = Calendar.getInstance().time,
                            markingTime = Calendar.getInstance().time
                        )
                    ),
                    todayActivitiesNumber = 1,
                    isDataLoading = false,
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
                    .background(appColor)
                    .padding(horizontal = 15.dp)
            ) {
                PersonalScreenProfileOption(
                    modifier = Modifier
                        .padding(personalScreenContentItemVerticalPadding)
                        .fillMaxSize()
                        .padding(personalScreenContentPadding),
                    user = UserModelUi(
                        name = "hdsjbnj",
                        email = "dcsmlksdccmlkcdsm",
                        phoneNumber = "1231232",
                        age = 12,
                        height = 124.5,
                        fitnessGoal = FitnessGoal.Support,
                        address = "lja, adskm 12"
                    ),
                    todayWeight = WeightModelUi(
                        domainModel = WeightModelDomain(
                            id = "1",
                            userId = "1",
                            weight = 11.0,
                            markingDate = Calendar.getInstance().time,
                            markingTime = Calendar.getInstance().time
                        )
                    ),
                    todayHeartRate = HeartRateModelUi(
                        domainModel = HeartRateModelDomain(
                            id = "1",
                            userId = "1",
                            heartRate = 215,
                            markingDate = Calendar.getInstance().time,
                            markingTime = Calendar.getInstance().time
                        )
                    ),
                    todayActivitiesNumber = 1,
                    isDataLoading = false,
                    proceedIntent = {}
                )
            }
        }
    }
}