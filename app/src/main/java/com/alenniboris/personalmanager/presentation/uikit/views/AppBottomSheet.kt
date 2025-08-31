package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.bottomSheetShape
import com.alenniboris.personalmanager.presentation.uikit.theme.filterDragHandleHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.filterDragHandlePadding
import com.alenniboris.personalmanager.presentation.uikit.theme.filterDragHandleShape
import com.alenniboris.personalmanager.presentation.uikit.theme.filterDragHandleWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.filterSheetTonalElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    onDismiss: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = bottomSheetShape,
        containerColor = appColor,
        tonalElevation = filterSheetTonalElevation,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(filterDragHandlePadding)
                    .width(filterDragHandleWidth)
                    .height(filterDragHandleHeight)
                    .clip(filterDragHandleShape)
                    .background(appMainTextColor)

            )
        }
    ) {
        content()
    }
}