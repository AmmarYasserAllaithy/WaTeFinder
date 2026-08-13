package app.ammar.watefinder.di

import app.ammar.watefinder.data.local.AccountDatabase
import app.ammar.watefinder.data.repo.AccountRepo
import app.ammar.watefinder.domain.repository.AccountRepository
import app.ammar.watefinder.ui.main.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single { AccountDatabase.getDao(get()) }

    single<AccountRepository> { AccountRepo(get()) }

    viewModel { MainViewModel(get()) }

}