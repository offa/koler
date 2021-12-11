package com.chooloo.www.koler.ui.phones

import com.chooloo.www.koler.data.account.PhoneAccount
import com.chooloo.www.koler.ui.list.ListContract

interface PhonesContract : ListContract {
    interface View : ListContract.View<PhoneAccount> {
        val contactId: Long?
    }

    interface Controller<V : View> : ListContract.Controller<PhoneAccount, V>
}