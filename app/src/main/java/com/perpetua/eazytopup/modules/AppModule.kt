package com.perpetua.eazytopup.modules

import com.perpetua.eazytopup.repositories.AirtimeRepository
import com.perpetua.eazytopup.repositories.DataRepository
import com.perpetua.eazytopup.viewmodels.AirtimeViewModel
import com.perpetua.eazytopup.viewmodels.DataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        AirtimeViewModel(get())
    }
    viewModel {
        DataViewModel(get()) // Add DataViewModel definition
    }
}

val repositoryModule = module {
    single {
        AirtimeRepository()
    }
    single {
        DataRepository() // Add DataRepository definition
    }
}
