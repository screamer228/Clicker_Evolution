package com.example.clickerevolution.data.repository.resources

import com.example.clickerevolution.presentation.model.Resources

interface ResourcesRepository {
    suspend fun getResources() : Resources
    suspend fun updateResources(resources: Resources)
}