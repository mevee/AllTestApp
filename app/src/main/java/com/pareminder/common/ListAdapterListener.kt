package com.pareminder.ui.note.notesadapter

import com.pareminder.common.EventConst

interface ListAdapterListener {
    fun onItemClicked(position:Int,event: EventConst? =null)

}