package com.afarelramdani.talentyou.content.projectrecruiter


interface ProjectContract {

    interface View {
        fun addListProject(list: List<ProjectModel>)
//        fun showProgressBar()
//        fun hideProgressBar()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun getAllProject()
    }
}