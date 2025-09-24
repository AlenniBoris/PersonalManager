package com.alenniboris.personalmanager.presentation.screens.personal

import android.os.Build
import androidx.annotation.RequiresApi
import com.alenniboris.personalmanager.domain.model.activity.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.model.user.FitnessGoal
import com.alenniboris.personalmanager.domain.model.user.UserGender
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.utils.stripTime
import com.alenniboris.personalmanager.presentation.mapper.toDayType
import com.alenniboris.personalmanager.presentation.model.activity.ActivityModelUi
import com.alenniboris.personalmanager.presentation.model.heart.HeartRateModelUi
import com.alenniboris.personalmanager.presentation.model.user.UserModelUi
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import com.alenniboris.personalmanager.presentation.uikit.values.toUiString
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

data class PersonalScreenState(
    val currentOption: PersonalScreenOption = PersonalScreenOption.Profile,
    val listOfScreenOptions: List<PersonalScreenOption> = PersonalScreenOption.entries.toList(),
    val user: UserModelUi = UserModelUi(),
    val todayWeight: WeightModelUi? = null,
    val isTodayWeightLoading: Boolean = false,
    val todayHeartRate: HeartRateModelUi? = null,
    val isTodayHeartRateLoading: Boolean = false,
    val todayActivitiesNumber: Int = 0,
    val isTodayActivitiesNumberLoading: Boolean = false,
    val isUserUpdateDialogVisible: Boolean = false,
    val userUpdate: UserModelUi = user,
    val isUserUpdating: Boolean = false,
    val listOfUserGenders: List<UserGender> = UserGender.entries.toList(),
    val listOfFitnessGoals: List<FitnessGoal> = FitnessGoal.entries.toList(),
    val isWeightsLoading: Boolean = false,
    val weightsSelectedDate: Date? = null,
    val weights: List<WeightModelUi> = emptyList(),
    val isWeightsDatePickerVisible: Boolean = false,
    val isWeightAddDialogVisible: Boolean = false,
    val addingWeight: AddingWeight = AddingWeight(userId = user.id),
    val isWeightUploading: Boolean = false,
    val isHeartRatesLoading: Boolean = false,
    val heartRatesSelectedDate: Date? = null,
    val heartRates: List<HeartRateModelUi> = emptyList(),
    val isHeartRatesDatePickerVisible: Boolean = false,
    val isHeartRateAddDialogVisible: Boolean = false,
    val addingHeartRate: AddingHeartRate = AddingHeartRate(userId = user.id),
    val isHeartRateUploading: Boolean = false,
    val isActivitiesLoading: Boolean = false,
    val activitiesSelectedDate: Date? = null,
    val activities: List<ActivityModelUi> = emptyList(),
    val isActivitiesDatePickerVisible: Boolean = false,
    val isActivityAddDialogVisible: Boolean = false,
    val addingActivity: AddingActivity = AddingActivity(userId = user.id),
    val isActivityUploading: Boolean = false,
) {
    val weightsSelectedDateText: String? = weightsSelectedDate?.let {
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(it)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val weightsSelectedDateDayText: Int? = weightsSelectedDate?.toDayType()?.toUiString()

    val heartRatesSelectedDateText: String? = heartRatesSelectedDate?.let {
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(it)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val heartRatesSelectedDateDayText: Int? = heartRatesSelectedDate?.toDayType()?.toUiString()

    val activitiesSelectedDateText: String? = activitiesSelectedDate?.let {
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(it)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val activitiesSelectedDateDayText: Int? = activitiesSelectedDate?.toDayType()?.toUiString()

    val isUserTodayDataLoading: Boolean =
        isTodayWeightLoading || isTodayHeartRateLoading || isTodayActivitiesNumberLoading

    data class AddingWeight(
        val id: String = "",
        val userId: String = "",
        val weight: String = "",
        val markingDate: Date = Calendar.getInstance().time.stripTime(),
        val markingTime: Date = Calendar.getInstance().time
    ) {

        fun toModelDomain(): WeightModelDomain =
            WeightModelDomain(
                id = this.id,
                userId = this.userId,
                weight = this.weight.toDouble(),
                markingDate = this.markingDate,
                markingTime = this.markingTime
            )
    }

    data class AddingHeartRate(
        val id: String = "",
        val userId: String = "",
        val heartRate: String = "",
        val markingDate: Date = Calendar.getInstance().time.stripTime(),
        val markingTime: Date = Calendar.getInstance().time
    ) {

        fun toModelDomain(): HeartRateModelDomain =
            HeartRateModelDomain(
                id = this.id,
                userId = this.userId,
                heartRate = this.heartRate.toDouble().roundToInt(),
                markingDate = this.markingDate,
                markingTime = this.markingTime
            )
    }

    data class AddingActivity(
        val id: String = "",
        val userId: String = "",
        val title: String = "",
        val duration: String = "",
        val calories: String = "",
        val markingDate: Date = Calendar.getInstance().time.stripTime(),
        val markingTime: Date = Calendar.getInstance().time
    ) {
        fun toModelDomain(): ActivityModelDomain =
            ActivityModelDomain(
                id = this.id,
                userId = this.userId,
                title = this.title,
                duration = this.duration.toDouble().roundToInt(),
                calories = this.calories.toDouble(),
                markingDate = this.markingDate,
                markingTime = this.markingTime
            )
    }
}
