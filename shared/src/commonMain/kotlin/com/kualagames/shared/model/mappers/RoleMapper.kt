package com.kualagames.shared.model.mappers

import com.kualagames.shared.model.dto.RoleDTO
import com.kualagames.shared.model.Role

fun RoleDTO.toDomain() = Role(raw)