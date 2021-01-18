package com.afarelramdani.talentyou.content.search

import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerModel
import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerResponse
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchPresenter(private val coroutineScope: CoroutineScope,
                      private val service: ApiService) : SearchContract.Presenter {

    private var view: SearchContract.View? = null

    override fun bindToView(view: SearchContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun callServiceSearch(search: String?, filter: Int?) {
        coroutineScope.launch {
            view?.showLoading()
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getAllEngineerSearch(search = search, filter = filter)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("Engineer Not Found!")
                            }
                            e.code() == 400 -> {
                                view?.onResultFail("Please Re-login")
                            }
                            else -> {
                                view?.onResultFail("Server Under Maintenance")
                            }
                    }

                }
                }
            }
            if (result is ListEngineerResponse) {
                view?.hideLoading()
                if (result.success) {
                    val list = result.data.map {
                        ListEngineerModel(it.engineerId, it.accountId, it.accountName,
                            it.accountEmail, it.accountNoHp, it.engineerJobTittle, it.engineerJobType, it.engineerOrigin,
                            it.engineerDesc, it.engineerFotoProfile)
                    }
                    view?.onResultSuccess(list)
                } else {
                    view?.onResultFail(result.message)
                }
            }
        }
    }
}