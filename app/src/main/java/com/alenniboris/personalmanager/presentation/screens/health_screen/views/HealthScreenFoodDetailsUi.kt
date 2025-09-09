package com.alenniboris.personalmanager.presentation.screens.health_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeModelUi
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenCarbsColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemDoubleTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenFatColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenFoodDetailsColorRadius
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenFoodDetailsStartPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenProteinColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockInnerPadding
import java.util.Calendar
import kotlin.math.roundToInt

@Composable
fun HealthScreenFoodDetailsUi(
    modifier: Modifier = Modifier,
    food: FoodIntakeModelUi,
    totalUserProteins: Double,
    totalUserFats: Double,
    totalUserCarbs: Double,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = food.title,
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSizeMedium,
                fontWeight = FontWeight.Bold
            )
        )

        Row(
            modifier = Modifier.padding(healthScreenContentItemTopPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.calories_section_icon),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(healthScreenFoodDetailsStartPadding),
                text = food.dateText,
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Column(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .fillMaxWidth()
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = food.caloriesText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSizeMedium,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(healthScreenContentItemTopPadding),
                text = stringResource(R.string.calories_section),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )
        }

        MacronutrientsSection(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .fillMaxWidth()
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            proteinsText = food.proteinsText,
            fatsText = food.fatsText,
            carbsText = food.carbsText
        )

        CaloriesDistributionSection(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .fillMaxWidth()
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            proteins = food.proteinsValue / totalUserProteins,
            fats = food.fatsValue / totalUserFats,
            carbs = food.carbsValue / totalUserCarbs
        )
    }
}

@Composable
private fun CaloriesDistributionSection(
    modifier: Modifier = Modifier,
    proteins: Double,
    fats: Double,
    carbs: Double
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.calories_distribution_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        HealthScreenDataProgressSection(
            modifier = Modifier.padding(healthScreenContentItemDoubleTopPadding),
            value = proteins,
            valueText = "${(proteins * 100).roundToInt()}%",
            headerText = stringResource(R.string.proteins_section_text)
        )

        HealthScreenDataProgressSection(
            modifier = Modifier.padding(healthScreenContentItemTopPadding),
            value = fats,
            valueText = "${(fats * 100).roundToInt()}%",
            headerText = stringResource(R.string.fats_section_text)
        )

        HealthScreenDataProgressSection(
            modifier = Modifier.padding(healthScreenContentItemTopPadding),
            value = carbs,
            valueText = "${(carbs * 100).roundToInt()}%",
            headerText = stringResource(R.string.carbs_section_text)
        )
    }
}

@Composable
private fun MacronutrientsSection(
    modifier: Modifier = Modifier,
    proteinsText: String,
    fatsText: String,
    carbsText: String
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.macronutients_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        MacronutrientsRow(
            modifier = Modifier
                .padding(healthScreenContentItemDoubleTopPadding)
                .fillMaxWidth(),
            color = healthScreenProteinColor,
            value = proteinsText
        )

        MacronutrientsRow(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .fillMaxWidth(),
            color = healthScreenFatColor,
            value = fatsText
        )

        MacronutrientsRow(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .fillMaxWidth(),
            color = healthScreenCarbsColor,
            value = carbsText
        )
    }
}

@Composable
private fun MacronutrientsRow(
    modifier: Modifier = Modifier,
    color: Color,
    value: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(healthScreenFoodDetailsColorRadius)
                    .clip(CircleShape)
                    .background(color)
            )

            Text(
                modifier = Modifier.padding(healthScreenFoodDetailsStartPadding),
                text = stringResource(R.string.proteins_section_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Text(
            text = value,
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize
            )
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
                HealthScreenFoodDetailsUi(
                    modifier = Modifier
                        .padding(healthScreenContentItemTopPadding)
                        .fillMaxWidth()
                        .padding(healthScreenContentPadding),
                    food = FoodIntakeModelUi(
                        domainModel = FoodIntakeModelDomain(
                            id = "1",
                            userId = "1",
                            title = "kjsdcnkjds",
                            proteins = 112.3,
                            fats = 122.3,
                            carbohydrates = 17.0,
                            markingDate = Calendar.getInstance().time,
                            markingTime = Calendar.getInstance().time,
                            calories = 12112.3
                        )
                    ),
                    totalUserFats = 200.0,
                    totalUserProteins = 120.0,
                    totalUserCarbs = 50.0
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
                HealthScreenFoodDetailsUi(
                    modifier = Modifier
                        .padding(healthScreenContentItemTopPadding)
                        .fillMaxWidth()
                        .padding(healthScreenContentPadding),
                    food = FoodIntakeModelUi(
                        domainModel = FoodIntakeModelDomain(
                            id = "1",
                            userId = "1",
                            title = "kjsdcnkjds",
                            proteins = 112.3,
                            fats = 122.3,
                            carbohydrates = 17.0,
                            markingDate = Calendar.getInstance().time,
                            markingTime = Calendar.getInstance().time,
                            calories = 12112.3
                        )
                    ),
                    totalUserFats = 200.0,
                    totalUserProteins = 120.0,
                    totalUserCarbs = 50.0
                )
            }
        }
    }
}