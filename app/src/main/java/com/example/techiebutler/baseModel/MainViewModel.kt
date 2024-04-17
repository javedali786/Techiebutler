package com.example.techiebutler.baseModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techiebutler.apiClient.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
  /*  private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts
*/
 /*   fun loadData() {
        viewModelScope.launch {
            val response = ApiClient.apiService.getPosts(1,10)
            _posts.postValue(response)
        }
    }*/

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private var currentPage = 1
    val pageSize = 10

    var isLoading = false
    var isLastPage = false

    fun loadData() {
        if (!isLoading && !isLastPage) {
            isLoading = true
            viewModelScope.launch(Dispatchers.IO) {
                val response = fetchDataFromApi(currentPage, pageSize)
                if (response.isNotEmpty()) {
                    currentPage++
                    _posts.postValue(response)
                } else {
                    isLastPage = true
                }
                isLoading = false
            }
        }
    }

    private suspend fun fetchDataFromApi(page: Int, pageSize: Int): List<Post> {
        return withContext(Dispatchers.IO) {
            val response = ApiClient.apiService.getPosts(page, pageSize)
            response // Return the list of posts retrieved from the API
        }
    }
}
