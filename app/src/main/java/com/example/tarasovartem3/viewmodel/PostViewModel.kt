package com.example.tarasovartem3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.example.tarasovartem3.db.AppDb
import com.example.tarasovartem3.repository.Post
import com.example.tarasovartem3.repository.PostRepository
import com.example.tarasovartem3.repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likes = 0,
    share = 0,
    likedByMe = false
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(AppDb.getInstance(application).postDao)
    val data = repository.getAll()
    val selectedPost = MutableLiveData<Post>()
    val edited = MutableLiveData(empty)
    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Int) = repository.likeById(id)
    fun shareById(id: Int) = repository.shareById(id)
    fun removeById(id: Int) = repository.removeById(id)
    fun getPostById(id: Int) {
        repository.postID(id).observeForever {
            selectedPost.value = it
        }
    }
}