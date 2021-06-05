package com.javatar.api.nexus.api

import com.javatar.api.nexus.api.data.AssetList
import com.javatar.api.nexus.api.data.NexusAsset
import kotlinx.coroutines.flow.Flow

interface NexusAssetAPI {

    fun assets(repository: String, token: String = "") : Flow<AssetList>
    fun getAsset(assetId: String) : Flow<NexusAsset>

}