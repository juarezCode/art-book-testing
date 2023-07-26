package com.juarezcode.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.juarezcode.artbooktesting.model.ImageResponse
import com.juarezcode.artbooktesting.roomdb.Art
import com.juarezcode.artbooktesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}