package com.example.clickerevolution.data.repository.resources

import com.example.clickerevolution.data.room.resources.ResourcesDao
import com.example.clickerevolution.data.room.resources.mapper.toEntity
import com.example.clickerevolution.data.room.resources.mapper.toResources
import com.example.clickerevolution.presentation.model.Resources
import javax.inject.Inject

class ResourcesRepositoryImpl @Inject constructor(
    private val resourcesDao: ResourcesDao
) : ResourcesRepository {

    override suspend fun getResources(): Resources {
        val resourcesEntity = resourcesDao.getResources()
        return resourcesEntity?.toResources() ?: Resources()
    }

    override suspend fun updateResources(resources: Resources) {
        resourcesDao.updateResources(resources.toEntity())
    }
}