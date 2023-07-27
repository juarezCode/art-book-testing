package com.juarezcode.artbooktesting.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarezcode.artbooktesting.model.ImageResponse
import com.juarezcode.artbooktesting.repo.ArtRepositoryInterface
import com.juarezcode.artbooktesting.roomdb.Art
import com.juarezcode.artbooktesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(private val repository: ArtRepositoryInterface) :
    ViewModel() {

    val artList = repository.getArt()

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg


    fun resetInsertArtMessage() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(Resource.error("enter name, artist, year", null))
            return
        }

        Log.d("year", "year $year")
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            insertArtMsg.postValue(Resource.error("year $year should be number", null))
            return
        }

        val art = Art(name, artistName, yearInt, selectedImage.value.orEmpty())
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchImage: String) {
        if (searchImage.isEmpty()) return

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchImage)
            images.value = response
        }
    }
}