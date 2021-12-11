package com.chooloo.www.koler.adapter

import android.graphics.Color
import com.chooloo.www.koler.contentresolver.RecentsContentResolver.Companion.getCallTypeImage
import com.chooloo.www.koler.data.account.Recent
import com.chooloo.www.koler.di.activitycomponent.ActivityComponent
import com.chooloo.www.koler.ui.widgets.listitem.ListItem
import com.chooloo.www.koler.util.getHoursString

class RecentsAdapter(activityComponent: ActivityComponent) :
    ListAdapter<Recent>(activityComponent) {

    override fun onBindListItem(listItem: ListItem, item: Recent) {
        listItem.apply {
            component.phoneAccountsInteractor.lookupAccount(item.number) {
                titleText = it.name ?: item.number
            }

            isCompact = component.preferencesInteractor.isCompact
            captionText = if (item.date != null) context.getHoursString(item.date) else null

            setImageBackgroundColor(Color.TRANSPARENT)
            setImageResource(getCallTypeImage(item.type))
        }
    }
}