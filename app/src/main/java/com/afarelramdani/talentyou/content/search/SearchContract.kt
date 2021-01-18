package com.afarelramdani.talentyou.content.search

import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerModel

interface SearchContract {
    interface View {
        fun onResultSuccess(list: List<ListEngineerModel>)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callServiceSearch(search: String?, filter: Int?)
    }
}