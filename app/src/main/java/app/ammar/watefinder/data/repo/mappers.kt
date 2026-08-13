package app.ammar.watefinder.data.repo

import app.ammar.watefinder.data.local.entity.AccountEntity
import app.ammar.watefinder.domain.model.AccountModel


internal fun AccountEntity.toDomain() = AccountModel(
    number = number,
    displayFormat = displayFormat,
    message = message,
    created = created,
)

internal fun AccountModel.toEntity() = AccountEntity(
    number = number,
    displayFormat = displayFormat,
    message = message,
    created = created,
)
